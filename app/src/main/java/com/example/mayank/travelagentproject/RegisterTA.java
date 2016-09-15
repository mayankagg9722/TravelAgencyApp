package com.example.mayank.travelagentproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterTA extends AppCompatActivity {

    EditText name,location,phone,email,price;
    Button register;
    Firebase firebase;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ta);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.show();

        name=(EditText) findViewById(R.id.name);
        location=(EditText) findViewById(R.id.location);
        email=(EditText) findViewById(R.id.email);
        phone=(EditText) findViewById(R.id.mobile);
        register=(Button)findViewById(R.id.register);
        price=(EditText)findViewById(R.id.price);

        Firebase.setAndroidContext(this);
        FinalForm.firebase=new Firebase("https://travelagentproject-40e3a.firebaseio.com/");


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                checknullfields();
                if (flag == 0) {
                    MainActivity.checknet(view.getContext());
                    if (MainActivity.connected == true) {
                        showalertbox();
                    }
                }
                else {
                    Toast.makeText(RegisterTA.this, "Fill Completely..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void showalertbox(){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Register");
        alert.setMessage("Are you sure you want to register your travel agency?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final ProgressDialog pd=new ProgressDialog(RegisterTA.this);
                pd.setMessage("Wait..");
                pd.show();

                TA_POJO ta=new TA_POJO(name.getText().toString(),
                        location.getText().toString(),phone.getText().toString(),email.getText().toString(),price.getText().toString());

                FinalForm.firebase.child("TravelAgents").push().setValue(ta, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if(firebaseError==null){
                            Toast.makeText(RegisterTA.this, "Registration Successfull..", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(RegisterTA.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(RegisterTA.this, "Try Again..", Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();
                    }
                });
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.create().show();
    }

    public void checknullfields(){
        if(TextUtils.isEmpty(name.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(location.toString().toString()))
            flag=1;
        else if(TextUtils.isEmpty(phone.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(email.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(price.getText().toString()))
            price.setText("null");
    }
}
