package io.github.zyngjaku.floors.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.zyngjaku.floors.game.GameSurface;
import io.github.zyngjaku.floors.utils.Dimensions;

public class Ninja extends GameObject {

    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 0;
    private static final int ROW_LEFT_TO_RIGHT = 1;
    private static final int ROW_BOTTOM_TO_TOP = 0;

    private int rowUsing = ROW_LEFT_TO_RIGHT;

    private int colUsing;

    private Bitmap[] leftToRights;
    private Bitmap[] rightToLeft;
    private Bitmap[] leftToRightJump;
    private Bitmap[] rightToLeftJump;

    private static float VELOCITY = 0.5f;

    private int movingVectorX = 20;
    private int movingVectorY = 0;

    private int tmp = 0;

    private long lastDrawNanoTime =-1;

    private boolean jumping = false;
    private int level = 0;

    private GameSurface gameSurface;

    public Ninja(GameSurface gameSurface, Bitmap image) {
        super(image, 4, 16, 0, 0);

        this.gameSurface = gameSurface;

        this.leftToRights = new Bitmap[colCount];
        this.rightToLeft = new Bitmap[colCount];
        this.leftToRightJump = new Bitmap[colCount];
        this.rightToLeftJump = new Bitmap[colCount];

        for(int col = 0; col< this.colCount; col++ ) {
            this.leftToRights[col] = this.createSubImageAt(1, col);
            this.rightToLeft[col] = this.createSubImageAt(3, col);
            this.leftToRightJump[col] = this.createSubImageAt(0, col);
            this.rightToLeftJump[col] = this.createSubImageAt(2, col);
        }

        initPosition();
    }

    private void initPosition() {
        Bitmap bitmap = this.getCurrentMoveBitmap();

        this.y = (int) (Dimensions.blockPositions[0] + Dimensions.blockHeight - bitmap.getHeight());
        this.x = -bitmap.getWidth();
    }

    private void updatePosition(int level) {
        Bitmap bitmap = this.getCurrentMoveBitmap();

        this.y = (int) (Dimensions.blockPositions[level] + Dimensions.blockHeight - bitmap.getHeight());
    }

    public Bitmap[] getMoveBitmaps()  {
        if(jumping) {
            if(rowUsing == 1)
                return this.leftToRightJump;

            return this.rightToLeftJump;
        }

        if(rowUsing == 1)
            return this.leftToRights;

        return this.rightToLeft;
        /*
        switch (rowUsing)  {
            case ROW_BOTTOM_TO_TOP:
                return  this.bottomToTops;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottoms;
            default:
                return null;
        }
         */
    }

    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }


    public void update()  {
        Bitmap bitmap = this.getCurrentMoveBitmap();

        if (jumping && movingVectorY < 0 && this.y < Dimensions.blockPositions[level] + Dimensions.blockHeight - 7/4f*bitmap.getHeight()) {
            movingVectorY = -movingVectorY;
        }

        if (jumping && movingVectorY < 0 && this.y < Dimensions.blockPositions[level] + Dimensions.blockHeight - 6/4f*bitmap.getHeight()) {
            tmp = 13;
        }

        if(jumping && movingVectorY > 0 && this.y >= Dimensions.blockPositions[level] + Dimensions.blockHeight - bitmap.getHeight()) {
            this.y = (int) (Dimensions.blockPositions[level] + Dimensions.blockHeight - bitmap.getHeight());
            movingVectorY = 0;
            jumping = false;
            tmp = 16;
        }

        if(jumping) {
            if(colUsing < tmp)
                this.colUsing++;
        }
        else {
            this.colUsing++;
        }

        if(colUsing >= this.colCount)  {
            this.colUsing =0;
        }

        long now = System.nanoTime();

        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }

        int deltaTime = (int) ((now - lastDrawNanoTime)/ 1000000 );

        float distance = VELOCITY * deltaTime;

        double movingVectorLength = Math.sqrt(movingVectorX* movingVectorX + movingVectorY*movingVectorY);

        this.x = x +  (int)(distance* movingVectorX / movingVectorLength);
        this.y = y +  (int)(distance* movingVectorY / movingVectorLength);

        if(movingVectorX > 0)  {
            this.rowUsing = 1;
        } else {
            this.rowUsing = 3;
        }
    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);

        this.lastDrawNanoTime= System.nanoTime();
    }

    public void jump() {
        if (!jumping) {
            jumping = true;
            movingVectorY = -Math.abs(movingVectorX);
            colUsing=0;
            tmp = 1;
        }
    }

    public int getMovingVectorX()  {
        return this.movingVectorX;
    }

    public void newLevel(int level) {
        this.level = level;
        movingVectorX = -movingVectorX;
        updatePosition(level);
    }

    public void incrSpeed () {
        if (VELOCITY < 0.9) {
            VELOCITY += 0.02;
        }
    }
}