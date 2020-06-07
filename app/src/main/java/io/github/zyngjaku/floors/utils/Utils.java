package io.github.zyngjaku.floors.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import io.github.zyngjaku.floors.R;

public class Utils {
    public static Bitmap resizeBitmap(Bitmap bitmap, float newWidth, float newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleHeight = (newHeight) / height;
        float scaleWidth = (newWidth) / width;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);

        return newBitmap;
    }
}
