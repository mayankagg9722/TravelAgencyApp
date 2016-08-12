package com.example.mayank.travelagentproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Modedislpay extends AppCompatActivity {



    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> cityadapter;
    ImageButton done;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modedislpay);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Enter Details");

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        done=(ImageButton)findViewById(R.id.imageButton);

        final String[] cityname = getResources().getStringArray(R.array.cityarray);
        cityadapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,cityname);
        autoCompleteTextView.setAdapter(cityadapter);
        autoCompleteTextView.setDropDownBackgroundResource(R.color.droplist);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCompleteTextView.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter city name!", Toast.LENGTH_LONG).show();
                }
                else {
                    flag=0;
                    for(String n:cityname) {
                        if (autoCompleteTextView.getText().toString().equals(n)) {
                            flag=1;
                        }
                    }
                    if(flag==1){
                        flag=0;
                        Intent intent = new Intent(getApplicationContext(), Modedislpay.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Sorry!..We are not currently available here.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }
}
