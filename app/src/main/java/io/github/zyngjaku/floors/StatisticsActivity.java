package io.github.zyngjaku.floors;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class StatisticsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_statistics);

        //TODO: show best score and our score + position
        //SharedPreferences prefs = this.getSharedPreferences("AGH-Floors", Context.MODE_PRIVATE);
        //prefs.getString("uniqueID", "null");
        //prefs.getInt("bestScore", 0);
        //uniqueID dla użytkownika (powinno dzialac ale nie testowane)
    }

}