package io.github.zyngjaku.floors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

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
        Log.d("SCORE", String.valueOf(prefs.getInt("bestScore", 0)));
    }


    public void onClickPlay(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickStatistics(View view) {
        if (isNetworkAvailable()) {
            SharedPreferences prefs = this.getSharedPreferences("AGH-Floors1", Context.MODE_PRIVATE);
            String uniqueID = prefs.getString("uniqueID", "null");
            Intent intent = (uniqueID.equals("null")) ? new Intent(this, LoginActivity.class) : new Intent(this, StatisticsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "No internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}