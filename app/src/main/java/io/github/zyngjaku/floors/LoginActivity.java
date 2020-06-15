package io.github.zyngjaku.floors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.DbDocImpl;
import com.mysql.cj.xdevapi.DocResult;
import com.mysql.cj.xdevapi.JsonNumber;
import com.mysql.cj.xdevapi.JsonString;
import com.mysql.cj.xdevapi.Schema;
import com.mysql.cj.xdevapi.Session;
import com.mysql.cj.xdevapi.SessionFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class LoginActivity extends Activity {

    private static final String TAG = "Login";
    DatabaseHelper mDatabaseHelper;


    private EditText usernameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username_edit_text);
        mDatabaseHelper = new DatabaseHelper(LoginActivity.this);
    }

    private boolean isUsernameValid() {

        return (usernameEditText.getText().toString().length() > 2);
    }

    public void onClickSubmit(View view) {
        if (isUsernameValid()) {
            SharedPreferences prefs = this.getSharedPreferences("AGH-Floors", Context.MODE_PRIVATE);

            String score = String.valueOf(prefs.getInt("lastScore", 0));
            String userID = UUID.randomUUID().toString();
            String userName = usernameEditText.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date dateDate = new Date();
            String userDate = sdf.format(dateDate).toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("uniqueID", userID);
            editor.putString("username",userName);
            editor.putString("date",userDate);
            editor.apply();

            AddData(userName, score, userDate);
            Intent intent = new Intent(LoginActivity.this, StatisticsActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Username should have at least 3 characters!", Toast.LENGTH_LONG).show();
        }

    }


    public void onClickView(View view) {
        Intent intent = new Intent(LoginActivity.this, StatisticsActivity.class);
        startActivity(intent);
    }


    public void AddData(String name, String score,String date) {
        boolean insertData = mDatabaseHelper.addData(name, score, date);

        if (insertData) {
            toastMessage("Youre score has been added!");
        } else {
            toastMessage("Something went wrong...");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}