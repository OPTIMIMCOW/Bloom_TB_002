package com.example.kennethallan.Bloom_TB_002;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Kenneth Allan on 14/10/2018.
 */

public class Dialogue_AddTheme extends AppCompatDialogFragment {

    EditText et_input_ThemeName;
    EditText et_input_ThemeDescription;


    interface_Dia_AddTheme mlistener_interface_addtheme;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // create the inflater object to get our layout.
        LayoutInflater infalter = getActivity().getLayoutInflater();
        // use the view to hold our layout.
        View view = infalter.inflate(R.layout.dialogue_addtheme,null);

        // create builder object to construct the whole thing.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // create thing now.
        builder.setView(view)
                .setTitle("Add New Theme")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String themeName = et_input_ThemeName.getText().toString();
                        String themeDescription = et_input_ThemeDescription.getText().toString();
                        // use listener object to get access to method in the interface
                        mlistener_interface_addtheme.applyTexts(themeName,themeDescription);
                    }
                });
        et_input_ThemeName = (EditText) view.findViewById(R.id.et_Input_ThemeName);
        et_input_ThemeDescription = (EditText) view.findViewById(R.id.et_Input_ThemeDescription);

        // create method actually creates the object.
        return builder.create();

    }

    // this confirms that the interface has been established between this dialogue and the
    //activity. essentially it complains if it has not been throug the try catch of casting error.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            // initialise my listener
            mlistener_interface_addtheme = (interface_Dia_AddTheme) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement interface_Dia_AddTheme listener");
        }
    }

    public interface interface_Dia_AddTheme {
        // a method which sends the strings from this fialogue (fragement) to the activity.
        void applyTexts(String themeName, String themeDescription);

    }

}
