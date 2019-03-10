package com.example.kennethallan.Bloom_TB_002;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.service.carrier.CarrierService;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kennethallan.Bloom_TB_002.R;


import java.util.ArrayList;
import java.util.List;

public class Summary extends AppCompatActivity implements Fragment_Output_12.interface_Frag12,Fragment_Output_11.interface_Frag11,
        Fragment_Output_10.interface_Frag10,Fragment_Output_09.interface_Frag09,Fragment_Output_08.interface_Frag08,
        Fragment_Output_07.interface_Frag07,Fragment_Output_06.interface_Frag06,Fragment_Output_05.interface_Frag05,
        Fragment_Output_04.interface_Frag04, Fragment_Output_03.interface_Frag03{

    DBHelper Mydb;
    ListView sessionActivitiesListView;

    Context context;

    public String BUNDLE_NAME = "";
    public String BUNDLE_GOAL = "";
    public String BUNDLE_ATTAIN = "";
    public String BUNDLE_SCALEFACTOR = "";

    Integer numCurrentThemes;

    Bundle sis;
    Bundle currentWeekBundle;

    // fragment stuff
    View fragmentHolder;
    List<Boolean> checkBoxValues;

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

    FloatingActionButton fab_CarryOver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        context = Summary.this;
        Mydb = new DBHelper(this);
        sis = savedInstanceState;
        currentWeekBundle = getIntent().getExtras(); // get bundle that was attached to the intent that started this activity.



        // find and bind variables to views
        sessionActivitiesListView = (ListView)findViewById(R.id.lv_SessionActivities);
        fragmentHolder = findViewById(R.id.Fragment_Holder);
        fab_CarryOver = (FloatingActionButton) findViewById(R.id.fab);

        /////////////////////// SET UP TOOLBAR /////////////////////
        // need this to enable overrides to link the overflow menu to it.
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(getResources().getString(R.string.ActivityTitle_SessionSummary));
        setSupportActionBar(myToolbar);


        // erase arrays in case they hold variables from last time activity used.(not sure if reqquired) TODO test this
        al_values_ThemeNames.clear();
        al_values_ProGoals.clear();
        al_values_ProAttain.clear();
        i_ScaleFactor = 0.00;

        al_values_ProGoals = getCURRENTGoals();
        al_values_ThemeNames = getCurrentThemeNames();
        al_values_ProAttain = getCURRENTSessionProgress();
        i_ScaleFactor = getScaleFactor();

        loadOutputFragment(sis,currentWeekBundle,false); // TODO check if this works. Savedinstance state might be the bundle needed here.

        // create listview

        switch (numCurrentThemes){

            case 3:
                ListAdapter summaryActivitiesListAdapter_03 = new CustomAdaptor_SessionActivities_03(
                        this,al_activitiesList_Name,
                        al_activitiesList_01,
                        al_activitiesList_02,
                        al_activitiesList_03);

                sessionActivitiesListView.setAdapter(summaryActivitiesListAdapter_03);
                break;

            case 4:
                ListAdapter summaryActivitiesListAdapter_04 = new CustomAdaptor_SessionActivities_04(
                        this,al_activitiesList_Name,
                        al_activitiesList_01,
                        al_activitiesList_02,
                        al_activitiesList_03,
                        al_activitiesList_04);

                sessionActivitiesListView.setAdapter(summaryActivitiesListAdapter_04);
                break;

            case 5:
                ListAdapter summaryActivitiesListAdapter_05 = new CustomAdaptor_SessionActivities_05(
                        this,al_activitiesList_Name,
                        al_activitiesList_01,
                        al_activitiesList_02,
                        al_activitiesList_03,
                        al_activitiesList_04,
                        al_activitiesList_05);

                sessionActivitiesListView.setAdapter(summaryActivitiesListAdapter_05);
                break;
            case 6:
                ListAdapter summaryActivitiesListAdapter_06 = new CustomAdaptor_SessionActivities_06(
                        this,al_activitiesList_Name,
                        al_activitiesList_01,
                        al_activitiesList_02,
                        al_activitiesList_03,
                        al_activitiesList_04,
                        al_activitiesList_05,
                        al_activitiesList_06);

                sessionActivitiesListView.setAdapter(summaryActivitiesListAdapter_06);
                break;

            case 7:
                ListAdapter summaryActivitiesListAdapter_07 = new CustomAdaptor_SessionActivities_07(
                        this,al_activitiesList_Name,
                        al_activitiesList_01,
                        al_activitiesList_02,
                        al_activitiesList_03,
                        al_activitiesList_04,
                        al_activitiesList_05,
                        al_activitiesList_06,
                        al_activitiesList_07);

                sessionActivitiesListView.setAdapter(summaryActivitiesListAdapter_07);
                break;

            case 8:
                ListAdapter summaryActivitiesListAdapter_08 = new CustomAdaptor_SessionActivities_08(
                        this,al_activitiesList_Name,
                        al_activitiesList_01,
                        al_activitiesList_02,
                        al_activitiesList_03,
                        al_activitiesList_04,
                        al_activitiesList_05,
                        al_activitiesList_06,
                        al_activitiesList_07,
                        al_activitiesList_08);

                sessionActivitiesListView.setAdapter(summaryActivitiesListAdapter_08);
                break;
            case 9:
                ListAdapter summaryActivitiesListAdapter_09 = new CustomAdaptor_SessionActivities_09(
                        this,al_activitiesList_Name,
                        al_activitiesList_01,
                        al_activitiesList_02,
                        al_activitiesList_03,
                        al_activitiesList_04,
                        al_activitiesList_05,
                        al_activitiesList_06,
                        al_activitiesList_07,
                        al_activitiesList_08,
                        al_activitiesList_09);

                sessionActivitiesListView.setAdapter(summaryActivitiesListAdapter_09);
                break;

            case 10:
                ListAdapter summaryActivitiesListAdapter_10 = new CustomAdaptor_SessionActivities_10(
                        this,al_activitiesList_Name,
                        al_activitiesList_01,
                        al_activitiesList_02,
                        al_activitiesList_03,
                        al_activitiesList_04,
                        al_activitiesList_05,
                        al_activitiesList_06,
                        al_activitiesList_07,
                        al_activitiesList_08,
                        al_activitiesList_09,
                        al_activitiesList_10);

                sessionActivitiesListView.setAdapter(summaryActivitiesListAdapter_10);

                break;

            case 11:

                ListAdapter summaryActivitiesListAdapter_11 = new CustomAdaptor_SessionActivities_11(
                        this,al_activitiesList_Name,
                        al_activitiesList_01,
                        al_activitiesList_02,
                        al_activitiesList_03,
                        al_activitiesList_04,
                        al_activitiesList_05,
                        al_activitiesList_06,
                        al_activitiesList_07,
                        al_activitiesList_08,
                        al_activitiesList_09,
                        al_activitiesList_10,
                        al_activitiesList_11);

                sessionActivitiesListView.setAdapter(summaryActivitiesListAdapter_11);

                break;

            case 12:

                ListAdapter summaryActivitiesListAdapter_12 = new CustomAdaptor_SessionActivities_12(
                        this,al_activitiesList_Name,
                        al_activitiesList_01,
                        al_activitiesList_02,
                        al_activitiesList_03,
                        al_activitiesList_04,
                        al_activitiesList_05,
                        al_activitiesList_06,
                        al_activitiesList_07,
                        al_activitiesList_08,
                        al_activitiesList_09,
                        al_activitiesList_10,
                        al_activitiesList_11,
                        al_activitiesList_12);
                sessionActivitiesListView.setAdapter(summaryActivitiesListAdapter_12);

                break;

        }

        ///////////////////////////// SET ON CLICK LISTENERS //////////////////////////////////////

        /////////////////////////// SET CARRYOVER BUTTON ///////////////////////////
        fab_CarryOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carryOver();
            }
        });

        /////////////////////// CREATE CHECKBOX ARRAY INITIAL BUILD//////////////////////
        // required in case no boxes are checked thus there is an array of "false" values.
        checkBoxValues = new ArrayList<>(); // instantiate object for holding values
        for (int i=0; i< numCurrentThemes; i++){
            checkBoxValues.add(false); // set number of placeholder values
        }

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

    // used to handle the information transferred from the frament to the activity.
    @Override
    public void onMessageRead(List<Boolean> message) {
        checkBoxValues = message;
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
            Toast.makeText(Summary.this, "You have no Current themes to review", Toast.LENGTH_SHORT).show();
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

    public ArrayList<Integer> getCURRENTGoals(){

        // this fetches the values as an arraylist of Strings
        ArrayList<String> al_temp = Mydb.getGoals_CURRENT();

        // this just converts the string arraylist to an integer arraylist
        ArrayList<Integer> al_returnValues = new ArrayList<Integer>();
        for (int i =0; i<al_temp.size();i++){
            al_returnValues.add(Integer.parseInt(al_temp.get(i)));
        }

        return al_returnValues;
    }

    public Double getScaleFactor(){
        Integer temp = 0;
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

    public void carryOver(){

        // create new arraylist of goals based on checkbox values to send to the database for next weeks goals
        ArrayList<String> al_newGoals = new ArrayList<>();
        Integer alteration;
        Integer value;

        for (int i=0; i < numCurrentThemes;i++ ){

            if (checkBoxValues.get(i)==true){
                try{
                    alteration = al_values_ProGoals.get(i) - al_values_ProAttain.get(i);
                    value = al_values_ProGoals.get(i) + alteration;
                    al_newGoals.add(i,value.toString());

                }catch (Exception e) {
                }

            }if (checkBoxValues.get(i)==false){
                try{
                    alteration = 0;
                    value = al_values_ProGoals.get(i) + alteration;
                    al_newGoals.add(i,value.toString());

                }catch (Exception e) {
                }

            }

        }

        Boolean temp = Mydb.insertGoal(al_newGoals, "N");

        if (temp == true){
            Toast.makeText(Summary.this, "Successfully Input Goal", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(Summary.this, "Failed to Input Goal", Toast.LENGTH_SHORT).show();

        }

        // ////////////////// RETURN TO MAIN ACTIVITY //////////////////////////
        Intent intent = new Intent(Summary.this, MainActivity.class);
        startActivity(intent);

    }




    // The following ArrayAdapter classes are for displaying the activities in different layouts.
    class CustomAdaptor_SessionActivities_03 extends ArrayAdapter<String> {

        ArrayList<String> arrayList_Title = new ArrayList<>();
        ArrayList<String> arrayList_values_01 = new ArrayList<>();
        ArrayList<String> arrayList_values_02 = new ArrayList<>();
        ArrayList<String> arrayList_values_03 = new ArrayList<>();

        public CustomAdaptor_SessionActivities_03(
                Context context,
                ArrayList<String> res_title,
                ArrayList<String> res_01,
                ArrayList<String> res_02,
                ArrayList<String> res_03) {

            super(context, R.layout.summary_activities_03, res_title); // not using this but need it to work.

            this.arrayList_Title = res_title;
            this.arrayList_values_01 = res_01;
            this.arrayList_values_02 = res_02;
            this.arrayList_values_03 = res_03;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = LayoutInflater.from(getContext());
            View customView = Inflater.inflate(R.layout.summary_activities_03, parent, false);
            // 1) refence to data
            // 2) reference to text view
            // 3) Reference to seekbar.

            TextView title = (TextView) customView.findViewById(R.id.tv_activity_title);
            TextView activitytheme_01 = (TextView) customView.findViewById(R.id.tv_activity_01);
            TextView activitytheme_02 = (TextView) customView.findViewById(R.id.tv_activity_02);
            TextView activitytheme_03 = (TextView) customView.findViewById(R.id.tv_activity_03);

            //set values to view
            title.setText(arrayList_Title.get(position));
            activitytheme_01.setText(arrayList_values_01.get(position));
            activitytheme_02.setText(arrayList_values_02.get(position));
            activitytheme_03.setText(arrayList_values_03.get(position));

            return customView;
        }
    }

    class CustomAdaptor_SessionActivities_04 extends ArrayAdapter<String> {

        ArrayList<String> arrayList_Title = new ArrayList<>();
        ArrayList<String> arrayList_values_01 = new ArrayList<>();
        ArrayList<String> arrayList_values_02 = new ArrayList<>();
        ArrayList<String> arrayList_values_03 = new ArrayList<>();
        ArrayList<String> arrayList_values_04 = new ArrayList<>();

        public CustomAdaptor_SessionActivities_04(
                Context context,
                ArrayList<String> res_title,
                ArrayList<String> res_01,
                ArrayList<String> res_02,
                ArrayList<String> res_03,
                ArrayList<String> res_04) {

            super(context, R.layout.summary_activities_04, res_title); // not using this but need it to work.

            this.arrayList_Title = res_title;
            this.arrayList_values_01 = res_01;
            this.arrayList_values_02 = res_02;
            this.arrayList_values_03 = res_03;
            this.arrayList_values_04 = res_04;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = LayoutInflater.from(getContext());
            View customView = Inflater.inflate(R.layout.summary_activities_04, parent, false);
            // 1) refence to data
            // 2) reference to text view
            // 3) Reference to seekbar.

            TextView title = (TextView) customView.findViewById(R.id.tv_activity_title);
            TextView activitytheme_01 = (TextView) customView.findViewById(R.id.tv_activity_01);
            TextView activitytheme_02 = (TextView) customView.findViewById(R.id.tv_activity_02);
            TextView activitytheme_03 = (TextView) customView.findViewById(R.id.tv_activity_03);
            TextView activitytheme_04 = (TextView) customView.findViewById(R.id.tv_activity_04);

            //set values to view
            title.setText(arrayList_Title.get(position));
            activitytheme_01.setText(arrayList_values_01.get(position));
            activitytheme_02.setText(arrayList_values_02.get(position));
            activitytheme_03.setText(arrayList_values_03.get(position));
            activitytheme_04.setText(arrayList_values_04.get(position));

            return customView;
        }
    }

    class CustomAdaptor_SessionActivities_05 extends ArrayAdapter<String> {

        ArrayList<String> arrayList_Title = new ArrayList<>();
        ArrayList<String> arrayList_values_01 = new ArrayList<>();
        ArrayList<String> arrayList_values_02 = new ArrayList<>();
        ArrayList<String> arrayList_values_03 = new ArrayList<>();
        ArrayList<String> arrayList_values_04 = new ArrayList<>();
        ArrayList<String> arrayList_values_05 = new ArrayList<>();

        public CustomAdaptor_SessionActivities_05(
                Context context,
                ArrayList<String> res_title,
                ArrayList<String> res_01,
                ArrayList<String> res_02,
                ArrayList<String> res_03,
                ArrayList<String> res_04,
                ArrayList<String> res_05) {

            super(context, R.layout.summary_activities_05, res_title); // not using this but need it to work.

            this.arrayList_Title = res_title;
            this.arrayList_values_01 = res_01;
            this.arrayList_values_02 = res_02;
            this.arrayList_values_03 = res_03;
            this.arrayList_values_04 = res_04;
            this.arrayList_values_05 = res_05;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = LayoutInflater.from(getContext());
            View customView = Inflater.inflate(R.layout.summary_activities_05, parent, false);
            // 1) refence to data
            // 2) reference to text view
            // 3) Reference to seekbar.

            TextView title = (TextView) customView.findViewById(R.id.tv_activity_title);
            TextView activitytheme_01 = (TextView) customView.findViewById(R.id.tv_activity_01);
            TextView activitytheme_02 = (TextView) customView.findViewById(R.id.tv_activity_02);
            TextView activitytheme_03 = (TextView) customView.findViewById(R.id.tv_activity_03);
            TextView activitytheme_04 = (TextView) customView.findViewById(R.id.tv_activity_04);
            TextView activitytheme_05 = (TextView) customView.findViewById(R.id.tv_activity_05);

            //set values to view
            title.setText(arrayList_Title.get(position));
            activitytheme_01.setText(arrayList_values_01.get(position));
            activitytheme_02.setText(arrayList_values_02.get(position));
            activitytheme_03.setText(arrayList_values_03.get(position));
            activitytheme_04.setText(arrayList_values_04.get(position));
            activitytheme_05.setText(arrayList_values_05.get(position));

            return customView;
        }
    }

    class CustomAdaptor_SessionActivities_06 extends ArrayAdapter<String> {

        ArrayList<String> arrayList_Title = new ArrayList<>();
        ArrayList<String> arrayList_values_01 = new ArrayList<>();
        ArrayList<String> arrayList_values_02 = new ArrayList<>();
        ArrayList<String> arrayList_values_03 = new ArrayList<>();
        ArrayList<String> arrayList_values_04 = new ArrayList<>();
        ArrayList<String> arrayList_values_05 = new ArrayList<>();
        ArrayList<String> arrayList_values_06 = new ArrayList<>();

        public CustomAdaptor_SessionActivities_06(
                Context context,
                ArrayList<String> res_title,
                ArrayList<String> res_01,
                ArrayList<String> res_02,
                ArrayList<String> res_03,
                ArrayList<String> res_04,
                ArrayList<String> res_05,
                ArrayList<String> res_06) {

            super(context, R.layout.summary_activities_06, res_title); // not using this but need it to work.

            this.arrayList_Title = res_title;
            this.arrayList_values_01 = res_01;
            this.arrayList_values_02 = res_02;
            this.arrayList_values_03 = res_03;
            this.arrayList_values_04 = res_04;
            this.arrayList_values_05 = res_05;
            this.arrayList_values_06 = res_06;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = LayoutInflater.from(getContext());
            View customView = Inflater.inflate(R.layout.summary_activities_06, parent, false);
            // 1) refence to data
            // 2) reference to text view
            // 3) Reference to seekbar.

            TextView title = (TextView) customView.findViewById(R.id.tv_activity_title);
            TextView activitytheme_01 = (TextView) customView.findViewById(R.id.tv_activity_01);
            TextView activitytheme_02 = (TextView) customView.findViewById(R.id.tv_activity_02);
            TextView activitytheme_03 = (TextView) customView.findViewById(R.id.tv_activity_03);
            TextView activitytheme_04 = (TextView) customView.findViewById(R.id.tv_activity_04);
            TextView activitytheme_05 = (TextView) customView.findViewById(R.id.tv_activity_05);
            TextView activitytheme_06 = (TextView) customView.findViewById(R.id.tv_activity_06);

            //set values to view
            title.setText(arrayList_Title.get(position));
            activitytheme_01.setText(arrayList_values_01.get(position));
            activitytheme_02.setText(arrayList_values_02.get(position));
            activitytheme_03.setText(arrayList_values_03.get(position));
            activitytheme_04.setText(arrayList_values_04.get(position));
            activitytheme_05.setText(arrayList_values_05.get(position));
            activitytheme_06.setText(arrayList_values_06.get(position));

            return customView;
        }
    }

    class CustomAdaptor_SessionActivities_07 extends ArrayAdapter<String> {

        ArrayList<String> arrayList_Title = new ArrayList<>();
        ArrayList<String> arrayList_values_01 = new ArrayList<>();
        ArrayList<String> arrayList_values_02 = new ArrayList<>();
        ArrayList<String> arrayList_values_03 = new ArrayList<>();
        ArrayList<String> arrayList_values_04 = new ArrayList<>();
        ArrayList<String> arrayList_values_05 = new ArrayList<>();
        ArrayList<String> arrayList_values_06 = new ArrayList<>();
        ArrayList<String> arrayList_values_07 = new ArrayList<>();


        public CustomAdaptor_SessionActivities_07(
                Context context,
                ArrayList<String> res_title,
                ArrayList<String> res_01,
                ArrayList<String> res_02,
                ArrayList<String> res_03,
                ArrayList<String> res_04,
                ArrayList<String> res_05,
                ArrayList<String> res_06,
                ArrayList<String> res_07) {

            super(context, R.layout.summary_activities_07, res_title); // not using this but need it to work.

            this.arrayList_Title = res_title;
            this.arrayList_values_01 = res_01;
            this.arrayList_values_02 = res_02;
            this.arrayList_values_03 = res_03;
            this.arrayList_values_04 = res_04;
            this.arrayList_values_05 = res_05;
            this.arrayList_values_06 = res_06;
            this.arrayList_values_07 = res_07;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = LayoutInflater.from(getContext());
            View customView = Inflater.inflate(R.layout.summary_activities_07, parent, false);
            // 1) refence to data
            // 2) reference to text view
            // 3) Reference to seekbar.

            TextView title = (TextView) customView.findViewById(R.id.tv_activity_title);
            TextView activitytheme_01 = (TextView) customView.findViewById(R.id.tv_activity_01);
            TextView activitytheme_02 = (TextView) customView.findViewById(R.id.tv_activity_02);
            TextView activitytheme_03 = (TextView) customView.findViewById(R.id.tv_activity_03);
            TextView activitytheme_04 = (TextView) customView.findViewById(R.id.tv_activity_04);
            TextView activitytheme_05 = (TextView) customView.findViewById(R.id.tv_activity_05);
            TextView activitytheme_06 = (TextView) customView.findViewById(R.id.tv_activity_06);
            TextView activitytheme_07 = (TextView) customView.findViewById(R.id.tv_activity_07);

            //set values to view
            title.setText(arrayList_Title.get(position));
            activitytheme_01.setText(arrayList_values_01.get(position));
            activitytheme_02.setText(arrayList_values_02.get(position));
            activitytheme_03.setText(arrayList_values_03.get(position));
            activitytheme_04.setText(arrayList_values_04.get(position));
            activitytheme_05.setText(arrayList_values_05.get(position));
            activitytheme_06.setText(arrayList_values_06.get(position));
            activitytheme_07.setText(arrayList_values_07.get(position));

            return customView;
        }
    }

    class CustomAdaptor_SessionActivities_08 extends ArrayAdapter<String> {

        ArrayList<String> arrayList_Title = new ArrayList<>();
        ArrayList<String> arrayList_values_01 = new ArrayList<>();
        ArrayList<String> arrayList_values_02 = new ArrayList<>();
        ArrayList<String> arrayList_values_03 = new ArrayList<>();
        ArrayList<String> arrayList_values_04 = new ArrayList<>();
        ArrayList<String> arrayList_values_05 = new ArrayList<>();
        ArrayList<String> arrayList_values_06 = new ArrayList<>();
        ArrayList<String> arrayList_values_07 = new ArrayList<>();
        ArrayList<String> arrayList_values_08 = new ArrayList<>();


        public CustomAdaptor_SessionActivities_08(
                Context context,
                ArrayList<String> res_title,
                ArrayList<String> res_01,
                ArrayList<String> res_02,
                ArrayList<String> res_03,
                ArrayList<String> res_04,
                ArrayList<String> res_05,
                ArrayList<String> res_06,
                ArrayList<String> res_07,
                ArrayList<String> res_08) {

            super(context, R.layout.summary_activities_08, res_title); // not using this but need it to work.

            this.arrayList_Title = res_title;
            this.arrayList_values_01 = res_01;
            this.arrayList_values_02 = res_02;
            this.arrayList_values_03 = res_03;
            this.arrayList_values_04 = res_04;
            this.arrayList_values_05 = res_05;
            this.arrayList_values_06 = res_06;
            this.arrayList_values_07 = res_07;
            this.arrayList_values_08 = res_08;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = LayoutInflater.from(getContext());
            View customView = Inflater.inflate(R.layout.summary_activities_08, parent, false);
            // 1) refence to data
            // 2) reference to text view
            // 3) Reference to seekbar.

            TextView title = (TextView) customView.findViewById(R.id.tv_activity_title);
            TextView activitytheme_01 = (TextView) customView.findViewById(R.id.tv_activity_01);
            TextView activitytheme_02 = (TextView) customView.findViewById(R.id.tv_activity_02);
            TextView activitytheme_03 = (TextView) customView.findViewById(R.id.tv_activity_03);
            TextView activitytheme_04 = (TextView) customView.findViewById(R.id.tv_activity_04);
            TextView activitytheme_05 = (TextView) customView.findViewById(R.id.tv_activity_05);
            TextView activitytheme_06 = (TextView) customView.findViewById(R.id.tv_activity_06);
            TextView activitytheme_07 = (TextView) customView.findViewById(R.id.tv_activity_07);
            TextView activitytheme_08 = (TextView) customView.findViewById(R.id.tv_activity_08);

            //set values to view
            title.setText(arrayList_Title.get(position));
            activitytheme_01.setText(arrayList_values_01.get(position));
            activitytheme_02.setText(arrayList_values_02.get(position));
            activitytheme_03.setText(arrayList_values_03.get(position));
            activitytheme_04.setText(arrayList_values_04.get(position));
            activitytheme_05.setText(arrayList_values_05.get(position));
            activitytheme_06.setText(arrayList_values_06.get(position));
            activitytheme_07.setText(arrayList_values_07.get(position));
            activitytheme_08.setText(arrayList_values_08.get(position));

            return customView;
        }
    }

    class CustomAdaptor_SessionActivities_09 extends ArrayAdapter<String> {

        ArrayList<String> arrayList_Title = new ArrayList<>();
        ArrayList<String> arrayList_values_01 = new ArrayList<>();
        ArrayList<String> arrayList_values_02 = new ArrayList<>();
        ArrayList<String> arrayList_values_03 = new ArrayList<>();
        ArrayList<String> arrayList_values_04 = new ArrayList<>();
        ArrayList<String> arrayList_values_05 = new ArrayList<>();
        ArrayList<String> arrayList_values_06 = new ArrayList<>();
        ArrayList<String> arrayList_values_07 = new ArrayList<>();
        ArrayList<String> arrayList_values_08 = new ArrayList<>();
        ArrayList<String> arrayList_values_09 = new ArrayList<>();


        public CustomAdaptor_SessionActivities_09(
                Context context,
                ArrayList<String> res_title,
                ArrayList<String> res_01,
                ArrayList<String> res_02,
                ArrayList<String> res_03,
                ArrayList<String> res_04,
                ArrayList<String> res_05,
                ArrayList<String> res_06,
                ArrayList<String> res_07,
                ArrayList<String> res_08,
                ArrayList<String> res_09) {

            super(context, R.layout.summary_activities_09, res_title); // not using this but need it to work.

            this.arrayList_Title = res_title;
            this.arrayList_values_01 = res_01;
            this.arrayList_values_02 = res_02;
            this.arrayList_values_03 = res_03;
            this.arrayList_values_04 = res_04;
            this.arrayList_values_05 = res_05;
            this.arrayList_values_06 = res_06;
            this.arrayList_values_07 = res_07;
            this.arrayList_values_08 = res_08;
            this.arrayList_values_09 = res_09;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = LayoutInflater.from(getContext());
            View customView = Inflater.inflate(R.layout.summary_activities_09, parent, false);
            // 1) refence to data
            // 2) reference to text view
            // 3) Reference to seekbar.

            TextView title = (TextView) customView.findViewById(R.id.tv_activity_title);
            TextView activitytheme_01 = (TextView) customView.findViewById(R.id.tv_activity_01);
            TextView activitytheme_02 = (TextView) customView.findViewById(R.id.tv_activity_02);
            TextView activitytheme_03 = (TextView) customView.findViewById(R.id.tv_activity_03);
            TextView activitytheme_04 = (TextView) customView.findViewById(R.id.tv_activity_04);
            TextView activitytheme_05 = (TextView) customView.findViewById(R.id.tv_activity_05);
            TextView activitytheme_06 = (TextView) customView.findViewById(R.id.tv_activity_06);
            TextView activitytheme_07 = (TextView) customView.findViewById(R.id.tv_activity_07);
            TextView activitytheme_08 = (TextView) customView.findViewById(R.id.tv_activity_08);
            TextView activitytheme_09 = (TextView) customView.findViewById(R.id.tv_activity_09);

            //set values to view
            title.setText(arrayList_Title.get(position));
            activitytheme_01.setText(arrayList_values_01.get(position));
            activitytheme_02.setText(arrayList_values_02.get(position));
            activitytheme_03.setText(arrayList_values_03.get(position));
            activitytheme_04.setText(arrayList_values_04.get(position));
            activitytheme_05.setText(arrayList_values_05.get(position));
            activitytheme_06.setText(arrayList_values_06.get(position));
            activitytheme_07.setText(arrayList_values_07.get(position));
            activitytheme_08.setText(arrayList_values_08.get(position));
            activitytheme_09.setText(arrayList_values_09.get(position));

            return customView;
        }
    }

    class CustomAdaptor_SessionActivities_10 extends ArrayAdapter<String> {

        // designating global variable to inherit the input parameters.
        ArrayList<String> arrayList_Title = new ArrayList<>();
        ArrayList<String> arrayList_values_01 = new ArrayList<>();
        ArrayList<String> arrayList_values_02 = new ArrayList<>();
        ArrayList<String> arrayList_values_03 = new ArrayList<>();
        ArrayList<String> arrayList_values_04 = new ArrayList<>();
        ArrayList<String> arrayList_values_05 = new ArrayList<>();
        ArrayList<String> arrayList_values_06 = new ArrayList<>();
        ArrayList<String> arrayList_values_07 = new ArrayList<>();
        ArrayList<String> arrayList_values_08 = new ArrayList<>();
        ArrayList<String> arrayList_values_09 = new ArrayList<>();
        ArrayList<String> arrayList_values_10 = new ArrayList<>();

        // constructor
        public CustomAdaptor_SessionActivities_10(
                Context context,
                ArrayList<String> res_title,
                ArrayList<String> res_01,
                ArrayList<String> res_02,
                ArrayList<String> res_03,
                ArrayList<String> res_04,
                ArrayList<String> res_05,
                ArrayList<String> res_06,
                ArrayList<String> res_07,
                ArrayList<String> res_08,
                ArrayList<String> res_09,
                ArrayList<String> res_10) {

            // not passing all input values into the super since it will make it not work. Just binding data in the getView method instead.
            super(context, R.layout.summary_activities_10, res_title); // not using this but need it to work.

            // pass input parameters to the global variables.
            this.arrayList_Title = res_title;
            this.arrayList_values_01 = res_01;
            this.arrayList_values_02 = res_02;
            this.arrayList_values_03 = res_03;
            this.arrayList_values_04 = res_04;
            this.arrayList_values_05 = res_05;
            this.arrayList_values_06 = res_06;
            this.arrayList_values_07 = res_07;
            this.arrayList_values_08 = res_08;
            this.arrayList_values_09 = res_09;
            this.arrayList_values_10 = res_10;
        }

        // bind views in this method.
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = LayoutInflater.from(getContext());
            View customView = Inflater.inflate(R.layout.summary_activities_10, parent, false);
            // 1) refence to data
            // 2) reference to text view
            // 3) Reference to seekbar.

            TextView title = (TextView) customView.findViewById(R.id.tv_activity_title);
            TextView activitytheme_01 = (TextView) customView.findViewById(R.id.tv_activity_01);
            TextView activitytheme_02 = (TextView) customView.findViewById(R.id.tv_activity_02);
            TextView activitytheme_03 = (TextView) customView.findViewById(R.id.tv_activity_03);
            TextView activitytheme_04 = (TextView) customView.findViewById(R.id.tv_activity_04);
            TextView activitytheme_05 = (TextView) customView.findViewById(R.id.tv_activity_05);
            TextView activitytheme_06 = (TextView) customView.findViewById(R.id.tv_activity_06);
            TextView activitytheme_07 = (TextView) customView.findViewById(R.id.tv_activity_07);
            TextView activitytheme_08 = (TextView) customView.findViewById(R.id.tv_activity_08);
            TextView activitytheme_09 = (TextView) customView.findViewById(R.id.tv_activity_09);
            TextView activitytheme_10 = (TextView) customView.findViewById(R.id.tv_activity_10);

            //set values to view
            title.setText(arrayList_Title.get(position));
            activitytheme_01.setText(arrayList_values_01.get(position));
            activitytheme_02.setText(arrayList_values_02.get(position));
            activitytheme_03.setText(arrayList_values_03.get(position));
            activitytheme_04.setText(arrayList_values_04.get(position));
            activitytheme_05.setText(arrayList_values_05.get(position));
            activitytheme_06.setText(arrayList_values_06.get(position));
            activitytheme_07.setText(arrayList_values_07.get(position));
            activitytheme_08.setText(arrayList_values_08.get(position));
            activitytheme_09.setText(arrayList_values_09.get(position));
            activitytheme_10.setText(arrayList_values_10.get(position));

            return customView;
        }
    }

    class CustomAdaptor_SessionActivities_11 extends ArrayAdapter<String> {

        // designating global variable to inherit the input parameters.
        ArrayList<String> arrayList_Title = new ArrayList<>();
        ArrayList<String> arrayList_values_01 = new ArrayList<>();
        ArrayList<String> arrayList_values_02 = new ArrayList<>();
        ArrayList<String> arrayList_values_03 = new ArrayList<>();
        ArrayList<String> arrayList_values_04 = new ArrayList<>();
        ArrayList<String> arrayList_values_05 = new ArrayList<>();
        ArrayList<String> arrayList_values_06 = new ArrayList<>();
        ArrayList<String> arrayList_values_07 = new ArrayList<>();
        ArrayList<String> arrayList_values_08 = new ArrayList<>();
        ArrayList<String> arrayList_values_09 = new ArrayList<>();
        ArrayList<String> arrayList_values_10 = new ArrayList<>();
        ArrayList<String> arrayList_values_11 = new ArrayList<>();

        // constructor
        public CustomAdaptor_SessionActivities_11(
                Context context,
                ArrayList<String> res_title,
                ArrayList<String> res_01,
                ArrayList<String> res_02,
                ArrayList<String> res_03,
                ArrayList<String> res_04,
                ArrayList<String> res_05,
                ArrayList<String> res_06,
                ArrayList<String> res_07,
                ArrayList<String> res_08,
                ArrayList<String> res_09,
                ArrayList<String> res_10,
                ArrayList<String> res_11) {

            // not passing all input values into the super since it will make it not work. Just binding data in the getView method instead.
            super(context, R.layout.summary_activities_11, res_title); // not using this but need it to work.

            // pass input parameters to the global variables.
            this.arrayList_Title = res_title;
            this.arrayList_values_01 = res_01;
            this.arrayList_values_02 = res_02;
            this.arrayList_values_03 = res_03;
            this.arrayList_values_04 = res_04;
            this.arrayList_values_05 = res_05;
            this.arrayList_values_06 = res_06;
            this.arrayList_values_07 = res_07;
            this.arrayList_values_08 = res_08;
            this.arrayList_values_09 = res_09;
            this.arrayList_values_10 = res_10;
            this.arrayList_values_11 = res_11;
        }

        // bind views in this method.
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = LayoutInflater.from(getContext());
            View customView = Inflater.inflate(R.layout.summary_activities_11, parent, false);
            // 1) refence to data
            // 2) reference to text view
            // 3) Reference to seekbar.

            TextView title = (TextView) customView.findViewById(R.id.tv_activity_title);
            TextView activitytheme_01 = (TextView) customView.findViewById(R.id.tv_activity_01);
            TextView activitytheme_02 = (TextView) customView.findViewById(R.id.tv_activity_02);
            TextView activitytheme_03 = (TextView) customView.findViewById(R.id.tv_activity_03);
            TextView activitytheme_04 = (TextView) customView.findViewById(R.id.tv_activity_04);
            TextView activitytheme_05 = (TextView) customView.findViewById(R.id.tv_activity_05);
            TextView activitytheme_06 = (TextView) customView.findViewById(R.id.tv_activity_06);
            TextView activitytheme_07 = (TextView) customView.findViewById(R.id.tv_activity_07);
            TextView activitytheme_08 = (TextView) customView.findViewById(R.id.tv_activity_08);
            TextView activitytheme_09 = (TextView) customView.findViewById(R.id.tv_activity_09);
            TextView activitytheme_10 = (TextView) customView.findViewById(R.id.tv_activity_10);
            TextView activitytheme_11 = (TextView) customView.findViewById(R.id.tv_activity_11);


            //set values to view
            title.setText(arrayList_Title.get(position));
            activitytheme_01.setText(arrayList_values_01.get(position));
            activitytheme_02.setText(arrayList_values_02.get(position));
            activitytheme_03.setText(arrayList_values_03.get(position));
            activitytheme_04.setText(arrayList_values_04.get(position));
            activitytheme_05.setText(arrayList_values_05.get(position));
            activitytheme_06.setText(arrayList_values_06.get(position));
            activitytheme_07.setText(arrayList_values_07.get(position));
            activitytheme_08.setText(arrayList_values_08.get(position));
            activitytheme_09.setText(arrayList_values_09.get(position));
            activitytheme_10.setText(arrayList_values_10.get(position));
            activitytheme_11.setText(arrayList_values_11.get(position));

            return customView;
        }
    }

    class CustomAdaptor_SessionActivities_12 extends ArrayAdapter<String> {

        ArrayList<String> arrayList_Title = new ArrayList<>();
        ArrayList<String> arrayList_values_01 = new ArrayList<>();
        ArrayList<String> arrayList_values_02 = new ArrayList<>();
        ArrayList<String> arrayList_values_03 = new ArrayList<>();
        ArrayList<String> arrayList_values_04 = new ArrayList<>();
        ArrayList<String> arrayList_values_05 = new ArrayList<>();
        ArrayList<String> arrayList_values_06 = new ArrayList<>();
        ArrayList<String> arrayList_values_07 = new ArrayList<>();
        ArrayList<String> arrayList_values_08 = new ArrayList<>();
        ArrayList<String> arrayList_values_09 = new ArrayList<>();
        ArrayList<String> arrayList_values_10 = new ArrayList<>();
        ArrayList<String> arrayList_values_11 = new ArrayList<>();
        ArrayList<String> arrayList_values_12 = new ArrayList<>();


        public CustomAdaptor_SessionActivities_12(
                Context context,
                ArrayList<String> res_title,
                ArrayList<String> res_01,
                ArrayList<String> res_02,
                ArrayList<String> res_03,
                ArrayList<String> res_04,
                ArrayList<String> res_05,
                ArrayList<String> res_06,
                ArrayList<String> res_07,
                ArrayList<String> res_08,
                ArrayList<String> res_09,
                ArrayList<String> res_10,
                ArrayList<String> res_11,
                ArrayList<String> res_12) {

            super(context, R.layout.summary_activities_12, res_title); // not using this but need it to work.

            this.arrayList_Title = res_title;
            this.arrayList_values_01 = res_01;
            this.arrayList_values_02 = res_02;
            this.arrayList_values_03 = res_03;
            this.arrayList_values_04 = res_04;
            this.arrayList_values_05 = res_05;
            this.arrayList_values_06 = res_06;
            this.arrayList_values_07 = res_07;
            this.arrayList_values_08 = res_08;
            this.arrayList_values_09 = res_09;
            this.arrayList_values_10 = res_10;
            this.arrayList_values_11 = res_11;
            this.arrayList_values_12 = res_12;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = LayoutInflater.from(getContext());
            View customView = Inflater.inflate(R.layout.summary_activities_12, parent, false);
            // 1) refence to data
            // 2) reference to text view
            // 3) Reference to seekbar.

            TextView title = (TextView) customView.findViewById(R.id.tv_activity_title);
            TextView activitytheme_01 = (TextView) customView.findViewById(R.id.tv_activity_01);
            TextView activitytheme_02 = (TextView) customView.findViewById(R.id.tv_activity_02);
            TextView activitytheme_03 = (TextView) customView.findViewById(R.id.tv_activity_03);
            TextView activitytheme_04 = (TextView) customView.findViewById(R.id.tv_activity_04);
            TextView activitytheme_05 = (TextView) customView.findViewById(R.id.tv_activity_05);
            TextView activitytheme_06 = (TextView) customView.findViewById(R.id.tv_activity_06);
            TextView activitytheme_07 = (TextView) customView.findViewById(R.id.tv_activity_07);
            TextView activitytheme_08 = (TextView) customView.findViewById(R.id.tv_activity_08);
            TextView activitytheme_09 = (TextView) customView.findViewById(R.id.tv_activity_09);
            TextView activitytheme_10 = (TextView) customView.findViewById(R.id.tv_activity_10);
            TextView activitytheme_11 = (TextView) customView.findViewById(R.id.tv_activity_11);
            TextView activitytheme_12 = (TextView) customView.findViewById(R.id.tv_activity_12);


            //set values to view
            title.setText(arrayList_Title.get(position));
            activitytheme_01.setText(arrayList_values_01.get(position));
            activitytheme_02.setText(arrayList_values_02.get(position));
            activitytheme_03.setText(arrayList_values_03.get(position));
            activitytheme_04.setText(arrayList_values_04.get(position));
            activitytheme_05.setText(arrayList_values_05.get(position));
            activitytheme_06.setText(arrayList_values_06.get(position));
            activitytheme_07.setText(arrayList_values_07.get(position));
            activitytheme_08.setText(arrayList_values_08.get(position));
            activitytheme_09.setText(arrayList_values_09.get(position));
            activitytheme_10.setText(arrayList_values_10.get(position));
            activitytheme_11.setText(arrayList_values_11.get(position));
            activitytheme_12.setText(arrayList_values_12.get(position));

            return customView;
        }
    }

}
