package com.example.kennethallan.Bloom_TB_002;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
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

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

public class SetGoals extends AppCompatActivity implements Fragment_Input_12.interface_Frag12,Fragment_Input_11.interface_Frag11,Fragment_Input_10.interface_Frag10,Fragment_Input_09.interface_Frag09,Fragment_Input_08.interface_Frag08,Fragment_Input_07.interface_Frag07,Fragment_Input_06.interface_Frag06,
        Fragment_Input_05.interface_Frag05,Fragment_Input_04.interface_Frag04, Fragment_Input_03.interface_Frag03,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, Dialogue_AddTheme.interface_Dia_AddTheme {



    DBHelper Mydb;
    Button TestButton;
    int numCurrentThemes;
    int  goal_InputTime_Hours;
    int  goal_InputTime_Minutes;

    View fragmentHolder;
    List<Integer> compiledValues = new ArrayList<>();
// TODO implement the ability to swap out input fragements as the number of themes is deleted
    TextView tv_sessionDate;
    TextView bn_setDate;
    Calendar c_sessionEndDate;


    ArrayList<String> arrayList_GlobalValues = new ArrayList<String>(); // arraylist for the values extracted from the sliders
    int goal_InputTime = 0; // to initalise the values
    ArrayList<String> arrayList_GoalsValues = new ArrayList<String>(); // arraylist for the factored values  for goal after being combined with the time input. to be saved in the DB database.
    // TODO ****** make this less clomplex with goalsValues and globalValues


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
    String currentDateString;

    // For list adapter
    ListView themeListView;

    FloatingActionButton fab_AddTheme;

    Bundle sis;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);

        sis = savedInstanceState;

        Mydb = new DBHelper(this);
        TestButton = (Button) findViewById(R.id.TestButton_SetGoals);
        fragmentHolder = findViewById(R.id.Fragment_Holder);

        TEST();

        // look though SQLite to fetch number of themes
        Mydb.getCURRENTThemeNames();
        numCurrentThemes = Mydb.getNumberOfCURRENTThemeIDs();

        // load fragment
        loadInputFragment(savedInstanceState, false);

        //implement date set functionality
        bn_setDate = (Button) findViewById(R.id.bn_dateSet);
        tv_sessionDate = (TextView) findViewById(R.id.tv_date);
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



 // //////////////////////////////////INPUT INTO CUSTOM ADAPTER FOR LIST VIEW //////////////////////////
        // Build arraylists for custom adapter
        ArrayList<String> al_ThemeNames = new ArrayList<String>();
        al_ThemeNames = Mydb.getCURRENTThemeNames();

        // inital build of array with placeholder names
        for (int i = 0; i<al_ThemeNames.size(); i++){
            arrayList_GoalsValues.add("No Value");
        }

        themeListView = (ListView)findViewById(R.id.ListViewSetGoals);
        populateListView(); // this method is called to repopulate the list when a theme is added thus it is made into a method.


        // ////////////////////////// FAB FUNCTIONALITY ////////////////////////////////////////
        fab_AddTheme = (FloatingActionButton) findViewById(R.id.fab);
        fab_AddTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogue();
            }
        });

        // TODO finish off adding a theme via this button.

    }

    // used for creating the dialogue that adds the
    public void openDialogue(){

        Dialogue_AddTheme dialogue_addTheme = new Dialogue_AddTheme();
        dialogue_addTheme.show(getSupportFragmentManager(),"Dialogue: Add Theme Attempt Open");
    }

    // this method is from the dialogue fragment for adding a new theme
    @Override
    public void applyTexts(String themeName, String themeDescription){

        boolean isInserted  = Mydb.insertTheme(themeName,themeDescription);

        if (isInserted == true) {
            cancelTimer(); // update counter now that we need to set themes again.
            mTimeLeftInMillis = 0;
            updateCountDownText();
            currentDateString = "No Deadline Set"; // update the datestring such that it can be saved
            tv_sessionDate.setText(currentDateString);
            reinitialiseGoalsValues(); // recreate GoalValues arraylist with correct number of elements so that we can refresh the listview.
            populateListView(); // refresh listview given the number of lists
            numCurrentThemes = Mydb.getNumberOfCURRENTThemeIDs();
            loadInputFragment(sis, true);
            Toast.makeText(SetGoals.this, "Theme Added", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(SetGoals.this, "Theme Add Failed", Toast.LENGTH_SHORT).show();
        }

    }

    public void reinitialiseGoalsValues(){
        // reset arrayList_GlobalValues so we can use repopulate the ListView
        int curSize = Mydb.getCURRENTThemeNames().size();
        arrayList_GoalsValues.clear();
        for (int i = 0;i<curSize;i++ ){
            arrayList_GoalsValues.add("No Value");
        }
    }

    public void loadInputFragment(Bundle s, boolean replace) {

        if (fragmentHolder != null) {
            if (s != null) {
                return;
                // this is like an exit if something has gone wrong for this to load????
            }

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (numCurrentThemes == 12) {
                Fragment_Input_12 myFragment = new Fragment_Input_12();
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();

            }
            if (numCurrentThemes == 11) {
                Fragment_Input_11 myFragment = new Fragment_Input_11();
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 10) {
                Fragment_Input_10 myFragment = new Fragment_Input_10();
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 9) {
                Fragment_Input_09 myFragment = new Fragment_Input_09();
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 8) {
                Fragment_Input_08 myFragment = new Fragment_Input_08();
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 7) {
                Fragment_Input_07 myFragment = new Fragment_Input_07();
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 6) {
                Fragment_Input_06 myFragment = new Fragment_Input_06();
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 5) {
                Fragment_Input_05 myFragment = new Fragment_Input_05();
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 4) {
                Fragment_Input_04 myFragment = new Fragment_Input_04();
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 3) {
                Fragment_Input_03 myFragment = new Fragment_Input_03();
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
        }
    }




// will run whenever something is sent through interface from the inputfragement to activity
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

        // calculate goals every time the fragement is altered now
        goal_InputTime = getFreeTime();
        if (goal_InputTime == 0){
            return;
        } else {
            calculateGoals(arrayList_GlobalValues, goal_InputTime); // will error if goal_Input time is == 0
            populateListView();
        }
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
        currentDateString = DateFormat.getDateInstance().format(c_sessionEndDate.getTime()) + " - " + c_sessionEndDate.get(Calendar.HOUR_OF_DAY) + ":" + c_sessionEndDate.get(Calendar.MINUTE);

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
                        if (goal_InputTime == 0){
                            Toast.makeText(SetGoals.this, "CANNOT SET GOAL. Free time must be input" , Toast.LENGTH_SHORT).show();
                            return;
                        } else {
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

                        // ////////////////// RETURN TO MAIN ACTIVITY //////////////////////////
                        Intent intent = new Intent(SetGoals.this, MainActivity.class);
                        startActivity(intent);


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
        if (mTimerRunning==true) {
            mCountDownTimer.cancel();
            mTimerRunning = false;
        }
    }

    private void updateCountDownText() {
        int totSeconds;
        int days;
        int hours;
        int minutes;
        int seconds;
        totSeconds = (int)(mTimeLeftInMillis / 1000);

        days = totSeconds/(60*60*24);

//        String daysString = Integer.toString(days) + "d";
        hours = (totSeconds %(60*60*24))/(60*60);
        minutes = (totSeconds %(60*60))/(60);
        seconds = (totSeconds %(60));


//        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", days, hours, minutes,seconds);

        tv_Countdown_Days.setText(Integer.toString(days) + "d");
        tv_Countdown_Hours.setText(Integer.toString(hours) + "hr");
        tv_Countdown_Minutes.setText(Integer.toString(minutes) + "m");
        tv_Countdown_Seconds.setText(Integer.toString(seconds)+ "s") ;

    }

    // used for populating the arrayadapter to show themes
    public void populateListView(){

        ArrayList<String> al_ThemeNames = Mydb.getCURRENTThemeNames();
        ListAdapter themeListAdapter = new CustomAdaptor_ThemeReview(this,al_ThemeNames,arrayList_GoalsValues);
        themeListView.setAdapter(themeListAdapter);
    }




    // use shared preferences to get timer saved to shared preferences for use when we open and close the app.

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.putString("dateString", currentDateString);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // TODO make shared prefferences name a constant to be used requested in different activities

        ///////////////////////////////// LOAD TIMER /////////////////////////////////////////////////////////////////
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS); // note this is a default value. it is expected to be overwritten when looking in shared preferences.
        mTimerRunning = prefs.getBoolean("timerRunning", false); // note this is a default value.it is expected to be overwritten when looking in shared preferences.
        currentDateString = prefs.getString("dateString", "");
        tv_sessionDate.setText(currentDateString);
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



    class CustomAdaptor_ThemeReview extends ArrayAdapter<String> {

        // need to make new arraylist objects inside this class to not have problems recieving from the other class
        ArrayList<String> al_ThemeName = new ArrayList<>();
        ArrayList<String> al_GoalValue = new ArrayList<>();
        ArrayList<Boolean> al_checkbox_CutomAdapter = new ArrayList<Boolean>(); // TODO find out if we need this
        int recordFirstCounter = 0; // TODO find out if i need this

        public CustomAdaptor_ThemeReview( Context context, ArrayList<String>  themeName, ArrayList<String>  goalValue) {
            super(context, R.layout.ca_themelist, themeName);

            this.al_ThemeName = themeName;
            this.al_GoalValue = goalValue;

            // inital build of arraylist.
            al_checkbox_CutomAdapter.clear();
            for (int i = 0; i<numCurrentThemes;i++){
                al_checkbox_CutomAdapter.add(i,false);
            }

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = LayoutInflater.from(getContext());
            View customView = Inflater.inflate(R.layout.ca_themelist, parent, false);


            String singleViewItem = getItem(position); //Confirm DOES work. Probably works through the one resource that goes through the super.
            // find relevant views
            TextView tv_title = (TextView) customView.findViewById(R.id.tv_ThemeName);
            TextView tv_goal = (TextView) customView.findViewById(R.id.tv_ThemeGoal);
            Button bn_ThemeDelete = (Button) customView.findViewById(R.id.bn_ThemeDelete);

            //set values to view
            tv_title.setText(al_ThemeName.get(position));
            tv_goal.setText(al_GoalValue.get(position));
            bn_ThemeDelete.setTag(position);
            bn_ThemeDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = view.getTag().toString();
                    String deleteName = al_ThemeName.get(Integer.parseInt(tag));
                    String IDtoDelete = Mydb.getSpecificThemeID(deleteName);
                    int isDeleted = Mydb.deleteTheme(IDtoDelete);
                    //notifyDataSetChanged();

                    if (isDeleted > 0) {

                        cancelTimer(); // update counter now that we need to set themes again.
                        mTimeLeftInMillis = 0;
                        updateCountDownText();
                        currentDateString = "No Deadline Set"; // update the datestring such that it can be saved
                        tv_sessionDate.setText(currentDateString);
                        numCurrentThemes = Mydb.getNumberOfCURRENTThemeIDs();
                        loadInputFragment(sis, true); // TODO why not working?
                        reinitialiseGoalsValues(); // recreate a GoalsArray of the correct size given the change in theme size
                        populateListView(); // repopulate the listview now themes have changed
                        Toast.makeText(SetGoals.this, "Theme Deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SetGoals.this, "No Themes were Deleted", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            //transferValues();

            return customView;
        }

//        public void transferValues(){
//            al_checkbox_Activity = al_checkbox_CutomAdapter;
//        }


    }



}
