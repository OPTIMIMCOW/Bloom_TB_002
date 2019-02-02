package com.example.kennethallan.Bloom_TB_002;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Output_07 extends Fragment {

    private ProgressBar pb_Goal_01;
    private ProgressBar pb_Goal_02;
    private ProgressBar pb_Goal_03;
    private ProgressBar pb_Goal_04;
    private ProgressBar pb_Goal_05;
    private ProgressBar pb_Goal_06;
    private ProgressBar pb_Goal_07;

    private ProgressBar pb_Attain_01;
    private ProgressBar pb_Attain_02;
    private ProgressBar pb_Attain_03;
    private ProgressBar pb_Attain_04;
    private ProgressBar pb_Attain_05;
    private ProgressBar pb_Attain_06;
    private ProgressBar pb_Attain_07;

    private TextView tv_Pro_01;
    private TextView tv_Pro_02;
    private TextView tv_Pro_03;
    private TextView tv_Pro_04;
    private TextView tv_Pro_05;
    private TextView tv_Pro_06;
    private TextView tv_Pro_07;

    private CheckBox cb_01;
    private CheckBox cb_02;
    private CheckBox cb_03;
    private CheckBox cb_04;
    private CheckBox cb_05;
    private CheckBox cb_06;
    private CheckBox cb_07;

    private ArrayList<Boolean> al_carryOverCheck;

    CheckBox.OnClickListener mlistener;

    Fragment_Output_07.interface_Frag07 sendValuesInterface_Frag07;

    public String BUNDLE_NAME = "";
    public String BUNDLE_GOAL = "";
    public String BUNDLE_ATTAIN = "";
    public String BUNDLE_SCALEFACTOR = "";
    public String BUNDLE_SUMMARYTOGGLE = "";

    public Integer numThemes = 7;

    public Fragment_Output_07() {
        // Required empty public constructor
    }

    public interface interface_Frag07 {

        public void onMessageRead(List<Boolean> message);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__output_07, container, false);

        pb_Goal_01 = (ProgressBar) view.findViewById(R.id.pb_Goal_01);
        pb_Goal_02 = (ProgressBar) view.findViewById(R.id.pb_Goal_02);
        pb_Goal_03 = (ProgressBar) view.findViewById(R.id.pb_Goal_03);
        pb_Goal_04 = (ProgressBar) view.findViewById(R.id.pb_Goal_04);
        pb_Goal_05 = (ProgressBar) view.findViewById(R.id.pb_Goal_05);
        pb_Goal_06 = (ProgressBar) view.findViewById(R.id.pb_Goal_06);
        pb_Goal_07 = (ProgressBar) view.findViewById(R.id.pb_Goal_07);

        pb_Attain_01 = (ProgressBar) view.findViewById(R.id.pb_Attain_01);
        pb_Attain_02 = (ProgressBar) view.findViewById(R.id.pb_Attain_02);
        pb_Attain_03 = (ProgressBar) view.findViewById(R.id.pb_Attain_03);
        pb_Attain_04 = (ProgressBar) view.findViewById(R.id.pb_Attain_04);
        pb_Attain_05 = (ProgressBar) view.findViewById(R.id.pb_Attain_05);
        pb_Attain_06 = (ProgressBar) view.findViewById(R.id.pb_Attain_06);
        pb_Attain_07 = (ProgressBar) view.findViewById(R.id.pb_Attain_07);

        tv_Pro_01 = (TextView) view.findViewById(R.id.tv_num_01);
        tv_Pro_02 = (TextView) view.findViewById(R.id.tv_num_02);
        tv_Pro_03 = (TextView) view.findViewById(R.id.tv_num_03);
        tv_Pro_04 = (TextView) view.findViewById(R.id.tv_num_04);
        tv_Pro_05 = (TextView) view.findViewById(R.id.tv_num_05);
        tv_Pro_06 = (TextView) view.findViewById(R.id.tv_num_06);
        tv_Pro_07 = (TextView) view.findViewById(R.id.tv_num_07);

        cb_01 = (CheckBox) view.findViewById(R.id.cb_01);
        cb_02 = (CheckBox) view.findViewById(R.id.cb_02);
        cb_03 = (CheckBox) view.findViewById(R.id.cb_03);
        cb_04 = (CheckBox) view.findViewById(R.id.cb_04);
        cb_05 = (CheckBox) view.findViewById(R.id.cb_05);
        cb_06 = (CheckBox) view.findViewById(R.id.cb_06);
        cb_07 = (CheckBox) view.findViewById(R.id.cb_07);


        // working with bundles
        //getting names of bundles
        BUNDLE_NAME = getResources().getString(R.string.bundle_name);
        BUNDLE_GOAL = getResources().getString(R.string.bundle_goal);
        BUNDLE_ATTAIN = getResources().getString(R.string.bundle_attain);
        BUNDLE_SCALEFACTOR = getResources().getString(R.string.bundle_scalefactor);
        BUNDLE_SUMMARYTOGGLE = getResources().getString(R.string.bundle_summarytoggle);

        // get info from bundles
        //names
        ArrayList<String> al_Bundle_Name = new ArrayList<String>();
        al_Bundle_Name = getArguments().getStringArrayList(BUNDLE_NAME);

        //goals
        ArrayList<Integer> al_Bundle_GoalVal = new ArrayList<Integer>();
        al_Bundle_GoalVal = getArguments().getIntegerArrayList(BUNDLE_GOAL);
        Integer goals_01 = al_Bundle_GoalVal.get(0);
        Integer goals_02 = al_Bundle_GoalVal.get(1);
        Integer goals_03 = al_Bundle_GoalVal.get(2);
        Integer goals_04 = al_Bundle_GoalVal.get(3);
        Integer goals_05 = al_Bundle_GoalVal.get(4);
        Integer goals_06 = al_Bundle_GoalVal.get(5);
        Integer goals_07 = al_Bundle_GoalVal.get(6);

        //attain
        ArrayList<Integer> al_Bundle_AttainVal = new ArrayList<Integer>();
        al_Bundle_AttainVal = getArguments().getIntegerArrayList(BUNDLE_ATTAIN);
        Integer attain_01 = al_Bundle_AttainVal.get(0);
        Integer attain_02 = al_Bundle_AttainVal.get(1);
        Integer attain_03 = al_Bundle_AttainVal.get(2);
        Integer attain_04 = al_Bundle_AttainVal.get(3);
        Integer attain_05 = al_Bundle_AttainVal.get(4);
        Integer attain_06 = al_Bundle_AttainVal.get(5);
        Integer attain_07 = al_Bundle_AttainVal.get(6);

        // scale factor
        Double scaleFactor = getArguments().getDouble(BUNDLE_SCALEFACTOR);

        //summary toggle - is this a summary or just current week?
        Boolean summaryToggle = getArguments().getBoolean(BUNDLE_SUMMARYTOGGLE);

        // get info from bundles
        //names
        //goals
        pb_Goal_01.setProgress((int) Math.round(goals_01*scaleFactor));
        pb_Goal_02.setProgress((int) Math.round(goals_02*scaleFactor));
        pb_Goal_03.setProgress((int) Math.round(goals_03*scaleFactor));
        pb_Goal_04.setProgress((int) Math.round(goals_04*scaleFactor));
        pb_Goal_05.setProgress((int) Math.round(goals_05*scaleFactor));
        pb_Goal_06.setProgress((int) Math.round(goals_06*scaleFactor));
        pb_Goal_07.setProgress((int) Math.round(goals_07*scaleFactor));

        //goals
        pb_Attain_01.setProgress((int) Math.round(attain_01*scaleFactor));
        pb_Attain_02.setProgress((int) Math.round(attain_02*scaleFactor));
        pb_Attain_03.setProgress((int) Math.round(attain_03*scaleFactor));
        pb_Attain_04.setProgress((int) Math.round(attain_04*scaleFactor));
        pb_Attain_05.setProgress((int) Math.round(attain_05*scaleFactor));
        pb_Attain_06.setProgress((int) Math.round(attain_06*scaleFactor));
        pb_Attain_07.setProgress((int) Math.round(attain_07*scaleFactor));

        // /////////////////////SET CHECKBOX VISIBILITY ////////////////////////////////

        if (summaryToggle){

            //for theme 01
            try { // try in case infinity if attain_01 = 0
                int temp_01 = goals_01 / attain_01;
                if (temp_01 < 0.9 || temp_01 > 1.10) {
                    cb_01.setVisibility(View.VISIBLE);
                }
            }catch(Exception e){

            }

            //for theme 02
            try {
                int temp_02 = goals_02 / attain_02;
                if (temp_02 < 0.9 || temp_02 > 1.10) {
                    cb_02.setVisibility(View.VISIBLE);
                }
            }catch(Exception e){

            }

            //for theme 03
            try {
                int temp_03 = goals_03 / attain_03;
                if (temp_03 < 0.9 || temp_03 > 1.1) {
                    cb_03.setVisibility(View.VISIBLE);
                }
            }catch(Exception e){

            }

            //for theme 04
            try {
                int temp_04 = goals_04 / attain_04;
                if (temp_04 < 0.9 || temp_04 > 1.1) {
                    cb_04.setVisibility(View.VISIBLE);
                }
            }catch(Exception e){

            }

            //for theme 05
            try {
                int temp_05 = goals_05 / attain_05;
                if (temp_05 < 0.9 || temp_05 > 1.1) {
                    cb_05.setVisibility(View.VISIBLE);
                }
            }catch(Exception e){

            }

            //for theme 06
            try {
                int temp_06 = goals_06 / attain_06;
                if (temp_06 < 0.9 || temp_06 > 1.1) {
                    cb_06.setVisibility(View.VISIBLE);
                }
            }catch(Exception e){

            }

            //for theme 07
            try {
                int temp_07 = goals_07 / attain_07;
                if (temp_07 < 0.9 || temp_07 > 1.1) {
                    cb_07.setVisibility(View.VISIBLE);
                }
            }catch(Exception e){

            }

        }

        // //////////////////////CARRY OVER FUNCTIONALITY ////////////////////////////////
        // set up click listeners for carry over
        mlistener = new CheckBox.OnClickListener(){
            @Override
            public void onClick(View view) {
                getCheckboxClick();
            }
        };

        // apply listeners to all seekbars
        cb_01.setOnClickListener(mlistener);
        cb_02.setOnClickListener(mlistener);
        cb_03.setOnClickListener(mlistener);
        cb_04.setOnClickListener(mlistener);
        cb_05.setOnClickListener(mlistener);
        cb_06.setOnClickListener(mlistener);
        cb_07.setOnClickListener(mlistener);


        al_carryOverCheck = new ArrayList<Boolean>();

        for (int i =0;i<numThemes;i++){
            al_carryOverCheck.add(false);
        }

        // use tags to identify which checkboxes apply to which values
        //TODO find out if this is required since i dont think i am sending tags back through the interface
        cb_01.setTag("Theme 01");
        cb_02.setTag("Theme 02");
        cb_03.setTag("Theme 03");
        cb_04.setTag("Theme 04");
        cb_05.setTag("Theme 05");
        cb_06.setTag("Theme 06");
        cb_07.setTag("Theme 07");

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;


        // making a try catch to check if this is initialised or not.
        try {
            sendValuesInterface_Frag07 = (Fragment_Output_07.interface_Frag07) activity;
            // we are just trying to get the interface linked to the activity this fragement
            // attaches to. We use the context of what this is Attached to to do this.
            //The context is cast to an activity and that is cast to the interface object.
        } catch (ClassCastException e){
            throw new ClassCastException((activity.toString()+"Must override onMessageRead...."));
        }

    }

    public void getCheckboxClick(){
        al_carryOverCheck.set(0,cb_01.isChecked());
        al_carryOverCheck.set(1,cb_02.isChecked());
        al_carryOverCheck.set(2,cb_03.isChecked());
        al_carryOverCheck.set(3,cb_04.isChecked());
        al_carryOverCheck.set(4,cb_05.isChecked());
        al_carryOverCheck.set(5,cb_06.isChecked());
        al_carryOverCheck.set(6,cb_07.isChecked());

        // send vales to interface
        sendValuesInterface_Frag07.onMessageRead(al_carryOverCheck);
    }
}
