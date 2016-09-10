package com.example.mayank.travelagentproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TravelConfirmation extends AppCompatActivity {

    EditText email,carname,carno,drivername,driverno,traveldate,traveltime,status;
    Button button;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_confirmation);
        email=(EditText)findViewById(R.id.custemail);
        carname=(EditText)findViewById(R.id.carname);
        carno=(EditText)findViewById(R.id.carnumber);
        drivername=(EditText)findViewById(R.id.drivername);
        driverno=(EditText)findViewById(R.id.drivernumber);
        traveldate=(EditText)findViewById(R.id.traveldate);
        traveltime=(EditText)findViewById(R.id.traveltime);
        status=(EditText)findViewById(R.id.status);
        button=(Button)findViewById(R.id.confirmsend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=0;
                checknullfields();
                if(flag==0){
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL,new String[]{email.getText().toString()});
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Booking Status:"+status.getText().toString());
                    intent.putExtra(Intent.EXTRA_TEXT,"Details:"+
                    "\nCar Name:"+carname.getText().toString()+
                                    "\nCar Number:"+carno.getText().toString()+
                                    "\nDriver Name:"+drivername.getText().toString()+
                                    "\nDriver Number:"+driverno.getText().toString()+
                                    "\nTravel Date:"+traveldate.getText().toString()+
                                    "\nTravel Time:"+traveltime.getText().toString()
                    );
                    intent.setType("message/rfc822");
                    Toast.makeText(TravelConfirmation.this, "Sending mail to customer..", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(TravelConfirmation.this, "Fill Form Completely", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void checknullfields(){
        if(TextUtils.isEmpty(email.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(carno.toString().toString()))
            flag=1;
        else if(TextUtils.isEmpty(carname.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(driverno.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(driverno.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(traveltime.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(traveldate.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(status.getText().toString()))
            flag=1;
    }
}
