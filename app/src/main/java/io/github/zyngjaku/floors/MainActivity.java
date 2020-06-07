package io.github.zyngjaku.floors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.UUID;

import io.github.zyngjaku.floors.game.GameSurface;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_main);

        SharedPreferences prefs = this.getSharedPreferences("AGH-Floors", Context.MODE_PRIVATE);
        String uniqueID = prefs.getString("uniqueID", "null");

        if(uniqueID.equals("null")) {
            uniqueID = UUID.randomUUID().toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("uniqueID", uniqueID);
            editor.apply();
        }

    }


    public void onClickPlay(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickStatistics(View view) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }
}