package com.example.kennethallan.Bloom_TB_002;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Output_11 extends Fragment {

    private ProgressBar pb_Goal_01;
    private ProgressBar pb_Goal_02;
    private ProgressBar pb_Goal_03;
    private ProgressBar pb_Goal_04;
    private ProgressBar pb_Goal_05;
    private ProgressBar pb_Goal_06;
    private ProgressBar pb_Goal_07;
    private ProgressBar pb_Goal_08;
    private ProgressBar pb_Goal_09;
    private ProgressBar pb_Goal_10;
    private ProgressBar pb_Goal_11;

    private ProgressBar pb_Attain_01;
    private ProgressBar pb_Attain_02;
    private ProgressBar pb_Attain_03;
    private ProgressBar pb_Attain_04;
    private ProgressBar pb_Attain_05;
    private ProgressBar pb_Attain_06;
    private ProgressBar pb_Attain_07;
    private ProgressBar pb_Attain_08;
    private ProgressBar pb_Attain_09;
    private ProgressBar pb_Attain_10;
    private ProgressBar pb_Attain_11;

    private TextView tv_Pro_01;
    private TextView tv_Pro_02;
    private TextView tv_Pro_03;
    private TextView tv_Pro_04;
    private TextView tv_Pro_05;
    private TextView tv_Pro_06;
    private TextView tv_Pro_07;
    private TextView tv_Pro_08;
    private TextView tv_Pro_09;
    private TextView tv_Pro_10;
    private TextView tv_Pro_11;


    Fragment_Output_11.interface_Frag11 sendValuesInterface_Frag11;

    public String BUNDLE_NAME = "";
    public String BUNDLE_GOAL = "";
    public String BUNDLE_ATTAIN = "";
    public String BUNDLE_SCALEFACTOR = "";

    public Fragment_Output_11() {
        // Required empty public constructor
    }

    public interface interface_Frag11 {

        public void onMessageRead(List<Boolean> message);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__output_11, container, false);

        pb_Goal_01 = (ProgressBar) view.findViewById(R.id.pb_Goal_01);
        pb_Goal_02 = (ProgressBar) view.findViewById(R.id.pb_Goal_02);
        pb_Goal_03 = (ProgressBar) view.findViewById(R.id.pb_Goal_03);
        pb_Goal_04 = (ProgressBar) view.findViewById(R.id.pb_Goal_04);
        pb_Goal_05 = (ProgressBar) view.findViewById(R.id.pb_Goal_05);
        pb_Goal_06 = (ProgressBar) view.findViewById(R.id.pb_Goal_06);
        pb_Goal_07 = (ProgressBar) view.findViewById(R.id.pb_Goal_07);
        pb_Goal_08 = (ProgressBar) view.findViewById(R.id.pb_Goal_08);
        pb_Goal_09 = (ProgressBar) view.findViewById(R.id.pb_Goal_09);
        pb_Goal_10 = (ProgressBar) view.findViewById(R.id.pb_Goal_10);
        pb_Goal_11 = (ProgressBar) view.findViewById(R.id.pb_Goal_11);

        pb_Attain_01 = (ProgressBar) view.findViewById(R.id.pb_Attain_01);
        pb_Attain_02 = (ProgressBar) view.findViewById(R.id.pb_Attain_02);
        pb_Attain_03 = (ProgressBar) view.findViewById(R.id.pb_Attain_03);
        pb_Attain_04 = (ProgressBar) view.findViewById(R.id.pb_Attain_04);
        pb_Attain_05 = (ProgressBar) view.findViewById(R.id.pb_Attain_05);
        pb_Attain_06 = (ProgressBar) view.findViewById(R.id.pb_Attain_06);
        pb_Attain_07 = (ProgressBar) view.findViewById(R.id.pb_Attain_07);
        pb_Attain_08 = (ProgressBar) view.findViewById(R.id.pb_Attain_08);
        pb_Attain_09 = (ProgressBar) view.findViewById(R.id.pb_Attain_09);
        pb_Attain_10 = (ProgressBar) view.findViewById(R.id.pb_Attain_10);
        pb_Attain_11 = (ProgressBar) view.findViewById(R.id.pb_Attain_11);

        tv_Pro_01 = (TextView) view.findViewById(R.id.tv_num_01);
        tv_Pro_02 = (TextView) view.findViewById(R.id.tv_num_02);
        tv_Pro_03 = (TextView) view.findViewById(R.id.tv_num_03);
        tv_Pro_04 = (TextView) view.findViewById(R.id.tv_num_04);
        tv_Pro_05 = (TextView) view.findViewById(R.id.tv_num_05);
        tv_Pro_06 = (TextView) view.findViewById(R.id.tv_num_06);
        tv_Pro_07 = (TextView) view.findViewById(R.id.tv_num_07);
        tv_Pro_08 = (TextView) view.findViewById(R.id.tv_num_08);
        tv_Pro_09 = (TextView) view.findViewById(R.id.tv_num_09);
        tv_Pro_10 = (TextView) view.findViewById(R.id.tv_num_10);
        tv_Pro_11 = (TextView) view.findViewById(R.id.tv_num_11);


        // working with bundles
        //getting names of bundles
        BUNDLE_NAME = getResources().getString(R.string.bundle_name);
        BUNDLE_GOAL = getResources().getString(R.string.bundle_goal);
        BUNDLE_ATTAIN = getResources().getString(R.string.bundle_attain);
        BUNDLE_SCALEFACTOR = getResources().getString(R.string.bundle_scalefactor);

        // get info from bundles
        //names
        String name_01 = getArguments().getString(BUNDLE_NAME + "0");
        String name_02 = getArguments().getString(BUNDLE_NAME + "1");
        String name_03 = getArguments().getString(BUNDLE_NAME + "2");
        String name_04 = getArguments().getString(BUNDLE_NAME + "3");
        String name_05 = getArguments().getString(BUNDLE_NAME + "4");
        String name_06 = getArguments().getString(BUNDLE_NAME + "5");
        String name_07 = getArguments().getString(BUNDLE_NAME + "6");
        String name_08 = getArguments().getString(BUNDLE_NAME + "7");
        String name_09 = getArguments().getString(BUNDLE_NAME + "8");
        String name_10 = getArguments().getString(BUNDLE_NAME + "9");
        String name_11 = getArguments().getString(BUNDLE_NAME + "10");

        //goals
        Integer goals_01 = getArguments().getInt(BUNDLE_GOAL + "0");
        Integer goals_02 = getArguments().getInt(BUNDLE_GOAL + "1");
        Integer goals_03 = getArguments().getInt(BUNDLE_GOAL + "2");
        Integer goals_04 = getArguments().getInt(BUNDLE_GOAL + "3");
        Integer goals_05 = getArguments().getInt(BUNDLE_GOAL + "4");
        Integer goals_06 = getArguments().getInt(BUNDLE_GOAL + "5");
        Integer goals_07 = getArguments().getInt(BUNDLE_GOAL + "6");
        Integer goals_08 = getArguments().getInt(BUNDLE_GOAL + "7");
        Integer goals_09 = getArguments().getInt(BUNDLE_GOAL + "8");
        Integer goals_10 = getArguments().getInt(BUNDLE_GOAL + "9");
        Integer goals_11 = getArguments().getInt(BUNDLE_GOAL + "10");

        //attain
        Integer attain_01 = getArguments().getInt(BUNDLE_ATTAIN + "0");
        Integer attain_02 = getArguments().getInt(BUNDLE_ATTAIN + "1");
        Integer attain_03 = getArguments().getInt(BUNDLE_ATTAIN + "2");
        Integer attain_04 = getArguments().getInt(BUNDLE_ATTAIN + "3");
        Integer attain_05 = getArguments().getInt(BUNDLE_ATTAIN + "4");
        Integer attain_06 = getArguments().getInt(BUNDLE_ATTAIN + "5");
        Integer attain_07 = getArguments().getInt(BUNDLE_ATTAIN + "6");
        Integer attain_08 = getArguments().getInt(BUNDLE_ATTAIN + "7");
        Integer attain_09 = getArguments().getInt(BUNDLE_ATTAIN + "8");
        Integer attain_10 = getArguments().getInt(BUNDLE_ATTAIN + "9");
        Integer attain_11 = getArguments().getInt(BUNDLE_ATTAIN + "10");

        //scale factor
        Double scaleFactor = getArguments().getDouble(BUNDLE_SCALEFACTOR);

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
        pb_Goal_08.setProgress((int) Math.round(goals_08*scaleFactor));
        pb_Goal_09.setProgress((int) Math.round(goals_09*scaleFactor));
        pb_Goal_10.setProgress((int) Math.round(goals_10*scaleFactor));
        pb_Goal_11.setProgress((int) Math.round(goals_11*scaleFactor));

        //goals
        pb_Attain_01.setProgress((int) Math.round(attain_01*scaleFactor));
        pb_Attain_02.setProgress((int) Math.round(attain_02*scaleFactor));
        pb_Attain_03.setProgress((int) Math.round(attain_03*scaleFactor));
        pb_Attain_04.setProgress((int) Math.round(attain_04*scaleFactor));
        pb_Attain_05.setProgress((int) Math.round(attain_05*scaleFactor));
        pb_Attain_06.setProgress((int) Math.round(attain_06*scaleFactor));
        pb_Attain_07.setProgress((int) Math.round(attain_07*scaleFactor));
        pb_Attain_08.setProgress((int) Math.round(attain_08*scaleFactor));
        pb_Attain_09.setProgress((int) Math.round(attain_09*scaleFactor));
        pb_Attain_10.setProgress((int) Math.round(attain_10*scaleFactor));
        pb_Attain_11.setProgress((int) Math.round(attain_11*scaleFactor));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;


        // making a try catch to check if this is initialised or not.
        try {
            sendValuesInterface_Frag11 = (Fragment_Output_11.interface_Frag11) activity;
            // we are just trying to get the interface linked to the activity this fragement
            // attaches to. We use the context of what this is Attached to to do this.
            //The context is cast to an activity and that is cast to the interface object.
        } catch (ClassCastException e){
            throw new ClassCastException((activity.toString()+"Must override onMessageRead...."));
        }

    }

}
