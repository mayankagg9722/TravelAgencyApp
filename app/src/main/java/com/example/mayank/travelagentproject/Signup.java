package com.example.mayank.travelagentproject;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signup extends android.support.v4.app.Fragment {

    EditText fname,lname,email,pass,repass;
    Button signup,google,facebook;

    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        fname= (EditText) container.findViewById(R.id.firstname);
        lname=(EditText)container.findViewById(R.id.lastname);
        email=(EditText)container.findViewById(R.id.email);
        pass=(EditText)container.findViewById(R.id.password);
        repass=(EditText)container.findViewById(R.id.repassword);
        signup=(Button)container.findViewById(R.id.register);
        google=(Button)container.findViewById(R.id.googlelogo);
        facebook=(Button)container.findViewById(R.id.facebooklogo);

        return inflater.inflate(R.layout.fragment_signup, container, false);
    }



}
