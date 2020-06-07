package io.github.zyngjaku.floors.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

public class Dimensions {
    public static float screenHeight;
    public static float screenWidth;

    public static float blockHeight;
    public static float blockWidth;
    public static float[] blockPositions;

    public static float obstackleSize;

    public static float characterHeight;

    public Dimensions(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        blockWidth = screenWidth;
        blockHeight = 0.15f*screenHeight;

        blockPositions = new float[3];
        blockPositions[0] = screenHeight/2 - blockHeight * 3/2 - screenHeight * 0.02f;
        blockPositions[1] = screenHeight/2 - blockHeight / 2;
        blockPositions[2] = screenHeight/2 + blockHeight / 2 + screenHeight * 0.02f;

        obstackleSize = 0.20f*blockHeight;

        characterHeight = 0.5f*blockHeight;
    }

}