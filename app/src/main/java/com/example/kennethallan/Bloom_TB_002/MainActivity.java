package com.example.kennethallan.Bloom_TB_002;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RunnableScheduledFuture;

// insert activity has a lot of stuff from the tutorial but also is where activities are set into the database.
// TODO Transfer adding activities to another activity just for clarity and have a summary of the activities there. Essentially copy the add themes activity.
public class MainActivity extends AppCompatActivity implements Fragment_Output_12.interface_Frag12,Fragment_Output_11.interface_Frag11,
        Fragment_Output_10.interface_Frag10,Fragment_Output_09.interface_Frag09,Fragment_Output_08.interface_Frag08,
        Fragment_Output_07.interface_Frag07,Fragment_Output_06.interface_Frag06,Fragment_Output_05.interface_Frag05,
        Fragment_Output_04.interface_Frag04, Fragment_Output_03.interface_Frag03 {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_MESSAGE = "com.example.kennethallan.testbuildofsqllightdatabase_01.MESSAGE";

    DBHelper Mydb;
    Button addActivityButton;
    Button makeSummaryButton;

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

    Button bn_CarryOver;
    ListView sessionActivitiesListView;

    Context context;

    public String BUNDLE_NAME = "";
    public String BUNDLE_GOAL = "";
    public String BUNDLE_ATTAIN = "";
    public String BUNDLE_SCALEFACTOR = "";

    Integer numCurrentThemes;

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

    TextView tv_ErrorMessage;
    Bundle sis;
    Bundle currentWeekBundle;

    FloatingActionButton fab_AddActivity;




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

        Log.d(TAG, "In OnCreate()");

        // initialise the DB helper
        Mydb = new DBHelper(this); // can call because it is a public class in the package

        sis = savedInstanceState; // make an object so we can use in loading fragement from a method
        currentWeekBundle = new Bundle(); // initalise bundle so that we can populate with values for fragment


        makeSummaryButton = (Button) findViewById(R.id.Summary);

        makeSummary(); // initialising the method but not actually running without button click.

        //////////////////////////// FUNCTIONALITY OF TIMER///////////////////////////

        // implement countdown functionality
        tv_sessionDate = (TextView) findViewById(R.id.tv_date);
        tv_Countdown_Days = (TextView) findViewById(R.id.tv_countdown_days);
        tv_Countdown_Hours = (TextView) findViewById(R.id.tv_countdown_hours);
        tv_Countdown_Minutes = (TextView) findViewById(R.id.tv_countdown_minutes);
        tv_Countdown_Seconds = (TextView) findViewById(R.id.tv_countdown_seconds);


        ////// TIMER DONE IN ONSTART


        /////////////////////////// FUNCTIONALITY OF OUTPUT FRAGMENT /////////////////////////
        context = MainActivity.this;
        Mydb = new DBHelper(this);

        // find and bind variables to views
        bn_CarryOver = (Button) findViewById(R.id.bn_CarryOver);
        sessionActivitiesListView = (ListView)findViewById(R.id.lv_SessionActivities);
        fragmentHolder = findViewById(R.id.Fragment_Holder);
        tv_ErrorMessage = (TextView) findViewById(R.id.tv_ErrorMessage);
        tv_ErrorMessage.setVisibility(View.INVISIBLE);


        BUNDLE_NAME = getResources().getString(R.string.bundle_name);
        BUNDLE_GOAL = getResources().getString(R.string.bundle_goal);
        BUNDLE_ATTAIN = getResources().getString(R.string.bundle_attain);
        BUNDLE_SCALEFACTOR = getResources().getString(R.string.bundle_scalefactor);


        // erase arrays in case they hold variables from last time activity used.(not sure if reqquired) TODO test this
        al_values_ThemeNames.clear();
        al_values_ProGoals.clear();
        al_values_ProAttain.clear();
        i_ScaleFactor = 0.00;

        al_values_ProGoals = getCURRENTGoals();

        // run rest of code to load the the fragement if no error was recieved from the getCURRENTGoals() method.
        if (al_values_ProGoals.get(0)!=-1){

            al_values_ThemeNames = getCurrentThemeNames();
            al_values_ProAttain = getCURRENTSessionProgress();
            i_ScaleFactor = getScaleFactor();
            Integer maxThemeNum = Mydb.getMaxNumThemesSupported() -1;

            //Pupulate bundle to open fragement using.

            //add names
            for (int i =0; i<maxThemeNum;i++){
                try{
                    currentWeekBundle.putString(BUNDLE_NAME+i,al_values_ThemeNames.get(i));
                }catch (Exception e){
                    currentWeekBundle.putString(BUNDLE_NAME+i,"NoTheme");
                }
            }
            //add goal values
            for (int i =0; i<maxThemeNum;i++){
                try{
                    currentWeekBundle.putInt(BUNDLE_GOAL+i,al_values_ProGoals.get(i));
                }catch (Exception e){
                    currentWeekBundle.putInt(BUNDLE_GOAL+i,0);
                }
            }
            //add attain values
            for (int i =0; i<maxThemeNum;i++){
                try{
                    currentWeekBundle.putInt(BUNDLE_ATTAIN+i,al_values_ProAttain.get(i));
                }catch (Exception e){
                    currentWeekBundle.putInt(BUNDLE_ATTAIN+i,0);
                }
            }
            // add scale factor
            currentWeekBundle.putDouble(BUNDLE_SCALEFACTOR,i_ScaleFactor);


            loadOutputFragment(sis,currentWeekBundle,false); // TODO check if this works. Savedinstance state might be the bundle needed here.


        }

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
                Intent intent = new Intent(MainActivity.this,AddEvent.class);
                startActivity(intent);
            }
        });

    }

    public void loadOutputFragment(Bundle savedInstanceState, Bundle currentWeekBundle,  boolean replace) {

        if (fragmentHolder != null) {

            if (savedInstanceState != null) {
                return;
                // this is like an exit if something has gone wrong for this to load????
            }

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (numCurrentThemes == 12) {
                Fragment_Output_12 myFragment = new Fragment_Output_12();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();

            }
            if (numCurrentThemes == 11) {
                Fragment_Output_11 myFragment = new Fragment_Output_11();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 10) {
                Fragment_Output_10 myFragment = new Fragment_Output_10();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 9) {
                Fragment_Output_09 myFragment = new Fragment_Output_09();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 8) {
                Fragment_Output_08 myFragment = new Fragment_Output_08();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 7) {
                Fragment_Output_07 myFragment = new Fragment_Output_07();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 6) {
                Fragment_Output_06 myFragment = new Fragment_Output_06();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 5) {
                Fragment_Output_05 myFragment = new Fragment_Output_05();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 4) {
                Fragment_Output_04 myFragment = new Fragment_Output_04();
                myFragment.setArguments(currentWeekBundle);
                if (replace == false) {
                    fragmentTransaction.add(R.id.Fragment_Holder, myFragment, null);
                }else{
                    fragmentTransaction.replace(R.id.Fragment_Holder, myFragment, null);
                }
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 3) {
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

        // this fetches the values as an arraylist of Strings
        ArrayList<String> al_temp = Mydb.getGoals_CURRENT();

        ArrayList<Integer> al_returnValues = new ArrayList<Integer>();

    // TODO do an if statement here to catch any errors and prompt a different fragment to be loaded. - or no fragement to be loaded but a textview appear with the error message.
        // check for errors. if error then return value -1. -1 will indicate in oncreate not to load fragement.
        if (al_temp.get(0)=="Order 66"){
            tv_ErrorMessage.setText(al_temp.get(1));
            tv_ErrorMessage.setVisibility(View.VISIBLE);
            al_returnValues.add(-1);
        }else{
            // continue as normal
            // this just converts the string arraylist to an integer arraylist
            for (int i =0; i<al_temp.size();i++){
                al_returnValues.add(Integer.parseInt(al_temp.get(i)));
            }

        }

        return al_returnValues;
    }

    public ArrayList<String> getCurrentThemeNames(){
        return Mydb.getCURRENTThemeNames();
    }

    public ArrayList<Integer> getCURRENTSessionProgress (){

        // get date of the last current goal
        Mydb.getGoals_CURRENT();
        Long goalDate = Long.parseLong(Mydb.getGoalStartDate());

        ArrayList<String> arrayList_arrayCURRENTthemeIDS = Mydb.getCURRENTThemeIDs();
        numCurrentThemes = arrayList_arrayCURRENTthemeIDS.size();


        if (numCurrentThemes==0){
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

        while(res.isAfterLast() == false){

            Long activityDate = 0L;
            try{
                activityDate = Long.parseLong(res.getString(res.getColumnIndex(Mydb.COL4_ACTIVITIES)));
            }catch(Exception e){
                Log.d("Summary","Likely no date found");
                activityDate = 0L;
            }

            if (activityDate<goalDate){
                res.moveToNext();
            }else{

                switch (numCurrentThemes){

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
        switch (numCurrentThemes){

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

    public void makeSummary(){
        makeSummaryButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Mydb.close(); // use this to close this database and when this is subsequently greyed out and
                        //              //the code runs again a new database will be created.**********REMEMBER TO INCREMENT THE DATABASE NAME TO AVOID REPEAT NAME CONFLICK
                        Intent intent = new Intent(MainActivity.this,Summary.class);
                        startActivity(intent);
                    }
                }
        );
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

    @Override
    protected void onStop() {
        super.onStop();
        // TODO make shared prefferences name a constant to be used requested in different activities
//        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//
//        editor.putLong("millisLeft", mTimeLeftInMillis);
//        editor.putBoolean("timerRunning", mTimerRunning);
//        editor.putLong("endTime", mEndTime);
//
//        editor.apply();
//
//        if (mCountDownTimer != null) {
//            mCountDownTimer.cancel();
//        }
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