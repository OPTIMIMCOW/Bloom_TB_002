package com.example.kennethallan.Bloom_TB_002;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Kenneth Allan on 06/02/2019.
 */

public class Fragment_Utilities {

    // Persistent variables

    public static String BUNDLE_NAME;
    public static String BUNDLE_GOAL;
    public static String BUNDLE_ATTAIN;
    public static String BUNDLE_SCALEFACTOR;
    public static String BUNDLE_COLOURSEQUENCE;

    Context testContext;

    public Fragment_Utilities(Context context){

        testContext = context;
        BUNDLE_NAME = testContext.getResources().getString(R.string.bundle_name);
        BUNDLE_GOAL = testContext.getResources().getString(R.string.bundle_goal);
        BUNDLE_ATTAIN = testContext.getResources().getString(R.string.bundle_attain);
        BUNDLE_SCALEFACTOR = testContext.getResources().getString(R.string.bundle_scalefactor);
        BUNDLE_COLOURSEQUENCE = testContext.getResources().getString(R.string.bundle_coloursequence);

    }


    public ArrayList<Drawable> AssembleColours(){

        ArrayList<Drawable> al_ProgressDrawables = new ArrayList<Drawable>(); // initialise arraylist

        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_01));
        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_02));
        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_03));
        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_04));
        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_05));
        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_06));
        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_07));
        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_08));
        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_09));
        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_10));
        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_11));
        al_ProgressDrawables.add(ContextCompat.getDrawable(testContext, R.drawable.theme_seekbar_path_colour_12));

        return al_ProgressDrawables;
    }

    public ArrayList<Integer> GetColourResourceIDs(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.color.Theme_base_01);
        arrayList.add(R.color.Theme_base_02);
        arrayList.add(R.color.Theme_base_03);
        arrayList.add(R.color.Theme_base_04);
        arrayList.add(R.color.Theme_base_05);
        arrayList.add(R.color.Theme_base_06);
        arrayList.add(R.color.Theme_base_07);
        arrayList.add(R.color.Theme_base_08);
        arrayList.add(R.color.Theme_base_09);
        arrayList.add(R.color.Theme_base_10);
        arrayList.add(R.color.Theme_base_11);
        arrayList.add(R.color.Theme_base_12);

        return arrayList;
    }

    public ArrayList<Integer> GetColourSequence(Bundle currentWeekBundle){
        // the purpose of this method is to get the colour sequence from the bundle and if it fails create a new one.

        ArrayList<Integer> al_colourSequence = new ArrayList<>(); // initalise array // not sure i need this step

        // make an intermediate variable because if the bundle is not made it will be set to null and we cannot work with it so
        // we use an intermeduate array so that we can judge it and choose how to form the al_colourSequence array to return.
        ArrayList<Integer> al_intermediate = currentWeekBundle.getIntegerArrayList(BUNDLE_COLOURSEQUENCE);

        if (al_intermediate == null){
            for (int i = 0 ; i<12; i++){
                // if no bundle created so no way to get al_colourSequence
                al_colourSequence.add(i);
            }
        }else{
            al_colourSequence = al_intermediate;
        }

        return al_colourSequence;
    }


}
