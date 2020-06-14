package io.github.zyngjaku.floors;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StatisticsActivity extends Activity {

    private static final String TAG = "Statistics";
    DatabaseHelper mDatabaseHelper;
    private RecyclerView recycledView;

    ArrayList<String > user_id, user_name, user_score;

    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_statistics);

        recycledView = (RecyclerView) findViewById(R.id.recycledView);
        mDatabaseHelper = new DatabaseHelper(StatisticsActivity.this);
        user_id = new ArrayList<>();
        user_name = new ArrayList<>();
        user_score = new ArrayList<>();

        //----------------------------------------------------------------------------------------------------------------------------------------------------
        storeDataInArrays();

        customAdapter = new CustomAdapter(StatisticsActivity.this, user_id,user_name, user_score);
        recycledView.setAdapter(customAdapter);
        recycledView.setLayoutManager(new LinearLayoutManager(StatisticsActivity.this));


        //TODO: show best score and our score + position
        //SharedPreferences prefs = this.getSharedPreferences("AGH-Floors", Context.MODE_PRIVATE);
        //prefs.getString("uniqueID", "null");
        //prefs.getInt("bestScore", 0);
        //uniqueID dla użytkownika (powinno dzialac ale nie testowane)
    }

    private void storeDataInArrays(){
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        Cursor cursor = mDatabaseHelper.getData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()) {
                user_id.add(cursor.getString(0));
                user_name.add(cursor.getString(1));
                user_score.add(cursor.getString(2));
            }
        }
     //   Recycler adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
     //   mListView.setAdapter(adapter);

    }

}