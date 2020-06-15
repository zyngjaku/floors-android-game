package io.github.zyngjaku.floors;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StatisticsActivity extends Activity {

    private static final String TAG = "Statistics";
    DatabaseHelper mDatabaseHelper;
    private RecyclerView recycledView;

    ArrayList<String> user_id, user_name, user_score, user_date;

    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_statistics);

        SharedPreferences prefs = this.getSharedPreferences("AGH-Floors", Context.MODE_PRIVATE);
        if (prefs.contains("uniqueID")) {
            String score = String.valueOf(prefs.getInt("lastScore", 0));

            //TODO: Update score in db
        }

        recycledView = findViewById(R.id.recycledView);
        mDatabaseHelper = new DatabaseHelper(StatisticsActivity.this);
        user_id = new ArrayList<>();
        user_name = new ArrayList<>();
        user_score = new ArrayList<>();
        user_date = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(StatisticsActivity.this, user_id, user_name, user_score, user_date);
        recycledView.setAdapter(customAdapter);
        recycledView.setLayoutManager(new LinearLayoutManager(StatisticsActivity.this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recycledView);

    }




    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            String remove_id = user_id.get(position);
            user_id.remove(position);
            user_name.remove(position);
            user_score.remove(position);
            user_date.remove(position);
            customAdapter.notifyItemRemoved(position);
            mDatabaseHelper.removeData(remove_id);

        }
    };

    private void storeDataInArrays() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        Cursor cursor = mDatabaseHelper.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                user_id.add(cursor.getString(0));
                user_name.add(cursor.getString(1));
                user_score.add(cursor.getString(2));
                user_date.add(cursor.getString(3));
            }
        }
    }

}