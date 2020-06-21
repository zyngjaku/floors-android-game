package io.github.zyngjaku.floors.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.UUID;

import io.github.zyngjaku.floors.GameActivity;
import io.github.zyngjaku.floors.MainActivity;
import io.github.zyngjaku.floors.R;
import io.github.zyngjaku.floors.objects.Ninja;
import io.github.zyngjaku.floors.objects.Obstacle;
import io.github.zyngjaku.floors.objects.ObstacleManager;
import io.github.zyngjaku.floors.utils.Dimensions;
import io.github.zyngjaku.floors.utils.Utils;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;

    private static final int MAX_STREAMS = 100;
    private int soundIdExplosion;
    private int soundIdBackground;

    private boolean soundPoolLoaded;
    private SoundPool soundPool;

    private Context context;

    private Dimensions dimensions;

    private Background background;
    private Score score;
    private Ninja ninja;
    private Obstacle obstacle;
    private ObstacleManager obstacleManager;

    public GameSurface(Context context) {
        super(context);

        this.context = context;

        this.setFocusable(true);
        this.getHolder().addCallback(this);
        this.initSurface();
    }

    private void initSurface() {
        dimensions = new Dimensions(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            ninja.jump();
        }

        return false;
    }

    public void update() {
        ninja.update();
        obstacleManager.update();

        if (obstacleManager.detectCollision(ninja)) {
            gameThread.setRunning(false);

            try {
                SharedPreferences prefs = context.getSharedPreferences("AGH-Floors", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorLast = prefs.edit();
                editorLast.putInt("lastScore", score.getScore());
                editorLast.apply();
                if (prefs.getInt("bestScore", 0) < score.getScore()) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("bestScore", score.getScore());
                    editor.apply();
                }

                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        }

        if ((ninja.getX() < -ninja.getWidth() && ninja.getMovingVectorX() < 0) || (ninja.getMovingVectorX() > 0 && ninja.getX() > Dimensions.screenWidth + ninja.getWidth())) {
            background.randomLevel();
            int newLevel = background.getLevel();
            ninja.incrSpeed();
            ninja.newLevel(newLevel);
            obstacleManager.newLevel(newLevel);
            score.incrScore();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        background.draw(canvas);
        score.draw(canvas);
        ninja.draw(canvas);
        obstacleManager.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        background = new Background(this, context);
        score = new Score(this, context);

        Bitmap ninja = BitmapFactory.decodeResource(this.getResources(), R.drawable.character);
        Bitmap scaledNinja = Utils.resizeBitmap(ninja, (ninja.getWidth() * 4 * Dimensions.characterHeight) / ninja.getHeight(), 4 * Dimensions.characterHeight);

        this.ninja = new Ninja(this, scaledNinja);

        obstacleManager = new ObstacleManager(this);

        gameThread = new GameThread(this, holder);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                this.gameThread.setRunning(false);
                this.gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = true;
        }
    }

}