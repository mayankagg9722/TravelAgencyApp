package com.example.mayank.travelagentproject;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LinkedCities extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_cities);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.show();


        listView=(ListView)findViewById(R.id.citylistview);
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.citylistlayout,R.id.citytext,getResources().getStringArray(R.array.cityarray));
        listView.setAdapter(arrayAdapter);
    }
}
