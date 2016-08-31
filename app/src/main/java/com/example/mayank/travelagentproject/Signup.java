package com.example.mayank.travelagentproject;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signup extends android.support.v4.app.Fragment {

    EditText fname,lname,email,pass,repass;
    Button signup,google,facebook;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_signup, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        fname= (EditText) view.findViewById(R.id.firstname);
        lname=(EditText)view.findViewById(R.id.lastname);
        email=(EditText)view.findViewById(R.id.email);
        pass=(EditText)view.findViewById(R.id.password);
        repass=(EditText)view.findViewById(R.id.repassword);
        signup=(Button)view.findViewById(R.id.register);
        google=(Button)view.findViewById(R.id.googlelogo);
        facebook=(Button)view.findViewById(R.id.facebooklogo);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(view.getContext());
                registeruser();
            }
        });
        return view;
    }
    public void registeruser(){
        if(TextUtils.isEmpty(fname.getText())){
            Toast.makeText(getContext(),"Please enter first name",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(lname.getText())){
            Toast.makeText(getContext(),"Please enter last name",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(email.getText())){
            Toast.makeText(getContext(),"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(pass.getText())){
            Toast.makeText(getContext(),"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(repass.getText())){
            Toast.makeText(getContext(),"Please enter correct password",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!(repass.getText().equals(pass.getText())))
        {
            Toast.makeText(getContext(),"Please enter correct password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Wait for registration..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                if(task.isSuccessful())
                    Toast.makeText(getContext(),"Successfully Registered..",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(),"Could not registered.Please try again..",Toast.LENGTH_SHORT).show();
            }
        });


    }


}
