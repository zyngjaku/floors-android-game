package io.github.zyngjaku.floors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "Statistics.db";

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME= "floors_scoring";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";
    private static final String COL_SCORE = "Score";

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable2 = "DROP TABLE IF EXISTS "+TABLE_NAME+";";
        db.execSQL(createTable2); //IF NOT EXISTS
        String createTable = "CREATE TABLE "+TABLE_NAME+" ("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_NAME+" TEXT, "+COL_SCORE+" TEXT);";
        db.execSQL(createTable); //IF NOT EXISTS
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        String command = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(command);
    }

    public boolean addData(String name,String score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_SCORE,score);

        Log.d(TAG,"addData: Adding "+name+ "to "+ TABLE_NAME);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME+ " ORDER BY "+COL_SCORE+ " DESC;";
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

}
