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
public class Fragment_Output_05 extends Fragment {

    private ProgressBar pb_Goal_01;
    private ProgressBar pb_Goal_02;
    private ProgressBar pb_Goal_03;
    private ProgressBar pb_Goal_04;
    private ProgressBar pb_Goal_05;

    private ProgressBar pb_Attain_01;
    private ProgressBar pb_Attain_02;
    private ProgressBar pb_Attain_03;
    private ProgressBar pb_Attain_04;
    private ProgressBar pb_Attain_05;

    private TextView tv_Pro_01;
    private TextView tv_Pro_02;
    private TextView tv_Pro_03;
    private TextView tv_Pro_04;
    private TextView tv_Pro_05;

    Fragment_Output_05.interface_Frag05 sendValuesInterface_Frag05;

    public String BUNDLE_NAME = "";
    public String BUNDLE_GOAL = "";
    public String BUNDLE_ATTAIN = "";
    public String BUNDLE_SCALEFACTOR = "";

    public Fragment_Output_05() {
        // Required empty public constructor
    }

    public interface interface_Frag05 {

        public void onMessageRead(List<Boolean> message);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__output_05, container, false);

        pb_Goal_01 = (ProgressBar) view.findViewById(R.id.pb_Goal_01);
        pb_Goal_02 = (ProgressBar) view.findViewById(R.id.pb_Goal_02);
        pb_Goal_03 = (ProgressBar) view.findViewById(R.id.pb_Goal_03);
        pb_Goal_04 = (ProgressBar) view.findViewById(R.id.pb_Goal_04);
        pb_Goal_05 = (ProgressBar) view.findViewById(R.id.pb_Goal_05);

        pb_Attain_01 = (ProgressBar) view.findViewById(R.id.pb_Attain_01);
        pb_Attain_02 = (ProgressBar) view.findViewById(R.id.pb_Attain_02);
        pb_Attain_03 = (ProgressBar) view.findViewById(R.id.pb_Attain_03);
        pb_Attain_04 = (ProgressBar) view.findViewById(R.id.pb_Attain_04);
        pb_Attain_05 = (ProgressBar) view.findViewById(R.id.pb_Attain_05);

        tv_Pro_01 = (TextView) view.findViewById(R.id.tv_num_01);
        tv_Pro_02 = (TextView) view.findViewById(R.id.tv_num_02);
        tv_Pro_03 = (TextView) view.findViewById(R.id.tv_num_03);
        tv_Pro_04 = (TextView) view.findViewById(R.id.tv_num_04);
        tv_Pro_05 = (TextView) view.findViewById(R.id.tv_num_05);


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

        //goals
        Integer goals_01 = getArguments().getInt(BUNDLE_GOAL + "0");
        Integer goals_02 = getArguments().getInt(BUNDLE_GOAL + "1");
        Integer goals_03 = getArguments().getInt(BUNDLE_GOAL + "2");
        Integer goals_04 = getArguments().getInt(BUNDLE_GOAL + "3");
        Integer goals_05 = getArguments().getInt(BUNDLE_GOAL + "4");

        //attain
        Integer attain_01 = getArguments().getInt(BUNDLE_ATTAIN + "0");
        Integer attain_02 = getArguments().getInt(BUNDLE_ATTAIN + "1");
        Integer attain_03 = getArguments().getInt(BUNDLE_ATTAIN + "2");
        Integer attain_04 = getArguments().getInt(BUNDLE_ATTAIN + "3");
        Integer attain_05 = getArguments().getInt(BUNDLE_ATTAIN + "4");

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

        //goals
        pb_Attain_01.setProgress((int) Math.round(attain_01*scaleFactor));
        pb_Attain_02.setProgress((int) Math.round(attain_02*scaleFactor));
        pb_Attain_03.setProgress((int) Math.round(attain_03*scaleFactor));
        pb_Attain_04.setProgress((int) Math.round(attain_04*scaleFactor));
        pb_Attain_05.setProgress((int) Math.round(attain_05*scaleFactor));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;


        // making a try catch to check if this is initialised or not.
        try {
            sendValuesInterface_Frag05 = (Fragment_Output_05.interface_Frag05) activity;
            // we are just trying to get the interface linked to the activity this fragement
            // attaches to. We use the context of what this is Attached to to do this.
            //The context is cast to an activity and that is cast to the interface object.
        } catch (ClassCastException e){
            throw new ClassCastException((activity.toString()+"Must override onMessageRead...."));
        }

    }
}
