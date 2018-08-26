package com.example.kennethallan.Bloom_TB_002;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

import com.example.kennethallan.Bloom_TB_002.R;

// insert activity has a lot of stuff from the tutorial but also is where activities are set into the database.
// TODO Add a timer to reset all activities in the summation.
// TODO Transfer adding activities to another activity just for clarity and have a summary of the activities there. Essentially copy the add themes activity.
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_MESSAGE = "com.example.kennethallan.testbuildofsqllightdatabase_01.MESSAGE";

    DBHelper Mydb;
    Context contextWord = this;
    Button addThemeButton;
    Button setGoalButton;
    Button addActivityButton;
    Button makeSummaryButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /* Stetho */
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Log.d(TAG,"In OnCreate()");

        Mydb = new DBHelper(this); // can call because it is a public class in the package

        addThemeButton = (Button) findViewById(R.id.AddThemeButton);
        setGoalButton = (Button) findViewById(R.id.SetGoalButton);
        addActivityButton = (Button) findViewById(R.id.SetActivity);
        makeSummaryButton = (Button) findViewById(R.id.Summary);


        editTheme();
        setGoal();
        addActivity();
        makeSummary();

    }


    // opens a new activity to where themes are set and viewed.
    public void editTheme (){
        addThemeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Mydb.close(); // this is used to recreate the database.
                        Intent intent = new Intent(MainActivity.this,EditThemePage.class);
                        //String message = "Successful Intent";
                        //intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);

                    }
                }
        );
    }

    // opens an activity to where goals are set.
    public void setGoal(){
        setGoalButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,SetGoals.class);
                        //String message = "Successful Intent";
                        //intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);
                    }
                }
        );
    }

    public void addActivity(){
        addActivityButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,AddEvent.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void makeSummary(){
        makeSummaryButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,Summary.class);
                        startActivity(intent);
                    }
                }
        );
    }


}