package com.example.kennethallan.Bloom_TB_002;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Input_03 extends Fragment {

    DBHelper Mydb;

    private SeekBar seekbar01;
    private SeekBar seekbar02;
    private SeekBar seekbar03;

    private TextView tv_Pro_01;
    private TextView tv_Pro_02;
    private TextView tv_Pro_03;
    private TextView tv_ThemeName;

    private EditText et_Hours;
    private EditText et_Minutes;

    SeekBar.OnSeekBarChangeListener mlistener;
    EditText.OnClickListener mlistener2;

    List<String> al_themeNames;
    //int testValue;
    List<Integer> testList = new ArrayList<>();

    Fragment_Input_03.interface_Frag03 sendValuesInterface_Frag03;

    public String BUNDLE_NAME = "";
    public String BUNDLE_GOAL = "";
    public String BUNDLE_ATTAIN = "";
    public String BUNDLE_SCALEFACTOR = "";

    public Integer numThemes = 3;


    public Fragment_Input_03() {
        // Required empty public constructor
    }

    public interface interface_Frag03 {
        public void onMessageRead(List<Integer> message);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__input_03, container, false);

        seekbar01 = (SeekBar) view.findViewById(R.id.seekBar1);
        seekbar02 = (SeekBar) view.findViewById(R.id.seekBar2);
        seekbar03 = (SeekBar) view.findViewById(R.id.seekBar3);
        seekbar01.setTag(0);
        seekbar02.setTag(1);
        seekbar03.setTag(2);


        tv_Pro_01 = (TextView) view.findViewById(R.id.tv_num_01);
        tv_Pro_02 = (TextView) view.findViewById(R.id.tv_num_02);
        tv_Pro_03 = (TextView) view.findViewById(R.id.tv_num_03);
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
        //getting names of bundle content
        BUNDLE_NAME = getResources().getString(R.string.bundle_name);
        BUNDLE_GOAL = getResources().getString(R.string.bundle_goal);
        BUNDLE_ATTAIN = getResources().getString(R.string.bundle_attain);
        BUNDLE_SCALEFACTOR = getResources().getString(R.string.bundle_scalefactor);

        Bundle currentWeekBundle = this.getArguments(); // get bundle
        if (currentWeekBundle != null){

            //goals //  get information from bundle
            Integer goals_01 = currentWeekBundle.getInt(BUNDLE_GOAL + "0");
            Integer goals_02 = currentWeekBundle.getInt(BUNDLE_GOAL + "1");
            Integer goals_03 = currentWeekBundle.getInt(BUNDLE_GOAL + "2");

            //TODO investigate whether we can do all our database stuff in main activity and load this up by passing bundles.

            Double scaleFactor = currentWeekBundle.getDouble(BUNDLE_SCALEFACTOR);
            Double alterfor100Factor = scaleFactor*(100.00/90.00); // used to alter the scale factor so as to not limit it for overachieve goal purposes in output fragements.
            // IMPORTANT - KEEP the two decimal places or the doubles dont round to two decimal places.
            // //////////////////SET PROGRESS OF seekbars //////////////////////////////
            seekbar01.setProgress((int) Math.round(goals_01*alterfor100Factor));
            seekbar02.setProgress((int) Math.round(goals_02*alterfor100Factor));
            seekbar03.setProgress((int) Math.round(goals_03*alterfor100Factor));
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

        mlistener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               compileProgress();
            }
        };

        et_Hours.setOnClickListener(mlistener2);
        et_Minutes.setOnClickListener(mlistener2);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;


        // making a try catch to check if this is initialised or not.
        try {
            sendValuesInterface_Frag03 = (Fragment_Input_03.interface_Frag03) activity;
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
            Log.d("Fragment_Input_03","Reading the fragment time input");
            temp_hours = 0;
            temp_minutes = 0;
        }



        // put all values in an object to send from the fragement to the activity.
        testList.add(Integer.parseInt(temp_hours.toString()));
        testList.add(Integer.parseInt(temp_minutes.toString()));
        testList.add(seekbar01.getProgress());
        testList.add(seekbar02.getProgress());
        testList.add(seekbar03.getProgress());

        sendValuesInterface_Frag03.onMessageRead(testList);

    }


    // this is used to alter the other seekbars on the fly.
    // this method works surprisingly ok given it is suppose to set back the progressbar being used at
    // the time it is being envoked. Not gonna fix anything if it isnt broken though :-)

    private void bounceback(){
        int pro01 = seekbar01.getProgress();
        int pro02 = seekbar02.getProgress();
        int pro03 = seekbar03.getProgress();
        int sum = pro01+pro02+pro03;
        double max =100.00;

        if (sum>max){
            double ratio = max/(double)sum;
            int newpro01 = (int) Math.round(pro01*ratio);
            int newpro02 = (int) Math.round(pro02*ratio);
            int newpro03 = (int) Math.round(pro03*ratio);
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
