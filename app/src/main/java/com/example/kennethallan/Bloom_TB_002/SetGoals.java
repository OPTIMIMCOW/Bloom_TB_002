package com.example.kennethallan.Bloom_TB_002;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SetGoals extends AppCompatActivity implements Fragment_Input_12.interface_Frag12,Fragment_Input_11.interface_Frag11,Fragment_Input_10.interface_Frag10,Fragment_Input_09.interface_Frag09,Fragment_Input_08.interface_Frag08,Fragment_Input_07.interface_Frag07,Fragment_Input_06.interface_Frag06,
        Fragment_Input_05.interface_Frag05,Fragment_Input_04.interface_Frag04, Fragment_Input_03.interface_Frag03, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{



    DBHelper Mydb;
    Button TestButton;
    int numCurrentThemes;
    int  goal_InputTime_Hours;
    int  goal_InputTime_Minutes;

    View fragmentHolder;
    List<Integer> compiledValues = new ArrayList<>();
    //TODO add Textview to set date and a countdown to show how much time was left.
    //TODO add variable to log the date of the next session.
    // TODO introduce scrollview.
    // TODO introduce customarray for the themes which results in a new fragment being loaded.
    TextView tv_sessionDate;
    TextView tv_sessionCountdown;
    TextView bn_setDate;
    Calendar c_sessionEndDate;


    ArrayList<String> arrayList_GlobalValues = new ArrayList<String>(); // arraylist for the values extracted from the sliders
    int goal_InputTime = 0; // to initalise the values
    ArrayList<String> arrayList_GoalsValues = new ArrayList<String>(); // arraylist for the factored values  for goal after being combined with the time input. to be saved in the DB database.

    //TODO figure out if the database is based on current themes or all themes and alter the golasValues arraylist as required to input into SqliteDB.

    // variable to do with the countdown
    private long START_TIME_IN_MILLIS;
    private TextView tv_Countdown_Days;
    private TextView tv_Countdown_Hours;
    private TextView tv_Countdown_Minutes;
    private TextView tv_Countdown_Seconds;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);

        Mydb = new DBHelper(this);
        TestButton = (Button) findViewById(R.id.TestButton_SetGoals);
        fragmentHolder = findViewById(R.id.Fragment_Holder);



        TEST();

        // look though SQLite to fetch number of themes
        Mydb.getCURRENTThemeNames();
        numCurrentThemes = Mydb.getNumberOfCURRENTThemeIDs();

        // load fragment
        if (fragmentHolder != null) {

            if (savedInstanceState != null) {
                return;
            }// this is like an exit if something has gone wrong for this to load????
            if (numCurrentThemes == 12) {
                Fragment_Input_12 myFragment = new Fragment_Input_12();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 11) {
                Fragment_Input_11 myFragment = new Fragment_Input_11();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 10) {
                Fragment_Input_10 myFragment = new Fragment_Input_10();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 9) {
                Fragment_Input_09 myFragment = new Fragment_Input_09();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 8) {
                Fragment_Input_08 myFragment = new Fragment_Input_08();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 7) {
                Fragment_Input_07 myFragment = new Fragment_Input_07();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 6) {
                Fragment_Input_06 myFragment = new Fragment_Input_06();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 5) {
                Fragment_Input_05 myFragment = new Fragment_Input_05();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 4) {
                Fragment_Input_04 myFragment = new Fragment_Input_04();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 3) {
                Fragment_Input_03 myFragment = new Fragment_Input_03();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
        }

        //implement date set functionality
        bn_setDate = (Button) findViewById(R.id.bn_dateSet);
        tv_sessionDate = (TextView) findViewById(R.id.tv_date);
        tv_sessionCountdown = (TextView) findViewById(R.id.tv_countdown);
        bn_setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // date picker fragment
                pickSessionDate();
            }
        });

        // implement countdown functionality

        tv_Countdown_Days = (TextView) findViewById(R.id.tv_countdown_days);
        tv_Countdown_Hours = (TextView) findViewById(R.id.tv_countdown_hours);
        tv_Countdown_Minutes = (TextView) findViewById(R.id.tv_countdown_minutes);
        tv_Countdown_Seconds = (TextView) findViewById(R.id.tv_countdown_seconds);

        tv_Countdown_Days.setText("1");
        tv_Countdown_Hours.setText("2");
        tv_Countdown_Minutes.setText("3");
        tv_Countdown_Seconds.setText("4");

    }





    @Override
    public void onMessageRead(List<Integer> message) {
        // clear old values
//        compiledValues.clear();   // todo how to clear the arraylist since its definiteyl causing problems
        // attach new values to array
        compiledValues = message;
        // create goal_InputTime using minutes and hourse values from List from Fragment.
        goal_InputTime_Hours = compiledValues.get(0);
        goal_InputTime_Minutes = compiledValues.get(1);

        // Remove hours and minutes values from array so only left with seekbar values.
        compiledValues.remove(0);
        compiledValues.remove(0); // twice because arraylist shrinks each time so this way we only erase those first two rows

        // convert from integer array to string array from calulateGoals() TODO see if we can get around changing to a string array since we change it back to int in this method.
        ArrayList<String> al_temp = new ArrayList<String>();
        for (int i =0; i<compiledValues.size();i++){

            al_temp.add(Integer.toString(compiledValues.get(i)));
        }

        arrayList_GlobalValues = al_temp; //make global variable for use elsewhere.
    }


