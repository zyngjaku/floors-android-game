package io.github.zyngjaku.floors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import java.util.UUID;

public class LoginActivity extends Activity {

    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username_edit_text);
    }

    private boolean isUsernameValid() {

        return (usernameEditText.getText().toString().length() > 2);
    }

    public void onClickSubmit(View view) {
        if (isUsernameValid()) {
            SharedPreferences prefs = this.getSharedPreferences("AGH-Floors", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("uniqueID", UUID.randomUUID().toString());
            editor.putString("username", usernameEditText.getText().toString());
            editor.apply();

            //TODO: Insert do database uniqueID & username & best score dostępne pod
            //prefs.getString("uniqueID", "null");
            //prefs.getString("username", "null");
            //prefs.getInt("bestScore", 0);
        } else {
            Toast.makeText(LoginActivity.this, "Username should have at least 3 characters!", Toast.LENGTH_LONG).show();
        }
    }

    /*
    public void addUserToStatistics() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, "","Please wait...", true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Session session = new SessionFactory().getSession("mysqlx://mysql.agh.edu.pl:33060/zyngier2?user=zyngier2&password=XLyfHv87RCN0QfsF");
                System.err.println("Connected!");

                SignInActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "There are some troubles with server!", Toast.LENGTH_LONG).show();
                    }
                });

                try {
                    //Class.forName("com.mysql.jdbc.Driver");
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:msql://mysql.agh.edu.pl:3306/zyngier2", "zyngier2", "XLyfHv87RCN0QfsF");
                    //Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.2:3306/android", "andro", "andro");

                    Statement st = conn.createStatement();
                    st.executeUpdate("INSERT INTO ranking VALUES (1,'test',1)");
                    conn.close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }


            }
        });

        thread.start();
    }
    */
}