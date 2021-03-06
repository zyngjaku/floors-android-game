package io.github.zyngjaku.floors.objects;

import android.graphics.Bitmap;

public abstract class GameObject {
    protected Bitmap image;

    protected final int rowCount;
    protected final int colCount;

    protected final int bitmapWidth;
    protected final int bitmapHeight;

    protected final int width;
    protected final int height;

    protected int x;
    protected int y;

    public GameObject(Bitmap image, int rowCount, int colCount, int x, int y)  {
        this.image = image;

        this.rowCount = rowCount;
        this.colCount = colCount;

        this.x= x;
        this.y= y;

        this.bitmapWidth = image.getWidth();
        this.bitmapHeight = image.getHeight();

        this.width = this.bitmapWidth / colCount;
        this.height= this.bitmapHeight / rowCount;
    }

    protected Bitmap createSubImageAt(int row, int col)  {
        Bitmap subImage = Bitmap.createBitmap(image, col*width, row*height, width, height);

        return subImage;
    }

    public int getX()  {
        return this.x;
    }

    public int getY()  {
        return this.y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
