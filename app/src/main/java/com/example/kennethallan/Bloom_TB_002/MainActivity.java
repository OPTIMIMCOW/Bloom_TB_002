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
import android.os.CountDownTimer;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

import com.example.kennethallan.Bloom_TB_002.R;

import java.util.Locale;

// insert activity has a lot of stuff from the tutorial but also is where activities are set into the database.
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

    private static final long START_TIME_IN_MILLIS = 100000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mEndTime;
    Button bt_StartSession;


// TODO add timer
// TODO add progress fragment

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

        setGoalButton = (Button) findViewById(R.id.SetGoalButton);
        addActivityButton = (Button) findViewById(R.id.SetActivity);
        makeSummaryButton = (Button) findViewById(R.id.Summary);
        mTextViewCountDown = (TextView) findViewById(R.id.tv_countdown);
        bt_StartSession = (Button) findViewById(R.id.bt_countdownStart);

        setGoal();
        addActivity();
        makeSummary();

        //////////////////////////// FUNCTIONALITY OF TIMER///////////////////////////
        // Button set amount
        bt_StartSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimeLeftInMillis==0){
                    resetTimer();
                }else{
                    startTimer();
                }
            }
        });
        updateCountDownText();

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

    // /////////////////////ALL TO DO WITH THE COUNTDOWN TEXT //////////////////////////////
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimeLeftInMillis = 0;
                updateCountDownText();
                Toast.makeText(MainActivity.this, "Finished", Toast.LENGTH_SHORT).show();
                mTimerRunning = false;

            }
        }.start();

        mTimerRunning = true;
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    private void updateCountDownText() {

            int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
            int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            mTextViewCountDown.setText(timeLeftFormatted);

    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO make shared prefferences name a constant to be used requested in different activities
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // TODO make shared prefferences name a constant to be used requested in different activities
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS); // note this is a default value. it is expected to be overwritten when looking in shared preferences.
        mTimerRunning = prefs.getBoolean("timerRunning", false); // note this is a default value.it is expected to be overwritten when looking in shared preferences.

        updateCountDownText();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0); // note this is a default value. it is expected to be overwritten when looking in shared preferences.
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            // used to make sure textview doesnt display negative values
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
            } else {
                startTimer();
            }
        }

    }
}