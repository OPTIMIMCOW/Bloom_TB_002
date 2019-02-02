package com.example.kennethallan.Bloom_TB_002;

import android.content.Intent;
import android.content.RestrictionEntry;
import android.database.Cursor;
import android.nfc.Tag;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.RunnableScheduledFuture;

import javax.annotation.meta.When;

// insert activity has a lot of stuff from the tutorial but also is where activities are set into the database.
// TODO Transfer adding activities to another activity just for clarity and have a summary of the activities there. Essentially copy the add themes activity.
public class MainActivity extends AppCompatActivity implements Fragment_Output_12.interface_Frag12,Fragment_Output_11.interface_Frag11,
        Fragment_Output_10.interface_Frag10,Fragment_Output_09.interface_Frag09,Fragment_Output_08.interface_Frag08,
        Fragment_Output_07.interface_Frag07,Fragment_Output_06.interface_Frag06,Fragment_Output_05.interface_Frag05,
        Fragment_Output_04.interface_Frag04, Fragment_Output_03.interface_Frag03 {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_MESSAGE = "com.example.kennethallan.testbuildofsqllightdatabase_01.MESSAGE";

    DBHelper Mydb;

    // variables to do with the countdown
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
    TextView tv_sessionDate;
    Calendar c_sessionEndDate;

    // OUTPUT FRAGMENT

    ListView sessionActivitiesListView;

    Context context;

    public String BUNDLE_NAME = "";
    public String BUNDLE_GOAL = "";
    public String BUNDLE_ATTAIN = "";
    public String BUNDLE_SCALEFACTOR = "";
    public String BUNDLE_SUMMARYTOGGLE = "";
    public String BUNDLE_COLOURSEQUENCE = "";

    // fragment stuff
    View fragmentHolder;
    List<Boolean> checkBoxValues = new ArrayList<>();

    // use these to store the activity values for each theme as a list to send to an adapter later.
    ArrayList<String> al_activitiesList_Name = new ArrayList<String>();
    ArrayList<String> al_activitiesList_Description = new ArrayList<String>();
    ArrayList<String> al_activitiesList_Date = new ArrayList<String>();
    ArrayList<String> al_activitiesList_ID = new ArrayList<String>();

    ArrayList<String> al_activitiesList_01 = new ArrayList<String>();
    ArrayList<String> al_activitiesList_02 = new ArrayList<String>();
    ArrayList<String> al_activitiesList_03 = new ArrayList<String>();
    ArrayList<String> al_activitiesList_04 = new ArrayList<String>();
    ArrayList<String> al_activitiesList_05 = new ArrayList<String>();
    ArrayList<String> al_activitiesList_06 = new ArrayList<String>();
    ArrayList<String> al_activitiesList_07 = new ArrayList<String>();
    ArrayList<String> al_activitiesList_08 = new ArrayList<String>();
    ArrayList<String> al_activitiesList_09 = new ArrayList<String>();
    ArrayList<String> al_activitiesList_10 = new ArrayList<String>();
    ArrayList<String> al_activitiesList_11 = new ArrayList<String>();
    ArrayList<String> al_activitiesList_12 = new ArrayList<String>();

    ArrayList<String> al_values_ThemeNames = new ArrayList<String>();
    ArrayList<Integer> al_values_ProGoals = new ArrayList<Integer>();
    ArrayList<Integer> al_values_ProAttain = new ArrayList<Integer>();
    Double i_ScaleFactor;
    Integer currentThemeNum;

    TextView tv_ErrorMessage;
    Bundle sis;
    Bundle currentWeekBundle;
    ArrayList<Integer> al_ColourSequence;
    String String_ColourSequence;

    FloatingActionButton fab_AddActivity;
    FloatingActionButton fab_StartSummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /* Stetho */
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Log.d(TAG, "In OnCreate()");

        // initialise the DB helper
        Mydb = new DBHelper(this); // can call because it is a public class in the package

        sis = savedInstanceState; // make an object so we can use in loading fragement from a method
        currentWeekBundle = new Bundle(); // initalise bundle so that we can populate with values for fragment

        /////////////////////// SET UP TOOLBAR /////////////////////
        // need this to enable overrides to link the overflow menu to it.
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(getResources().getString(R.string.ActivityTitle_CurrentPogress));
        setSupportActionBar(myToolbar);


        /////////////////////////////////// TIMER ///////////////////////////////////////////

        // implement countdown functionality
        tv_sessionDate = (TextView) findViewById(R.id.tv_date);
        tv_Countdown_Days = (TextView) findViewById(R.id.tv_countdown_days);
        tv_Countdown_Hours = (TextView) findViewById(R.id.tv_countdown_hours);
        tv_Countdown_Minutes = (TextView) findViewById(R.id.tv_countdown_minutes);
        tv_Countdown_Seconds = (TextView) findViewById(R.id.tv_countdown_seconds);
        ////// TIMER SETTING DONE IN ONSTART

        //////////////////////////////////// COLOUR //////////////////////////////////////////
        // COLOUR SET IN ONSTART


        ///////////////////////////////////OUTPUT FRAGMENT ///////////////////////////////////

        context = MainActivity.this;
        Mydb = new DBHelper(this);

        // find and bind variables to views
        sessionActivitiesListView = (ListView)findViewById(R.id.lv_SessionActivities);
        fragmentHolder = findViewById(R.id.Fragment_Holder);
        tv_ErrorMessage = (TextView) findViewById(R.id.tv_ErrorMessage);
        tv_ErrorMessage.setVisibility(View.INVISIBLE); // hide error message to make sure there is no error message if there is no error by default.


        ////////////////////////////////BUTTON LISTENERS /////////////////////////////////

        /////////////////////////// OPEN TO SET GOALS ACTIVITY ///////////////////////////////
        fragmentHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SetGoals.class);
                intent.putExtras(currentWeekBundle); // adding bundle to the method to set progressbar values
                startActivity(intent);
            }
        });

        /////////////////////////// OPEN ADD ACTIVITY ACTIVITY ///////////////////////////
        fab_AddActivity = (FloatingActionButton) findViewById(R.id.fab);
        fab_AddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mydb.close(); // use this to close this database and when this is subsequently greyed out and
                //              //the code runs again a new database will be created.**********REMEMBER TO INCREMENT THE DATABASE NAME TO AVOID REPEAT NAME CONFLICK

                Intent intent = new Intent(MainActivity.this,AddEvent.class);
                startActivity(intent);
            }
        });

        /////////////////////////// START MAKE SUMMARY BUTTON ///////////////////////////
        fab_StartSummary = (FloatingActionButton) findViewById(R.id.fab2);
        fab_StartSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mydb.close(); // use this to close this database and when this is subsequently greyed out and
                //              //the code runs again a new database will be created.**********REMEMBER TO INCREMENT THE DATABASE NAME TO AVOID REPEAT NAME CONFLICK
                Intent intent = new Intent(MainActivity.this,Summary.class);
                currentWeekBundle.putBoolean(BUNDLE_SUMMARYTOGGLE,true);
                intent.putExtras(currentWeekBundle); // adding bundle to the method to set progressbar values
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        ////////////////////////////////////  PREPARE COUNTDOWN TIMER //////////////////////////////////////////

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

        // set fab visibility
        fabInvisibilityToggle(mTimerRunning); // turn on the correct fabs given the situation

        ////////////////////////// GET COLOUR SEQUENCE ///////////////////////////////////////

        // look to shared preferences to get colour sequence, if you dont get one make a default mapping, otherwise make a JSON and parse to an arraylist to be used in the app later.
        Integer maxThemeNum = Mydb.getMaxNumThemesSupported(); // used for colour sequence only
        al_ColourSequence = new ArrayList<Integer>();
        String_ColourSequence = prefs.getString(getResources().getString(R.string.SPreferencesColourJSON), "");

        if (String_ColourSequence == ""){
            // make default array
            for (int i = 0; i<maxThemeNum; i++){
                al_ColourSequence.add(i);
            }

        }else { // else make the colour arraylist from the string in shared prefferences

            try {
                JSONObject JSON_ColourSequence = new JSONObject(String_ColourSequence); // initialise the json
                for (int i = 0; i<maxThemeNum; i++){
                    al_ColourSequence.set(i, JSON_ColourSequence.getInt(Integer.toString(i))); // convert json to arraylist
                }

            }catch (Exception e){
                // reset back to defalt if there is an error

                al_ColourSequence.clear(); // in case error on value mid list
                for (int i = 0; i<maxThemeNum; i++){ // create default
                    al_ColourSequence.add(i);
                }
            }

            ArrayList<Integer> colourCheck = al_ColourSequence;

            // final check that the arraylist is ok
            if (al_ColourSequence.size()!=maxThemeNum){
                Log.d("Main Activity","default colour array - had an array that was less than 12 elements so created");
                al_ColourSequence.clear(); // in case error on value mid list
                // make default array
                for (int i = 0; i<maxThemeNum; i++){
                    al_ColourSequence.add(i);
                }

            }

        }



        ///////////////////////////////// CREATE BUNDLES /////////////////////////////////////


        // check for errors in the database
        JSONObject dbErrorCheck = Mydb.CheckDB();
        Integer error = 0;
        Integer type = 0;
        String note = "No Error";
        try {
            error = (Integer) dbErrorCheck.get("error");
            type = (Integer) dbErrorCheck.get("type");
            note = (String) dbErrorCheck.get("note");

        }catch(Exception e){

        }


        BUNDLE_NAME = getResources().getString(R.string.bundle_name);
        BUNDLE_GOAL = getResources().getString(R.string.bundle_goal);
        BUNDLE_ATTAIN = getResources().getString(R.string.bundle_attain);
        BUNDLE_SCALEFACTOR = getResources().getString(R.string.bundle_scalefactor);
        BUNDLE_SUMMARYTOGGLE = getResources().getString(R.string.bundle_summarytoggle);
        BUNDLE_COLOURSEQUENCE = getResources().getString(R.string.bundle_coloursequence);

        // erase arrays in case they hold variables from last time activity used.(not sure if reqquired) TODO test this
        al_values_ThemeNames.clear();
        al_values_ProGoals.clear();
        al_values_ProAttain.clear();
        i_ScaleFactor = 0.00;


        // run rest of code to decide whether to build bundles and thus load fragments. If no error was recieved from the getCURRENTGoals() method.

        if (error == 1) {
            // theme error

            if (type <3) {
                // theme number = 0


                al_values_ThemeNames = getCurrentThemeNames();
                currentThemeNum = type; // manual override so that we dont build a bundle and dont need to call any of the following methods
//                al_values_ProGoals = getCURRENTGoals();
//                al_values_ProAttain = getCURRENTSessionProgress();
//                i_ScaleFactor = getScaleFactor();
                //TODO check of this works and delete if not required.

            }
        }else{
            // no errors or errors which can be handles by the try catches in the bundles
            al_values_ThemeNames = getCurrentThemeNames();
            currentThemeNum = al_values_ThemeNames.size(); // used to build arrays of correct size
            al_values_ProGoals = getCURRENTGoals();
            al_values_ProAttain = getCURRENTSessionProgress();
            i_ScaleFactor = getScaleFactor();

        }




        //////////////////////////////////POPULATE BUNDLES //////////////////////////////////
        //add names
        ArrayList<String> al_Bundle_Name = new ArrayList<String>(); // initialise arraylist to attach to bundle
        for (int i =0; i<currentThemeNum;i++){       // build arraylist
            try{
                al_Bundle_Name.add(al_values_ThemeNames.get(i));
            }catch (Exception e){
                al_Bundle_Name.add("NoTheme");
            }
        }
        currentWeekBundle.putStringArrayList(BUNDLE_NAME,al_Bundle_Name); // attach to bundle

        //add goal values
        ArrayList<Integer> al_Bundle_GoalVal = new ArrayList<Integer>();
        for (int i =0; i<currentThemeNum;i++){
            try{
                al_Bundle_GoalVal.add(al_values_ProGoals.get(i));
            }catch (Exception e){
                al_Bundle_GoalVal.add(0);
            }
        }
        currentWeekBundle.putIntegerArrayList(BUNDLE_GOAL,al_Bundle_GoalVal);

        //add attain values
        ArrayList<Integer> al_Bundle_AttainVal = new ArrayList<Integer>(); // initialise array
        for (int i =0; i<currentThemeNum;i++){
            try{
                al_Bundle_AttainVal.add(al_values_ProAttain.get(i));
            }catch (Exception e){
                al_Bundle_AttainVal.add(0);
            }
        }
        currentWeekBundle.putIntegerArrayList(BUNDLE_ATTAIN,al_Bundle_AttainVal);

        // add scale factor
        currentWeekBundle.putDouble(BUNDLE_SCALEFACTOR,i_ScaleFactor);

        // add default summary toggle (false)
        currentWeekBundle.putBoolean(BUNDLE_SUMMARYTOGGLE,false);

        // add colour sequence to bundle
        currentWeekBundle.putIntegerArrayList(BUNDLE_COLOURSEQUENCE,al_ColourSequence);


        //////////////////////////////////////////// LOAD OUTPUT FRAGEMENT /////////////////////////////////////
        Set check = currentWeekBundle.keySet();
        ArrayList<String> namecheck2 = currentWeekBundle.getStringArrayList(getResources().getString(R.string.bundle_name));
        loadOutputFragment(sis,currentWeekBundle,false);


        ////////////////////////////////////////////// ADD FRAGEMENT MASK //////////////////////////////////////
        // check the current session is running and if not grey out area.
        // Will only change outside of activity so ok to only assess in oncreate.
        if (mTimerRunning == false){
            View session_Overlay = findViewById(R.id.overlay);
            session_Overlay.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /////////////////////////OVERRIDE TOOLBAR SETUP ////////////////////////////////
    // overrride toolbar setup to introduce overflow menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.summary_toolbar_menu, menu);

        MenuItem item= menu.findItem(R.id.action_EndSession);

        return true;
    }

    // essentially the on click listener for the overflow menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_EndSession: {
                Intent intent = new Intent(MainActivity.this,Summary.class);
                currentWeekBundle.putBoolean(BUNDLE_SUMMARYTOGGLE,true);
                intent.putExtras(currentWeekBundle); // adding bundle to the method to set progressbar values
                startActivity(intent);
                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return true;
    }


    ///////////////////////// FAB VISIBILITY SWITCH ///////////////////////////////////
    public void fabInvisibilityToggle(boolean tog){

        // toggle value based on mtimerrunning variable

        if (tog){
            // timer running
            fab_AddActivity.setVisibility(View.VISIBLE);
            fab_StartSummary.setVisibility(View.INVISIBLE);
        }else{
            fab_AddActivity.setVisibility(View.INVISIBLE);
            fab_StartSummary.setVisibility(View.VISIBLE);
        }

    }



    //////////////////////////////////LOADING FRAGEMENT //////////////////////////////
    public void loadOutputFragment(Bundle savedInstanceState, Bundle currentWeekBundle, boolean replace) {

        if (fragmentHolder != null) {

            if (savedInstanceState != null) {
                return;
                // this is like an exit if something has gone wrong for this to load????
            }

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (currentThemeNum == 12) {
                Fragment_Output_12 myFragment = new Fragment_Output_12();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();

            }
            if (currentThemeNum == 11) {
                Fragment_Output_11 myFragment = new Fragment_Output_11();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (currentThemeNum == 10) {
                Fragment_Output_10 myFragment = new Fragment_Output_10();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (currentThemeNum == 9) {
                Fragment_Output_09 myFragment = new Fragment_Output_09();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (currentThemeNum == 8) {
                Fragment_Output_08 myFragment = new Fragment_Output_08();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (currentThemeNum == 7) {
                Fragment_Output_07 myFragment = new Fragment_Output_07();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (currentThemeNum == 6) {
                Fragment_Output_06 myFragment = new Fragment_Output_06();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (currentThemeNum == 5) {
                Fragment_Output_05 myFragment = new Fragment_Output_05();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (currentThemeNum == 4) {
                Fragment_Output_04 myFragment = new Fragment_Output_04();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (currentThemeNum == 3) {
                Fragment_Output_03 myFragment = new Fragment_Output_03();
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

    @Override
    public void onMessageRead(List<Boolean> message) {
        checkBoxValues.clear();
        checkBoxValues = message;

    }


    public ArrayList<Integer> getCURRENTGoals(){

        // this method should only be called knowing there are no errors in the

        // this fetches the values as an arraylist of Strings
        ArrayList<String> al_temp = Mydb.getGoals_CURRENT();
        ArrayList<Integer> al_returnValues = new ArrayList<Integer>();

        // TODO swap out the .size() method below for a if == 0 or null to avoid problems with error database

         for (int i =0; i<al_temp.size();i++){
             try {
                 al_returnValues.add(Integer.parseInt(al_temp.get(i)));
             }catch (Exception e){
                 al_returnValues.add(0);
             }// try catch used here for rare case where array.size != to the number of indexes.
        }

        return al_returnValues;
    }

    public ArrayList<String> getCurrentThemeNames(){
        return Mydb.getCURRENTThemeNames();
    }

    public ArrayList<Integer> getCURRENTSessionProgress (){



        if (currentThemeNum==0){
            Toast.makeText(MainActivity.this, "You have no Current themes to review", Toast.LENGTH_SHORT).show();
            //TODO check if this works
        }

        // get values of activities from database
        Cursor res = Mydb.getAllActivities();
        // trying to move to last row
        res.moveToFirst();

        Integer sum01 = 0;
        Integer sum02 = 0;
        Integer sum03 = 0;
        Integer sum04 = 0;
        Integer sum05 = 0;
        Integer sum06 = 0;
        Integer sum07 = 0;
        Integer sum08 = 0;
        Integer sum09 = 0;
        Integer sum10 = 0;
        Integer sum11 = 0;
        Integer sum12 = 0;

        al_activitiesList_Name.clear();
        al_activitiesList_Description.clear();
        al_activitiesList_Date.clear();
        al_activitiesList_ID.clear();
        al_activitiesList_01.clear();
        al_activitiesList_02.clear();
        al_activitiesList_03.clear();
        al_activitiesList_04.clear();
        al_activitiesList_05.clear();
        al_activitiesList_06.clear();
        al_activitiesList_07.clear();
        al_activitiesList_08.clear();
        al_activitiesList_09.clear();
        al_activitiesList_10.clear();
        al_activitiesList_11.clear();
        al_activitiesList_12.clear();

        // get date of the last current goal /// NOTE: required Mydb.getGoals_Current to run before.
        Long goalDate = Long.parseLong(Mydb.getGoalStartDate());

        ArrayList<String> arrayList_arrayCURRENTthemeIDS = Mydb.getCURRENTThemeIDs(); // get list of current theme ids

        while(res.isAfterLast() == false){

            Long activityDate = 0L;
            try{
                activityDate = Long.parseLong(res.getString(res.getColumnIndex(Mydb.COL4_ACTIVITIES)));
            }catch(Exception e){
                Log.d("Summary","Likely no date found");
                activityDate = 0L;
            }

            if (activityDate < goalDate){
                res.moveToNext();
            }else{

                switch (currentThemeNum){

                    case 3:
                        sum01 = sum01 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        sum02 = sum02 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        sum03 = sum03 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));

                        buildArrays(res);

                        al_activitiesList_01.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        al_activitiesList_02.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        al_activitiesList_03.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        break;

                    case 4:
                        sum01 = sum01 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        sum02 = sum02 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        sum03 = sum03 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        sum04 = sum04 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));

                        buildArrays(res);

                        al_activitiesList_01.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        al_activitiesList_02.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        al_activitiesList_03.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        al_activitiesList_04.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        break;

                    case 5:
                        sum01 = sum01 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        sum02 = sum02 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        sum03 = sum03 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        sum04 = sum04 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        sum05 = sum05 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));


                        // TODO change this back.
                        buildArrays(res);

                        al_activitiesList_01.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        al_activitiesList_02.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        al_activitiesList_03.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        al_activitiesList_04.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        al_activitiesList_05.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        break;

                    case 6:
                        sum01 = sum01 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        sum02 = sum02 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        sum03 = sum03 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        sum04 = sum04 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        sum05 = sum05 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        sum06 = sum06 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        // TODO change this back.


                        buildArrays(res);

                        al_activitiesList_01.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        al_activitiesList_02.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        al_activitiesList_03.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        al_activitiesList_04.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        al_activitiesList_05.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        al_activitiesList_06.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        break;

                    case 7:
                        sum01 = sum01 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        sum02 = sum02 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        sum03 = sum03 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        sum04 = sum04 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        sum05 = sum05 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        sum06 = sum06 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        sum07 = sum07 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));

                        buildArrays(res);

                        al_activitiesList_01.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        al_activitiesList_02.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        al_activitiesList_03.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        al_activitiesList_04.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        al_activitiesList_05.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        al_activitiesList_06.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        al_activitiesList_07.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));
                        break;

                    case 8:
                        sum01 = sum01 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        sum02 = sum02 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        sum03 = sum03 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        sum04 = sum04 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        sum05 = sum05 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        sum06 = sum06 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        sum07 = sum07 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));
                        sum08 = sum08 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(7))));

                        buildArrays(res);

                        al_activitiesList_01.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        al_activitiesList_02.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        al_activitiesList_03.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        al_activitiesList_04.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        al_activitiesList_05.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        al_activitiesList_06.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        al_activitiesList_07.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));
                        al_activitiesList_08.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(7))));
                        break;

                    case 9:
                        sum01 = sum01 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        sum02 = sum02 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        sum03 = sum03 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        sum04 = sum04 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        sum05 = sum05 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        sum06 = sum06 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        sum07 = sum07 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));
                        sum08 = sum08 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(7))));
                        sum09 = sum09 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(8))));

                        buildArrays(res);

                        al_activitiesList_01.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        al_activitiesList_02.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        al_activitiesList_03.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        al_activitiesList_04.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        al_activitiesList_05.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        al_activitiesList_06.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        al_activitiesList_07.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));
                        al_activitiesList_08.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(7))));
                        al_activitiesList_09.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(8))));
                        break;

                    case 10:
                        // looking up each summing the values of the SQLite database
                        sum01 = sum01 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        sum02 = sum02 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        sum03 = sum03 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        sum04 = sum04 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        sum05 = sum05 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        sum06 = sum06 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        sum07 = sum07 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));
                        sum08 = sum08 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(7))));
                        sum09 = sum09 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(8))));
                        sum10 = sum10 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(9))));

                        buildArrays(res);

                        al_activitiesList_01.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        al_activitiesList_02.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        al_activitiesList_03.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        al_activitiesList_04.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        al_activitiesList_05.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        al_activitiesList_06.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        al_activitiesList_07.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));
                        al_activitiesList_08.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(7))));
                        al_activitiesList_09.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(8))));
                        al_activitiesList_10.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(9))));
                        break;

                    case 11:
                        // looking up each summing the values of the SQLite database
                        sum01 = sum01 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        sum02 = sum02 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        sum03 = sum03 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        sum04 = sum04 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        sum05 = sum05 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        sum06 = sum06 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        sum07 = sum07 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));
                        sum08 = sum08 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(7))));
                        sum09 = sum09 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(8))));
                        sum10 = sum10 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(9))));
                        sum11 = sum11 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(10))));

                        buildArrays(res);

                        al_activitiesList_01.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        al_activitiesList_02.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        al_activitiesList_03.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        al_activitiesList_04.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        al_activitiesList_05.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        al_activitiesList_06.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        al_activitiesList_07.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));
                        al_activitiesList_08.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(7))));
                        al_activitiesList_09.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(8))));
                        al_activitiesList_10.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(9))));
                        al_activitiesList_11.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(10))));
                        break;

                    case 12:
                        // looking up each summing the values of the SQLite database
                        sum01 = sum01 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        sum02 = sum02 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        sum03 = sum03 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        sum04 = sum04 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        sum05 = sum05 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        sum06 = sum06 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        sum07 = sum07 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));
                        sum08 = sum08 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(7))));
                        sum09 = sum09 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(8))));
                        sum10 = sum10 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(9))));
                        sum11 = sum11 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(10))));
                        sum12 = sum12 + Integer.parseInt(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(11))));

                        buildArrays(res);

                        al_activitiesList_01.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(0))));
                        al_activitiesList_02.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(1))));
                        al_activitiesList_03.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(2))));
                        al_activitiesList_04.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(3))));
                        al_activitiesList_05.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(4))));
                        al_activitiesList_06.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(5))));
                        al_activitiesList_07.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(6))));
                        al_activitiesList_08.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(7))));
                        al_activitiesList_09.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(8))));
                        al_activitiesList_10.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(9))));
                        al_activitiesList_11.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(10))));
                        al_activitiesList_12.add(res.getString(res.getColumnIndex(Mydb.COLUMNPREFIX+ arrayList_arrayCURRENTthemeIDS.get(11))));
                        break;
                }

                res.moveToNext();
            }


        }

        // create arraylist of summed values so that we can use it in the custom array.
        ArrayList<Integer> al_attainValues = new ArrayList<Integer>();
        al_attainValues.clear();
        switch (currentThemeNum){

            case 3:
                al_attainValues.add(sum01);
                al_attainValues.add(sum02);
                al_attainValues.add(sum03);
                break;

            case 4:
                al_attainValues.add(sum01);
                al_attainValues.add(sum02);
                al_attainValues.add(sum03);
                al_attainValues.add(sum04);
                break;

            case 5:
                al_attainValues.add(sum01);
                al_attainValues.add(sum02);
                al_attainValues.add(sum03);
                al_attainValues.add(sum04);
                al_attainValues.add(sum05);
                break;

            case 6:
                al_attainValues.add(sum01);
                al_attainValues.add(sum02);
                al_attainValues.add(sum03);
                al_attainValues.add(sum04);
                al_attainValues.add(sum05);
                al_attainValues.add(sum06);
                break;
            case 7:
                al_attainValues.add(sum01);
                al_attainValues.add(sum02);
                al_attainValues.add(sum03);
                al_attainValues.add(sum04);
                al_attainValues.add(sum05);
                al_attainValues.add(sum06);
                al_attainValues.add(sum07);
                break;

            case 8:
                al_attainValues.add(sum01);
                al_attainValues.add(sum02);
                al_attainValues.add(sum03);
                al_attainValues.add(sum04);
                al_attainValues.add(sum05);
                al_attainValues.add(sum06);
                al_attainValues.add(sum07);
                al_attainValues.add(sum08);
                break;

            case 9:
                al_attainValues.add(sum01);
                al_attainValues.add(sum02);
                al_attainValues.add(sum03);
                al_attainValues.add(sum04);
                al_attainValues.add(sum05);
                al_attainValues.add(sum06);
                al_attainValues.add(sum07);
                al_attainValues.add(sum08);
                al_attainValues.add(sum09);
                break;

            case 10:
                al_attainValues.add(sum01);
                al_attainValues.add(sum02);
                al_attainValues.add(sum03);
                al_attainValues.add(sum04);
                al_attainValues.add(sum05);
                al_attainValues.add(sum06);
                al_attainValues.add(sum07);
                al_attainValues.add(sum08);
                al_attainValues.add(sum09);
                al_attainValues.add(sum10);
                break;

            case 11:
                al_attainValues.add(sum01);
                al_attainValues.add(sum02);
                al_attainValues.add(sum03);
                al_attainValues.add(sum04);
                al_attainValues.add(sum05);
                al_attainValues.add(sum06);
                al_attainValues.add(sum07);
                al_attainValues.add(sum08);
                al_attainValues.add(sum09);
                al_attainValues.add(sum10);
                al_attainValues.add(sum11);
                break;

            case 12:
                al_attainValues.add(sum01);
                al_attainValues.add(sum02);
                al_attainValues.add(sum03);
                al_attainValues.add(sum04);
                al_attainValues.add(sum05);
                al_attainValues.add(sum06);
                al_attainValues.add(sum07);
                al_attainValues.add(sum08);
                al_attainValues.add(sum09);
                al_attainValues.add(sum10);
                al_attainValues.add(sum11);
                al_attainValues.add(sum12);
                break;
        }

        return al_attainValues;
    }

    public Double getScaleFactor(){
        Integer temp = 0;
        // find maximm value
        for (int i = 0; i<al_values_ProGoals.size();i++){
            if (temp<al_values_ProGoals.get(i)){
                temp = al_values_ProGoals.get(i);
            }
        }

        // scale to max 90%
        double temp2 = 90.00;

        Double temp3 = temp2/temp;

        // return scale factor for use elsewhere.
        return temp3;

    }

    public void buildArrays(Cursor res){

        al_activitiesList_Name.add(res.getString(res.getColumnIndex(Mydb.COL2_ACTIVITIES)));  //TODO change this from hardcode text here
        al_activitiesList_Description.add(res.getString(res.getColumnIndex(Mydb.COL3_ACTIVITIES))); //TODO change this to "DESCRIPTION" in DBhelper
        al_activitiesList_Date.add(res.getString(res.getColumnIndex(Mydb.COL4_ACTIVITIES))); //TODO change this from hardcode text here
        al_activitiesList_ID.add(res.getString(res.getColumnIndex(Mydb.COL1_ACTIVITIES))); //TODO change this from hardcode text here

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
                Toast.makeText(MainActivity.this, "Finished", Toast.LENGTH_SHORT).show();
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
        hours = (totSeconds %(60*60*24))/(60*60);
        minutes = (totSeconds %(60*60))/(60);
        seconds = (totSeconds %(60));


//        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", days, hours, minutes,seconds);

        tv_Countdown_Days.setText(Integer.toString(days) + "d");
        tv_Countdown_Hours.setText(Integer.toString(hours) + "hr");
        tv_Countdown_Minutes.setText(Integer.toString(minutes) + "m");
        tv_Countdown_Seconds.setText(Integer.toString(seconds)+ "s") ;

    }




    // todo why do we go to onpause after onstart? ANSWER: because phone timed out during debug so it went to onpause. if i keep using my phone like on a real run then it wont do this.

    @Override
    protected void onPause() {
        super.onPause();
        // TODO i think this is unnecessary

//        //////////////////////////////// COLOUR SEQUENCE - SAVE ///////////////////////////////////////////
//        // now to convert from araylist to JSON to string and save in sharef preferences.
//        JSONObject JSON_ColourSequence = new JSONObject();
//        for (int i = 0; i<12; i++){
//            try{
//                JSON_ColourSequence.put(""+i, al_ColourSequence.get(i));
//            }catch (Exception e){
//                Log.d("main activity", "when saving the colour sequence error in building the json");
//            }
//        }
//        String_ColourSequence = JSON_ColourSequence.toString();
//        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(getResources().getString(R.string.SPreferencesColourJSON), String_ColourSequence);
//        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }



    // TODO colours. i think i should control the colours from here and sent them to the fragement using the bundle.
    // i think I ultimately should store the colour mappings to each theme in a table and that this would be easiest to access
    // from the activity. Additionally when a theme is deleted the activity is notified making scrolling through colours easier
    // and just sending the updated bundle to the fragment.

    // TODO: as a test I should create a listarray of colour mappings and see if i can change the drawables in the fragments using this.
    // If this works then move onto creating a table for it in myDB.

}