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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
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
import java.util.Set;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import org.json.JSONObject;

import okhttp3.internal.Util;

public class SetGoals extends AppCompatActivity implements Fragment_Input_12.interface_Frag12,Fragment_Input_11.interface_Frag11,Fragment_Input_10.interface_Frag10,Fragment_Input_09.interface_Frag09,Fragment_Input_08.interface_Frag08,Fragment_Input_07.interface_Frag07,Fragment_Input_06.interface_Frag06,
        Fragment_Input_05.interface_Frag05,Fragment_Input_04.interface_Frag04, Fragment_Input_03.interface_Frag03,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, Dialogue_AddTheme.interface_Dia_AddTheme {



    DBHelper Mydb;
    int numCurrentThemes;

    View fragmentHolder;
    List<Integer> compiledValues = new ArrayList<>();
    Button bn_sessionDate;
    Calendar c_sessionEndDate;

    ArrayList<String> al_Fragment_SliderValues = new ArrayList<String>(); // arraylist for the values extracted from the sliders    
    int goal_InputTime = 0; // to initalise the values
    ArrayList<String> al_Unsaved_GoalValues = new ArrayList<String>(); // arraylist for the factored values  for goal after being combined with the time input. to be saved in the DB database. Used to visualise the time for each theme.
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
    FloatingActionButton fab_Save;

    Bundle sis;
    Bundle currentWeekBundle;

    ArrayList<String> al_Bundle_ThemeNames;
    ArrayList<Integer> al_Bundle_GoalValues;
    ArrayList<Integer> al_Bundle_AttainValues;
    ArrayList<Integer> al_Bundle_ColourSequence;
    // TODO extract bundle somewhere and put these in an array and get rid of the references in this and the custom adapter to the database unnecessarily.


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);

        // pass this to the load input fragment method.
        sis = savedInstanceState;

        Mydb = new DBHelper(this);
        fragmentHolder = findViewById(R.id.Fragment_Holder);

        // TODO change this to a bundle and move it to the bundles bit so i can use the bundle. Base on theme names and program in an option for null.
// TODO delete if works
//        // look though SQLite to fetch number of themes
//        Mydb.getCURRENTThemeNames();
//        numCurrentThemes = Mydb.getNumberOfCURRENTThemeIDs();


        //implement date set functionality
        bn_sessionDate = (Button) findViewById(R.id.bn_date);
        bn_sessionDate.setOnClickListener(new View.OnClickListener() {
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

        /////////////////////// SET UP TOOLBAR /////////////////////
        // need this to enable overrides to link the overflow menu to it.
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(getResources().getString(R.string.ActivityTitle_SetGoals));
        setSupportActionBar(myToolbar);

        /////////////////////// EXTRACT BUNDLE INFORMATION ///////////////////////////////

        currentWeekBundle = getIntent().getExtras(); // get bundle that was attached to the intent that started this activity.
        Set check = currentWeekBundle.keySet();
        ArrayList<String> namecheck = currentWeekBundle.getStringArrayList(getResources().getString(R.string.bundle_name));

        // String Keys
        String BUNDLE_NAME = getResources().getString(R.string.bundle_name);
        String BUNDLE_GOAL = getResources().getString(R.string.bundle_goal);
        String BUNDLE_ATTAIN = getResources().getString(R.string.bundle_attain);
        String BUNDLE_SCALEFACTOR = getResources().getString(R.string.bundle_scalefactor);
        String BUNDLE_SUMMARYTOGGLE = getResources().getString(R.string.bundle_summarytoggle);
        String BUNDLE_COLOURSEQUENCE = getResources().getString(R.string.bundle_coloursequence);

        // Name Bundle
        al_Bundle_ThemeNames = currentWeekBundle.getStringArrayList(BUNDLE_NAME);
        // Goal Bundle (need for change in theme number)
        al_Bundle_GoalValues = currentWeekBundle.getIntegerArrayList(BUNDLE_GOAL);
        // Attain Bundle (need for change in theme number)
        al_Bundle_AttainValues = currentWeekBundle.getIntegerArrayList(BUNDLE_ATTAIN);
        // Summary Toggle
               // unnecessary here
        // Colour Sequence
        al_Bundle_ColourSequence = currentWeekBundle.getIntegerArrayList(BUNDLE_COLOURSEQUENCE);

        ///////////////////////////////////// GET CURRENT THEME NUMBER ////////////////////////////////////////

        if(al_Bundle_ThemeNames==null || al_Bundle_ThemeNames.size()==0){
            numCurrentThemes=0;
        }else{
            numCurrentThemes=al_Bundle_ThemeNames.size();
        }

        // //////////////////////////////////INPUT INTO CUSTOM ADAPTER FOR LIST VIEW //////////////////////////

        // TODO - maybe i should use the bundle here???
        // TODO DELETE if works
//        // Build arraylists for custom adapter
//        ArrayList<String> al_ThemeNames = new ArrayList<String>();
//        al_ThemeNames = Mydb.getCURRENTThemeNames();

        // initial build of theme goals for display in the listview.

        // TODO introduce toggle to prompt whether to populate certain things or not. Maybe unnecessary if it gets value of 0 here????? I think it will get value of 0 and be ok.

        for (int i = 0; i<numCurrentThemes; i++){
            al_Unsaved_GoalValues.add(al_Bundle_GoalValues.get(i).toString()); // convert for Integer array to String array
        }

        themeListView = (ListView)findViewById(R.id.ListViewSetGoals);
        // TODO should we move this to onStart?
        populateListView(); // this method is called to repopulate the list when a theme is added thus it is made into a method.


        // ////////////////////////// FAB FUNCTIONALITY ////////////////////////////////////////
        fab_AddTheme = (FloatingActionButton) findViewById(R.id.fab);
        fab_AddTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogue();
            }
        });

        fab_Save = (FloatingActionButton) findViewById(R.id.fab2);
        fab_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveGoals();
            }
        });


        ////////////////////////// LOAD INPUT FRAGMENT ///////////////////////////////////
        loadInputFragment(savedInstanceState,currentWeekBundle, false); // bundle with week values passed into it.


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
        bn_sessionDate.setText(currentDateString);
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

