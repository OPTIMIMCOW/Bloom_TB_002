package com.example.kennethallan.Bloom_TB_002;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;

public class EditThemePage extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    DBHelper Mydb;
    Context contextWord = this;
    Button commitButton;
    EditText themeName;
    EditText themeDescription;
    ListView themeListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_theme_page);

        commitButton = (Button)findViewById(R.id.saveButton);
        themeName = (EditText) findViewById(R.id.editText);
        themeDescription = (EditText)findViewById(R.id.editText2);
        themeListView = (ListView)findViewById(R.id.ListViewSetGoals);



        Mydb = new DBHelper(this);


        addTheme ();


        // Populate arrayAdaptor
        populateListView(); // this method is called to repopulate the list when a theme is added thus it is made into a method.

        themeListView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String ThemeName = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(EditThemePage.this,ThemeName,Toast.LENGTH_SHORT).show();

                        String IDtoDelete = Mydb.getSpecificThemeID(ThemeName);
                        int isDeleted = Mydb.deleteTheme(IDtoDelete);

                        if (isDeleted > 0)
                            populateListView();
                            Toast.makeText(EditThemePage.this, "Theme Deleted", Toast.LENGTH_SHORT).show();
                        if (isDeleted < 0)
                            Toast.makeText(EditThemePage.this, "No Themes were Deleted", Toast.LENGTH_SHORT).show();

                    }
                }
        );

    }

    public void populateListView(){
        ListAdapter themeListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Mydb.getCURRENTThemeNames());
        themeListView.setAdapter(themeListAdapter);
    }

    public void addTheme (){

        commitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = Mydb.insertTheme(themeName.getText().toString(),themeDescription.getText().toString());
                        if (isInserted == true)
                            populateListView();
                            Toast.makeText(EditThemePage.this, "Data is inserted", Toast.LENGTH_SHORT).show();

                        if (isInserted == false)
                            Toast.makeText(EditThemePage.this, "Data is NOT inserted", Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }





}
