package com.example.kennethallan.Bloom_TB_002;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Input_06 extends Fragment {

    DBHelper Mydb;

    private SeekBar seekbar01;
    private SeekBar seekbar02;
    private SeekBar seekbar03;
    private SeekBar seekbar04;
    private SeekBar seekbar05;
    private SeekBar seekbar06;

    private TextView tv_Pro_01;
    private TextView tv_Pro_02;
    private TextView tv_Pro_03;
    private TextView tv_Pro_04;
    private TextView tv_Pro_05;
    private TextView tv_Pro_06;
    private TextView tv_ThemeName;

    private EditText et_Hours;
    private EditText et_Minutes;

    SeekBar.OnSeekBarChangeListener mlistener;
    List<String> al_themeNames;
    //int testValue;
    List<Integer> testList = new ArrayList<>();

    Fragment_Input_06.interface_Frag06 sendValuesInterface_Frag06;

    public String BUNDLE_NAME = "";
    public String BUNDLE_GOAL = "";
    public String BUNDLE_ATTAIN = "";
    public String BUNDLE_SCALEFACTOR = "";
    public String BUNDLE_COLOURSEQUENCE = "";

    public Integer numThemes = 6;

    public Context testContext;
    public ArrayList<Drawable> al_ProgressDrawables;
    public ArrayList<Integer> al_colourSequence;

    public Fragment_Input_06() {
        // Required empty public constructor
    }

    public interface interface_Frag06 {

        public void onMessageRead(List<Integer> message);
        // I think you send through this to get to the activity but data flow is only in
        // this direction.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__input_06, container, false);

        seekbar01 = (SeekBar) view.findViewById(R.id.seekBar1);
        seekbar02 = (SeekBar) view.findViewById(R.id.seekBar2);
        seekbar03 = (SeekBar) view.findViewById(R.id.seekBar3);
        seekbar04 = (SeekBar) view.findViewById(R.id.seekBar4);
        seekbar05 = (SeekBar) view.findViewById(R.id.seekBar5);
        seekbar06 = (SeekBar) view.findViewById(R.id.seekBar6);
        seekbar01.setTag(0);
        seekbar02.setTag(1);
        seekbar03.setTag(2);
        seekbar04.setTag(3);
        seekbar05.setTag(4);
        seekbar06.setTag(5);


        tv_Pro_01 = (TextView) view.findViewById(R.id.tv_num_01);
        tv_Pro_02 = (TextView) view.findViewById(R.id.tv_num_02);
        tv_Pro_03 = (TextView) view.findViewById(R.id.tv_num_03);
        tv_Pro_04 = (TextView) view.findViewById(R.id.tv_num_04);
        tv_Pro_05 = (TextView) view.findViewById(R.id.tv_num_05);
        tv_Pro_06 = (TextView) view.findViewById(R.id.tv_num_06);
        tv_ThemeName = (TextView) view.findViewById(R.id.tv_ThemeName);

        // linking variables to view objects

        et_Hours = (EditText) view.findViewById(R.id.et_Input_Time_Hours);
        et_Minutes = (EditText) view.findViewById(R.id.et_Input_Time_Minutes);

        // pass current theme names to fragment for later use
        al_themeNames = new ArrayList<String>();
        Mydb = new DBHelper(getActivity()); //needed to do this so i could use the DH helper in a fragment. Probably needs the activity not the fragement for the constructer.....dunno
        al_themeNames = Mydb.getCURRENTThemeNames();

        /////////////////// SET SEEKBARS TO PREVIOUS GOAL VALUES /////////////////////////////
        // working with bundles
        //getting names of bundles
        BUNDLE_NAME = getResources().getString(R.string.bundle_name);
        BUNDLE_GOAL = getResources().getString(R.string.bundle_goal);
        BUNDLE_ATTAIN = getResources().getString(R.string.bundle_attain);
        BUNDLE_SCALEFACTOR = getResources().getString(R.string.bundle_scalefactor);
        BUNDLE_COLOURSEQUENCE = getResources().getString(R.string.bundle_coloursequence);

        Bundle currentWeekBundle = this.getArguments();

        if (currentWeekBundle != null){

            //goals
            ArrayList<Integer> al_Bundle_GoalVal = new ArrayList<Integer>();
            al_Bundle_GoalVal = getArguments().getIntegerArrayList(BUNDLE_GOAL);
            Integer goals_01;
            Integer goals_02;
            Integer goals_03;
            Integer goals_04;
            Integer goals_05;
            Integer goals_06;

            try{
                goals_01 = al_Bundle_GoalVal.get(0);
                goals_02 = al_Bundle_GoalVal.get(1);
                goals_03 = al_Bundle_GoalVal.get(2);
                goals_04 = al_Bundle_GoalVal.get(3);
                goals_05 = al_Bundle_GoalVal.get(4);
                goals_06 = al_Bundle_GoalVal.get(5);

            }catch(Exception e){

                Log.d("Fragement_Input_06", "Error on accessing bundle goal values, likely not set properly before so error in assigning. Default is to output 0s here instead");
                goals_01 = 0;
                goals_02 = 0;
                goals_03 = 0;
                goals_04 = 0;
                goals_05 = 0;
                goals_06 = 0;
            }

            Double scaleFactor = currentWeekBundle.getDouble(BUNDLE_SCALEFACTOR);
            Double alterfor100Factor = scaleFactor*(100.00/90.00); // used to alter the scale factor so as to not limit it for overachieve goal purposes in output fragements.
            // IMPORTANT - KEEP the two decimal places or the doubles dont round to two decimal places.

            // //////////////////SET PROGRESS OF seekbars //////////////////////////////
            seekbar01.setProgress((int) Math.round(goals_01*alterfor100Factor));
            seekbar02.setProgress((int) Math.round(goals_02*alterfor100Factor));
            seekbar03.setProgress((int) Math.round(goals_03*alterfor100Factor));
            seekbar04.setProgress((int) Math.round(goals_04*alterfor100Factor));
            seekbar05.setProgress((int) Math.round(goals_05*alterfor100Factor));
            seekbar06.setProgress((int) Math.round(goals_06*alterfor100Factor));

            Integer total = goals_01 + goals_02 + goals_03 + goals_04 + goals_05 + goals_06;
            Integer hours = total/ 60;
            Integer minutes = total % 60;
            et_Hours.setText(hours.toString());
            et_Minutes.setText(minutes.toString());

            // //////////////// SET COLOURS OF SEEKBARS
            Fragment_Utilities fragUtils =  new Fragment_Utilities(testContext); // initialise this class so i can use the methods in it.
            al_ProgressDrawables = fragUtils.AssembleColours(); // get a drawable arraylist
            al_colourSequence = fragUtils.GetColourSequence(currentWeekBundle); // get the colour sequence to use now.

            seekbar01.setProgressDrawable(al_ProgressDrawables.get(al_colourSequence.get(0)));
            seekbar02.setProgressDrawable(al_ProgressDrawables.get(al_colourSequence.get(1)));
            seekbar03.setProgressDrawable(al_ProgressDrawables.get(al_colourSequence.get(2)));
            seekbar04.setProgressDrawable(al_ProgressDrawables.get(al_colourSequence.get(3)));
            seekbar05.setProgressDrawable(al_ProgressDrawables.get(al_colourSequence.get(4)));
            seekbar06.setProgressDrawable(al_ProgressDrawables.get(al_colourSequence.get(5)));
        }


        // create listener for seekbars
        mlistener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bounceback();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                String temp = seekBar.getTag().toString();
                showName(temp);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                compileProgress();
                hideName();

            }

        };

        seekbar01.setOnSeekBarChangeListener(mlistener);
        seekbar02.setOnSeekBarChangeListener(mlistener);
        seekbar03.setOnSeekBarChangeListener(mlistener);
        seekbar04.setOnSeekBarChangeListener(mlistener);
        seekbar05.setOnSeekBarChangeListener(mlistener);
        seekbar06.setOnSeekBarChangeListener(mlistener);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        testContext = context;

        Activity activity = (Activity) context;

        // making a try catch to check if this is initialised or not.
        try {
            sendValuesInterface_Frag06 = (Fragment_Input_06.interface_Frag06) activity;
            // we are just trying to get the interface linked to the activity this fragement
            // attaches to. We use the context of what this is Attached to to do this.
            //The context is cast to an activity and that is cast to the interface object.
        } catch (ClassCastException e){
            throw new ClassCastException((activity.toString()+"Must override onMessageRead...."));
        }

    }


    // this method is used to gather the seekbar progress and send it onto the activity for display.
    private void compileProgress(){
        testList.clear();
        Integer temp_hours;
        Integer temp_minutes;

        try {
            temp_hours = Integer.parseInt(et_Hours.getText().toString());
            temp_minutes = Integer.parseInt(et_Minutes.getText().toString());

        }catch (Exception e){
            Log.d("Fragment_Input_06","Reading the fragment time input");
            temp_hours = 0;
            temp_minutes = 0;
        }



        // put all values in an object to send from the fragement to the activity.
        testList.add(Integer.parseInt(temp_hours.toString()));
        testList.add(Integer.parseInt(temp_minutes.toString()));
        testList.add(seekbar01.getProgress());
        testList.add(seekbar02.getProgress());
        testList.add(seekbar03.getProgress());
        testList.add(seekbar04.getProgress());
        testList.add(seekbar05.getProgress());
        testList.add(seekbar06.getProgress());

        sendValuesInterface_Frag06.onMessageRead(testList);

    }


    // this is used to alter the other seekbars on the fly.
    // this method works surprisingly ok given it is suppose to set back the progressbar being used at
    // the time it is being envoked. Not gonna fix anything if it isnt broken though :-)

    private void bounceback(){
        int pro01 = seekbar01.getProgress();
        int pro02 = seekbar02.getProgress();
        int pro03 = seekbar03.getProgress();
        int pro04 = seekbar04.getProgress();
        int pro05 = seekbar05.getProgress();
        int pro06 = seekbar06.getProgress();
        int sum = pro01+pro02+pro03+pro04+pro05+pro06;
        double max =100.00;

        if (sum>max){
            double ratio = max/(double)sum;
            int newpro01 = (int) Math.round(pro01*ratio);
            int newpro02 = (int) Math.round(pro02*ratio);
            int newpro03 = (int) Math.round(pro03*ratio);
            int newpro04 = (int) Math.round(pro04*ratio);
            int newpro05 = (int) Math.round(pro05*ratio);
            int newpro06 = (int) Math.round(pro06*ratio);
//            seekbar01.setProgress(newpro01);
//            seekbar02.setProgress(newpro02);
//            seekbar03.setProgress(newpro03);
//            seekbar04.setProgress(newpro04);
//            seekbar05.setProgress(newpro05);
            // this is to do the bouceback
            // instead do this to just update figures
            tv_Pro_01.setText( Integer.toString(newpro01));
            tv_Pro_02.setText( Integer.toString(newpro02));
            tv_Pro_03.setText( Integer.toString(newpro03));
            tv_Pro_04.setText( Integer.toString(newpro04));
            tv_Pro_05.setText( Integer.toString(newpro05));
            tv_Pro_06.setText( Integer.toString(newpro06));

        }


    }

    private void showName(String value){
        tv_ThemeName.setText(al_themeNames.get(Integer.parseInt(value)));
        tv_ThemeName.setVisibility(View.VISIBLE);
    }

    private void hideName(){
        tv_ThemeName.setVisibility(View.INVISIBLE);
    }


}
