package com.example.kennethallan.Bloom_TB_002;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddEvent extends AppCompatActivity implements Fragment_Input_12.interface_Frag12,Fragment_Input_11.interface_Frag11,Fragment_Input_10.interface_Frag10,Fragment_Input_09.interface_Frag09,Fragment_Input_08.interface_Frag08,Fragment_Input_07.interface_Frag07,Fragment_Input_06.interface_Frag06,
        Fragment_Input_05.interface_Frag05,Fragment_Input_04.interface_Frag04, Fragment_Input_03.interface_Frag03{

    private static final String TAG = MainActivity.class.getSimpleName();

    DBHelper Mydb;
    Button saveButton;
    EditText eventName;
    EditText eventDescription;
    ArrayList<String> al_GlobalSliderValues = new ArrayList<String>(); // arraylist for the values extracted from the sliders
    int numAdapterValues;
    int event_InputTime = 0; // to initalise the values
    int  goal_InputTime_Hours;
    int  goal_InputTime_Minutes;

    int numCurrentThemes;
    View fragmentHolder;
    List<Integer> compiledValues = new ArrayList<>();
    ArrayList<String> al_EventsValues = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        saveButton = (Button)findViewById(R.id.saveButton);
        eventName = (EditText) findViewById(R.id.editText);
        eventDescription = (EditText)findViewById(R.id.editText2);
        fragmentHolder = findViewById(R.id.Fragment_Holder);


        Mydb = new DBHelper(this);

        addEvent ();

        // look though SQLite to fetch number of themes
        Mydb.getCURRENTThemeNames();
        numCurrentThemes = Mydb.getNumberOfCURRENTThemeIDs();

        // TODO how to send the names of the themes to be displayed in the fragment.

        // load fragment
        if (fragmentHolder != null) {


            if (savedInstanceState != null) {
                return;
            }// this is like an exit if something has gone wrong for this to load????
            if (numCurrentThemes == 12) {
                Fragment_Input_12 myFragment = new Fragment_Input_12();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 11) {
                Fragment_Input_11 myFragment = new Fragment_Input_11();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 10) {
                Fragment_Input_10 myFragment = new Fragment_Input_10();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 9) {
                Fragment_Input_09 myFragment = new Fragment_Input_09();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 8) {
                Fragment_Input_08 myFragment = new Fragment_Input_08();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 7) {
                Fragment_Input_07 myFragment = new Fragment_Input_07();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 6) {
                Fragment_Input_06 myFragment = new Fragment_Input_06();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 5) {
                Fragment_Input_05 myFragment = new Fragment_Input_05();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 4) {
                Fragment_Input_04 myFragment = new Fragment_Input_04();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }
            if (numCurrentThemes == 3) {
                Fragment_Input_03 myFragment = new Fragment_Input_03();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.Fragment_Holder, myFragment, null);
                fragmentTransaction.commit();
            }


        }

    }

    @Override
    public void onMessageRead(List<Integer> message) {
        // clear old values
 //       compiledValues.clear(); // TODO conclude if a problem
        // attach new values to array
        compiledValues = message;
        // create goal_InputTime using minutes and hourse values from List from Fragment.
        goal_InputTime_Hours = compiledValues.get(0);
        goal_InputTime_Minutes = compiledValues.get(1);

        // Remove hours and minutes values from array so only left with seekbar values.
        compiledValues.remove(0);
        compiledValues.remove(0); // twice because arraylist shrinks each time so this way we only erase those first two rows

        // convert from integer array to string array from calulateGoals() TODO see if we can get around changing to a string array since we change it back to int in this method.
        ArrayList<String> al_temp = new ArrayList<String>();
        for (int i =0; i<compiledValues.size();i++){

            al_temp.add(Integer.toString(compiledValues.get(i)));
        }

        al_GlobalSliderValues = al_temp; //make global variable for use elsewhere.
    }


    public void addEvent (){

        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        event_InputTime = getEventTime(goal_InputTime_Hours,goal_InputTime_Minutes);
                        calculateGoals(al_GlobalSliderValues,event_InputTime);
                        String temp_eventName = eventName.getText().toString();
                        String temp_eventDescription = eventDescription.getText().toString();

                        // errorhandling data checks
                        if (event_InputTime == 0){
                            Toast.makeText(AddEvent.this, "CANNOT SAVE ACTIVITY. Event time must be input" , Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (temp_eventName == ""){
                            Toast.makeText(AddEvent.this, "CANNOT SAVE ACTIVITY. Event name required" , Toast.LENGTH_SHORT).show();
                            return;
                        }


                        boolean result = Mydb.insertActivity(temp_eventName,temp_eventDescription,al_EventsValues);
                        if (result){
                            Toast.makeText(AddEvent.this, "Succeeded To Input Activity", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddEvent.this, "Failed To Input Activity", Toast.LENGTH_SHORT).show();
                        }

//                        boolean isInserted = Mydb.insertTheme(eventName.getText().toString(),eventDescription.getText().toString());
//                        if (isInserted == true)
//                            Toast.makeText(AddEvent.this, "Data is inserted", Toast.LENGTH_SHORT).show();
//                        else
//                            Toast.makeText(AddEvent.this, "Data is NOT inserted", Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

    public int getEventTime(Integer eventHours, Integer eventMinutes){
        int  goal_InputTime_Hours = eventHours;
        int  goal_InputTime_Minutes = eventMinutes;
        double a = 60.0;
        int sum = goal_InputTime_Hours*60 + goal_InputTime_Minutes;
        return sum;
    }

    public void calculateGoals(ArrayList<String> valuesFromSliders,Integer freeTime ) {
        //sum values
        Integer sumNew = 0;
        Integer sumOld = 0;

        for (int i = 0; i < valuesFromSliders.size(); i++) {
            sumNew = Integer.parseInt(valuesFromSliders.get(i));
            sumOld = sumOld + sumNew;
        }

        double max = (double) freeTime;

        double ratio = max / (double) sumOld;
        al_EventsValues.clear(); // clear slider to ensure no mixing with old values.

        for (int i = 0; i < valuesFromSliders.size(); i++) {
            int factoredGoalTime = (int) Math.round(Integer.parseInt(valuesFromSliders.get(i)) * ratio);
            al_EventsValues.add(Integer.toString(factoredGoalTime));
        }

    }


}
