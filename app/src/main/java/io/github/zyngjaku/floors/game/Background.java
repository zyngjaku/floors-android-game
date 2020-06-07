package io.github.zyngjaku.floors.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import java.util.Random;

import io.github.zyngjaku.floors.game.GameSurface;
import io.github.zyngjaku.floors.R;
import io.github.zyngjaku.floors.utils.Dimensions;

public class Background {

    private GameSurface gameSurface;
    private Context context;
    private int level;
    private int colors[];

    public Background(GameSurface gameSurface, Context context) {
        this.gameSurface = gameSurface;
        this.context = context;

        this.level = 0;
        this.colors = new int[3];

        this.colors[0] = ContextCompat.getColor(context, R.color.gameActivityBlue);
        this.colors[1] = ContextCompat.getColor(context, R.color.gameActivityGreen);
        this.colors[2] = ContextCompat.getColor(context, R.color.gameActivityRed);
    }

    public void draw(Canvas canvas)  {
        canvas.drawColor(ContextCompat.getColor(context, R.color.gameActivityBackground));

        for (int i=0; i<3; i++) {
            Paint paint = new Paint();
            paint.setColor(colors[i]);
            canvas.drawRect(0, Dimensions.blockPositions[i], Dimensions.blockWidth, Dimensions.blockHeight+Dimensions.blockPositions[i], paint);

            if (i != level) {
                paint.setColor(ContextCompat.getColor(context, R.color.gameActivityBackground));
                paint.setAlpha(200);
                canvas.drawRect(0, Dimensions.blockPositions[i], Dimensions.blockWidth, Dimensions.blockHeight + Dimensions.blockPositions[i], paint);
            }
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void randomLevel() {
        Random rand = new Random();
        int newLevel = rand.nextInt(3);

        while (newLevel == level) {
            newLevel = rand.nextInt(3);
        }

        level = newLevel;
    }

}