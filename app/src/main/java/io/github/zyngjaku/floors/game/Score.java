package io.github.zyngjaku.floors.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;

import androidx.core.content.ContextCompat;

import io.github.zyngjaku.floors.game.GameSurface;
import io.github.zyngjaku.floors.R;
import io.github.zyngjaku.floors.utils.Dimensions;

public class Score {

    private GameSurface gameSurface;
    private Context context;
    private int score;

    public Score(GameSurface gameSurface, Context context) {
        this.gameSurface = gameSurface;
        this.context = context;

        this.score = 0;
    }

    public void draw(Canvas canvas)  {
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.gameActivityScore));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(Dimensions.screenWidth/4);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            paint.setTypeface(context.getResources().getFont(R.font.score));
        }

        Rect bounds = new Rect();
        paint.getTextBounds(String.valueOf((int)(Math.pow(10, (String.valueOf(score).length()-1)))), 0, String.valueOf(score).length(), bounds);

        canvas.drawText(String.valueOf(score), Dimensions.screenWidth/2 - bounds.width()/2f, (Dimensions.blockPositions[0] + bounds.height()) / 2, paint);
    }

    public void incrScore() {
        this.score += 1;
    }

    public int getScore() {
        return score;
    }
}