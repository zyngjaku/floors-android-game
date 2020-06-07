package io.github.zyngjaku.floors.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.zyngjaku.floors.game.GameSurface;
import io.github.zyngjaku.floors.utils.Dimensions;

public class Obstacle extends GameObject {
    private GameSurface gameSurface;

    private int level;


    public Obstacle(GameSurface gameSurface, Bitmap image) {
        super(image, 1, 1, 0, 0);

        this.gameSurface = gameSurface;

        newLevel(0);
    }

    public void update()  {

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void newLevel(int level) {
        this.level = level;
        this.y = (int) (Dimensions.blockPositions[level] + Dimensions.blockHeight - getHeight());

    }
}