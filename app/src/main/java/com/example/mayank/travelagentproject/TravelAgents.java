package com.example.mayank.travelagentproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class TravelAgents extends AppCompatActivity {

    TA_POJO list;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TA_POJO> details=new ArrayList<TA_POJO>();
    static Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_agents);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Travel Agents");

        FinalForm.firebase=new Firebase("https://travelagentproject-40e3a.firebaseio.com/");

        bundle=getIntent().getExtras();

        MainActivity.checknet(this);

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        if(MainActivity.connected==true) {
            pd.show();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.travelAgent_recyclerView);
        //details = TravelAgents_POJO.getDetails();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new TravelAgentsAdapter(details, this);
        mRecyclerView.setAdapter(mAdapter);


        FinalForm.firebase.child("TravelAgents").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot!=null&&dataSnapshot.getValue()!=null) {
                    Log.v("register",dataSnapshot.getValue().toString());
                    list=dataSnapshot.getValue(TA_POJO.class);
                    details.add(list);
                    mAdapter.notifyDataSetChanged();
                }
                pd.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
}
