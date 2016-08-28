package com.example.mayank.travelagentproject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FinalForm extends AppCompatActivity {

    TextView travelname,travellocation,travelcontact,travelemail,userfrom,userto,username,useraddress,usercontact,useremail,pref,drop,date,time,cartype,carsize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_form);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Travel Agents");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d96459")));


        travelname=(TextView)findViewById(R.id.travelagentname);
        travellocation=(TextView)findViewById(R.id.travellocation);
        travelcontact=(TextView)findViewById(R.id.travlecontact);
        username=(TextView)findViewById(R.id.username);
        useraddress=(TextView)findViewById(R.id.useraddress);
        useremail=(TextView)findViewById(R.id.useremail);
        usercontact=(TextView)findViewById(R.id.usercontact);
        userfrom=(TextView)findViewById(R.id.userfrom);
        userto=(TextView)findViewById(R.id.userto);
        pref=(TextView)findViewById(R.id.usertravelpref);
        drop=(TextView)findViewById(R.id.usercabtime);
        date=(TextView)findViewById(R.id.userdate);
        time=(TextView)findViewById(R.id.usertime);
        cartype=(TextView)findViewById(R.id.usercartype);
        carsize=(TextView)findViewById(R.id.usercarsize);


        Bundle bundle=getIntent().getExtras();

        travelname.setText(bundle.getString("agentname"));
        travellocation.setText(bundle.getString("agentaddress"));
        travelcontact.setText(bundle.getString("agentcontact"));
        username.setText(bundle.getString("name"));
        useraddress.setText(bundle.getString("address"));
        useremail.setText(bundle.getString("email"));
        usercontact.setText(bundle.getString("phone"));
        userfrom.setText(bundle.getString("from"));
        userto.setText(bundle.getString("to"));
        pref.setText(bundle.getString("travelpref"));
        drop.setText(bundle.getString("cabtime"));
        date.setText(bundle.getString("date"));
        time.setText(bundle.getString("time"));
        cartype.setText(bundle.getString("cartype"));
        carsize.setText(bundle.getString("carsize"));

    }
}