// ///////////////// TO DO WITH SESSION DATE AND TIME SETTING ////////////////////////

    public void pickSessionDate(){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"Open Date Picker");
    }

    // This method recieves the date we picked in the datePicker Fragment
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

        // create a calander object to hold the date we picked in the date picker.
        c_sessionEndDate = Calendar.getInstance();
        c_sessionEndDate.set(Calendar.YEAR,year);
        c_sessionEndDate.set(Calendar.MONTH,month);
        c_sessionEndDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        pickSessionTime();
    }

    public void pickSessionTime(){
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"Open Time Picker");
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
        c_sessionEndDate.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c_sessionEndDate.set(Calendar.MINUTE,minutes);

        // format calander into a string.

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String currentDateString = DateFormat.getDateInstance().format(c_sessionEndDate.getTime()) + " - " + c_sessionEndDate.get(Calendar.HOUR_OF_DAY) + ":" + c_sessionEndDate.get(Calendar.MINUTE);

        tv_sessionDate.setText(currentDateString);

    }

    /////////////////////////////// TO DO WITH TESTING FUNCTIONALITY /////////////////////////////////////

    // TODO rename these variables
    public void TEST(){

        TestButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /////////////////////// SETTING THE GOALS TO THE DATABASE //////////////////
                        goal_InputTime = getFreeTime(); // passing to the global variable. // TODO fix issue that means that you get a goal_nput time = 0 unless slider moved.
                        if (goal_InputTime == 0){
                            Toast.makeText(SetGoals.this, "CANNOT SET GOAL. Free time must be input" , Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            calculateGoals(arrayList_GlobalValues, goal_InputTime);
                            String manualSet = "Y"; // String to distinguish this input as manual input
                            boolean tempresult = Mydb.insertGoal(arrayList_GoalsValues, manualSet);
                            if (tempresult) {
                                Toast.makeText(SetGoals.this, "Succeeded To Input Goals", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SetGoals.this, "Failed To Input Goals", Toast.LENGTH_SHORT).show();

                            }

                            // setting or resetting the timer
                            if (mTimerRunning==true){
                                cancelTimer();
                                loadCountdown();
                                startTimer();
                            }else{
                                loadCountdown();
                                startTimer();
                            }
                            updateCountDownText();


                        }

                        /////////////////////// STARTING THE COUNTDOWN /////////////////////////////




                   }
                }
        );
    }
// get the free time from the edit texts and convert them to one figure in minutes

    public int getFreeTime(){
        double a = 60.0;
        int temp = goal_InputTime_Hours*60 + goal_InputTime_Minutes;
        return temp;
    }

    public void calculateGoals(ArrayList<String> valuesFromSliders,Integer freeTime ) {
        //sum values
        Integer sumNew = 0;
        Integer sumOld = 0;

        for (int i = 0; i < valuesFromSliders.size(); i++) {
            sumNew = Integer.parseInt(valuesFromSliders.get(i));
            sumOld = sumOld + sumNew;
        }

        double max = (double) freeTime;

        double ratio = max / (double) sumOld;
        arrayList_GoalsValues.clear();

        for (int i = 0; i < valuesFromSliders.size(); i++) {
            int factoredGoalTime = (int) Math.round(Integer.parseInt(valuesFromSliders.get(i)) * ratio);
            arrayList_GoalsValues.add(Integer.toString(factoredGoalTime));
        }

    }

    // /////////////////////ALL TO DO WITH THE COUNTDOWN TEXT //////////////////////////////
    private void loadCountdown(){
        START_TIME_IN_MILLIS = c_sessionEndDate.getTimeInMillis() - System.currentTimeMillis();
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
    }

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
                Toast.makeText(SetGoals.this, "Finished", Toast.LENGTH_SHORT).show();
                mTimerRunning = false;

            }
        }.start();

        mTimerRunning = true;
    }

    private void cancelTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }

    private void updateCountDownText() {
        int totSeconds;
        int days;
        int hours;
        int minutes;
        int seconds;
        totSeconds = (int)(mTimeLeftInMillis / 1000);

        days = totSeconds/(60*60*24);
        hours = (totSeconds %(60*60*24))/(60*60);
        minutes = (totSeconds %(60*60))/(60);
        seconds = (totSeconds %(60));


//        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", days, hours, minutes,seconds);

        tv_Countdown_Days.setText(Integer.toString(days) + "d");
        tv_Countdown_Hours.setText(Integer.toString(hours) + "hr");
        tv_Countdown_Minutes.setText(Integer.toString(minutes) + "m");
        tv_Countdown_Seconds.setText(Integer.toString(seconds)+ "s") ;

    }

    // use shared preferences to get timer saved to shared preferences for use when we open and close the app.

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