// TODO why does my dialogue call onstart on this activity? inefficient????

        // TODO the colour sequence comes in through the bundle so unnecessary to fetch from shared prefferences here.
//        ////////////////////////// GET COLOUR SEQUENCE ///////////////////////////////////////
//
//        // look to shared preferences to get colour sequence, if you dont get one make a default mapping, otherwise make a JSON and parse to an arraylist to be used in the app later.
//        al_ColourSequence = new ArrayList<Integer>();
//        String_ColourSequence = prefs.getString(getResources().getString(R.string.SPreferencesColourJSON), "");
//// todo something goes wrong here
//
//        if (String_ColourSequence == ""){
//            // make default array
//            for (int i = 0; i<12; i++){
//                al_ColourSequence.add(i);
//            }
//
//        }else {
//            for (int i = 0; i<12; i++){
//                try {
//                    JSONObject JSON_ColourSequence = new JSONObject(String_ColourSequence);
//                    al_ColourSequence.set(i, JSON_ColourSequence.getInt(Integer.toString(i)));
//                }catch(Exception e){
//                    for (int j = 0; i<12; i++){
//                        // reset back to defalt if there is an error
//                        al_ColourSequence.clear(); // in case error on value mid list
//                        al_ColourSequence.add(j);
//                    }
//                }
//            }
//        }
//
//        ArrayList<Integer> colourCheck = al_ColourSequence;
//        Integer temp = 5;

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // use shared preferences to get timer saved to shared preferences for use when we open and close the app.
    @Override
    protected void onPause() {
        super.onPause();

        /////////////////////////////////TIMER /////////////////////////////////////////
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

        //////////////////////////////// COLOUR SEQUENCE - SAVE ///////////////////////////////////////////
        // now to convert from araylist to JSON to string and save in sharef preferences.
        JSONObject JSON_ColourSequence = new JSONObject();
        for (int i = 0; i<12; i++){
            try{
                JSON_ColourSequence.put(""+i, al_Bundle_ColourSequence.get(i));
            }catch (Exception e){

            }
        }
        String String_ColourSequence = JSON_ColourSequence.toString();
        editor.putString(getResources().getString(R.string.SPreferencesColourJSON), String_ColourSequence);
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

//    // USed to disable the backpress button option.
//    @Override
//    public void onBackPressed() {
//        // super.onBackPressed(); commented this line in order to disable back press
//        //Write your code here
//        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
//    }


    /////////////////////// THESE METHODS ARE FOR ADDING A NEW THEME ////////////////////////////

    // used for creating the dialogue that adds the new theme
    public void openDialogue(){

        Dialogue_AddTheme dialogue_addTheme = new Dialogue_AddTheme();
        dialogue_addTheme.show(getSupportFragmentManager(),"Dialogue: Add Theme Attempt Open");
    }

    // this method is from the dialogue fragment for adding a new theme.
    // this method is what recieves the information from the dialogue.
    @Override
    public void applyTexts(String themeName, String themeDescription){

        // This is where the theme is saved

        boolean isInserted  = Mydb.insertTheme(themeName,themeDescription);

        if (isInserted == true) {
            cancelTimer(); // update counter now that we need to set themes again.
            mTimeLeftInMillis = 0;
            updateCountDownText();
            currentDateString = "Set Deadline"; // update the datestring such that it can be saved
            bn_sessionDate.setText(currentDateString);
            numCurrentThemes = Mydb.getNumberOfCURRENTThemeIDs(); // TODO check if this fixes it ****** needs to be before other things which depend on it.
            updateBundles_Addition(themeName); // update the bundle now so the name is loaded when we refresh the fragement.
            int tag2 = 13; // value larger than max number of themes so that it promps an addition to the arraylist.
            update_Visualise_GoalsValues(tag2); // recreate GoalValues arraylist with correct number of elements so that we can refresh the listview.
            populateListView(); // refresh listview given the number of lists
            // TODO do we need to update the bundle for this????? - NO - WHY?????
            loadInputFragment(sis,currentWeekBundle, true); // passed null as my currentweekvalue bundle so that it defaults to zero when created since no goal has been previously set.
            Toast.makeText(SetGoals.this, "Theme Added", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(SetGoals.this, "Theme Add Failed", Toast.LENGTH_SHORT).show();
        }

    }

    //////////////////////OTHER THINGS ////////////////////////////////
    public void update_Visualise_GoalsValues(int tag){ // TODO needs new name

        if (tag == 13){ // if tag is an actual theme (<12) then cause a delete of that value only
            al_Unsaved_GoalValues.add("0");
        }else{
            al_Unsaved_GoalValues.remove(tag);
        }

    }
    

    public void loadInputFragment(Bundle savedInstanceState, Bundle currentWeekBundle, boolean replace) {

        if (fragmentHolder != null) {
            if (savedInstanceState != null) {
                return;
                // this is like an exit if something has gone wrong for this to load????
            }

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (numCurrentThemes == 12) {
                Fragment_Input_12 myFragment = new Fragment_Input_12();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();

            }
            if (numCurrentThemes == 11) {
                Fragment_Input_11 myFragment = new Fragment_Input_11();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 10) {
                Fragment_Input_10 myFragment = new Fragment_Input_10();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 9) {
                Fragment_Input_09 myFragment = new Fragment_Input_09();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 8) {
                Fragment_Input_08 myFragment = new Fragment_Input_08();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 7) {
                Fragment_Input_07 myFragment = new Fragment_Input_07();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 6) {
                Fragment_Input_06 myFragment = new Fragment_Input_06();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 5) {
                Fragment_Input_05 myFragment = new Fragment_Input_05();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 4) {
                Fragment_Input_04 myFragment = new Fragment_Input_04();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 3) {
                Fragment_Input_03 myFragment = new Fragment_Input_03();
                myFragment.setArguments(currentWeekBundle);
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
        // create goal_InputTime using minutes and hours values from List from Fragment.
        int goal_InputTime_Hours = compiledValues.get(0);
        int goal_InputTime_Minutes = compiledValues.get(1);

        // Remove hours and minutes values from array so only left with seekbar values.
        compiledValues.remove(0);
        compiledValues.remove(0); // twice because arraylist shrinks each time so this way we only erase those first two rows

        // convert from integer array to string array from calulateGoals() TODO see if we can get around changing to a string array since we change it back to int in this method.
        ArrayList<String> al_temp = new ArrayList<String>();
        for (int i =0; i<compiledValues.size();i++){

            al_temp.add(Integer.toString(compiledValues.get(i)));
        }
        al_Fragment_SliderValues = al_temp; //make global variable for use elsewhere.

        // calculate goals every time the fragement is altered now
        goal_InputTime = getFreeTime(goal_InputTime_Hours,goal_InputTime_Minutes);
        if (goal_InputTime == 0){
            return;
        } else {
            calculateGoals(al_Fragment_SliderValues, goal_InputTime); // will error if goal_Input time is == 0
            populateListView();
        }

        fab_Save.setVisibility(View.VISIBLE); // allow save option through button
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

        bn_sessionDate.setText(currentDateString);

    }

    /////////////////////////////// TO DO WITH TESTING FUNCTIONALITY /////////////////////////////////////



    public void SaveGoals(){

        if (goal_InputTime == 0){
            Toast.makeText(SetGoals.this, "CANNOT SET GOAL. Free time must be input" , Toast.LENGTH_SHORT).show();
            return;
        } else {
            String manualSet = "Y"; // String to distinguish this input as manual input
            boolean tempresult = Mydb.insertGoal(al_Unsaved_GoalValues, manualSet);
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


    // get the free time from the edit texts and convert them to one figure in minutes

    public int getFreeTime(int hours,  int minutes){
        double a = 60.0;
        int temp = hours*60 + minutes;
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
        // save to the arraylist to visualise the changes

        al_Unsaved_GoalValues.clear(); // clear all values
        for (int i = 0; i < valuesFromSliders.size(); i++) {
            int factoredGoalTime = (int) Math.round(Integer.parseInt(valuesFromSliders.get(i)) * ratio);
            al_Unsaved_GoalValues.add(Integer.toString(factoredGoalTime));
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

        fab_Save.setVisibility(View.INVISIBLE); // check works
    }

    private void cancelTimer() {
        if (mTimerRunning==true) {
            mCountDownTimer.cancel();
            mTimerRunning = false;
            fab_Save.setVisibility(View.VISIBLE);
            // TODO add visibility of fab2 button change
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

        // stop population of listview if num current themes = 0
        if (numCurrentThemes>0) {

            // TODO does this value for goals automatically update?
            ListAdapter themeListAdapter = new CustomAdaptor_ThemeReview(this, al_Bundle_ThemeNames, al_Unsaved_GoalValues);
            themeListView.setAdapter(themeListAdapter);
        }else{

            // TODO perhaps offer alternative values to display to explain why not loaded?
        }

    }


    public void updateBundles_Addition(String name){

        // used to update the global arraylist of values and the currentWeekBundle so that the next fragement will load correctly.

        String BUNDLE_NAME = getResources().getString(R.string.bundle_name);
        String BUNDLE_GOAL = getResources().getString(R.string.bundle_goal);
        String BUNDLE_ATTAIN = getResources().getString(R.string.bundle_attain);
        String BUNDLE_SCALEFACTOR = getResources().getString(R.string.bundle_scalefactor);
        String BUNDLE_SUMMARYTOGGLE = getResources().getString(R.string.bundle_summarytoggle);
        String BUNDLE_COLOURSEQUENCE = getResources().getString(R.string.bundle_coloursequence);

        // NAMES
        al_Bundle_ThemeNames.add(name); // update arraylist
        currentWeekBundle.putStringArrayList(BUNDLE_NAME,al_Bundle_ThemeNames); // attach to bundle

        // GOALS
        al_Bundle_GoalValues.add(0); // update arraylist
        currentWeekBundle.putIntegerArrayList(BUNDLE_GOAL,al_Bundle_GoalValues); // attach to bundle

        // ATTAIN
        al_Bundle_AttainValues.add(0); // update arraylist
        currentWeekBundle.putIntegerArrayList(BUNDLE_ATTAIN,al_Bundle_AttainValues); // attach to bundle

        // SCALE FACTOR
        // Unchanged

        // SUMMARY TOGGLE
        // Unchanged

        // COLOUR
        // Unnecessary since already an array size = 12.

    }


/////////////////////////////// INNER CLASS - CUSTOM ADAPTER ////////////////////////////////

    class CustomAdaptor_ThemeReview extends ArrayAdapter<String> {

        // need to make new arraylist objects inside this class to not have problems recieving from the other class
        ArrayList<String> al_CA_ThemeName = new ArrayList<>();
        ArrayList<String> al_CA_GoalValue = new ArrayList<>();
        ArrayList<Boolean> al_checkbox_CutomAdapter = new ArrayList<Boolean>(); // TODO find out if we need this


        public CustomAdaptor_ThemeReview( Context context, ArrayList<String>  themeName, ArrayList<String>  view_GoalValue) {
            super(context, R.layout.ca_themelist, themeName);

            // TODO can we do this with our bundle?????? NO Since we need live values and our bundle is not for that.

            this.al_CA_ThemeName = themeName;
            this.al_CA_GoalValue = view_GoalValue;

            // inital build of arraylist. // TODO not sure if this does anything????? I think it doesnt!!!!!!!!!!
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
            tv_title.setText(al_CA_ThemeName.get(position));
            tv_goal.setText(al_CA_GoalValue.get(position));
            bn_ThemeDelete.setTag(position); // set tag so that we can identify the theme when we come to delete it.
            bn_ThemeDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = view.getTag().toString();
                    String deleteName = al_CA_ThemeName.get(Integer.parseInt(tag));
                    String IDtoDelete = Mydb.getSpecificThemeID(deleteName);
                    int isDeleted = Mydb.deleteTheme(IDtoDelete);

                    if (isDeleted > 0) {

                        cancelTimer(); // update counter now that we need to set themes again.
                        mTimeLeftInMillis = 0;
                        updateCountDownText();
                        currentDateString = "No Deadline Set"; // update the datestring such that it can be saved
                        bn_sessionDate.setText(currentDateString);
                        numCurrentThemes = Mydb.getNumberOfCURRENTThemeIDs(); //this updates the variable with the current number of themes. the methods after this depend on this variable.
                        int tag2 = Integer.parseInt(tag);
                        updateBundles_Delete(tag2);
                        update_Visualise_GoalsValues(tag2); // recreate a GoalsArray of the correct size given the change in theme size
                        populateListView(); // repopulate the listview now themes have changed
                        loadInputFragment(sis, currentWeekBundle, true);
                        Toast.makeText(SetGoals.this, "Theme Deleted", Toast.LENGTH_SHORT).show();
                        fab_Save.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(SetGoals.this, "No Themes were Deleted", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            return customView;
        }


        public void updateBundles_Delete(int tag){


            // this method is in the innerclass because it is only used here.
            // This method is used to update the global variables and update the bundle to be used in loading a new fragement

            String BUNDLE_NAME = getResources().getString(R.string.bundle_name);
            String BUNDLE_GOAL = getResources().getString(R.string.bundle_goal);
            String BUNDLE_ATTAIN = getResources().getString(R.string.bundle_attain);
            String BUNDLE_SCALEFACTOR = getResources().getString(R.string.bundle_scalefactor);
            String BUNDLE_SUMMARYTOGGLE = getResources().getString(R.string.bundle_summarytoggle);
            String BUNDLE_COLOURSEQUENCE = getResources().getString(R.string.bundle_coloursequence);

            // NAMES
            al_Bundle_ThemeNames.remove(tag); // update global arraylist
            currentWeekBundle.putStringArrayList(BUNDLE_NAME,al_Bundle_ThemeNames); // attach to bundle

            // GOALS
            al_Bundle_GoalValues.remove(tag); // update global arraylist
            currentWeekBundle.putIntegerArrayList(BUNDLE_GOAL,al_Bundle_GoalValues); // attach to bundle

            // ATTAIN
            al_Bundle_AttainValues.remove(tag); // update arraylist
            currentWeekBundle.putIntegerArrayList(BUNDLE_ATTAIN,al_Bundle_AttainValues); // attach to bundle

            // SCALE FACTOR
            // Unchanged

            // SUMMARY TOGGLE
            // Unchanged

            // COLOUR
            Integer temp = al_Bundle_ColourSequence.get(tag); // identify value to be deleted
            al_Bundle_ColourSequence.remove(tag);             // delete value
            al_Bundle_ColourSequence.add(temp);               // add value back on to the end of the array.
            currentWeekBundle.putIntegerArrayList(BUNDLE_COLOURSEQUENCE,al_Bundle_ColourSequence); // attach to bundle

        }

    }


}
