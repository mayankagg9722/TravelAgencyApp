package com.example.mayank.travelagentproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signup extends android.support.v4.app.Fragment {

    EditText fname,lname,email,pass,repass;
    Button signup,google,facebook;
    int flag=0;
    String signupas="user";
    ProgressDialog progressDialog;
    RadioGroup radiogroup;
    String password,repassword;
    ImageView eye;

    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_signup, container, false);

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(getContext(),"You are already signed in.",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), MainActivity.class));
        }

        eye=(ImageView)view.findViewById(R.id.eye);
        fname= (EditText) view.findViewById(R.id.firstname);
        lname=(EditText)view.findViewById(R.id.lastname);
        email=(EditText)view.findViewById(R.id.email);
        pass=(EditText)view.findViewById(R.id.password);
        repass=(EditText)view.findViewById(R.id.repassword);
        signup=(Button)view.findViewById(R.id.register);
        google=(Button)view.findViewById(R.id.googlelogo);
        facebook=(Button)view.findViewById(R.id.facebooklogo);
        radiogroup=(RadioGroup)view.findViewById(R.id.radiogroup);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.user:
                        signupas = "user";
                        break;
                    case R.id.traveagent:
                        signupas = "travelagent";
                        break;
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(view.getContext());
                registeruser();
            }
        });

        eye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch ( motionEvent.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        pass.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
        return view;
    }

    public void registeruser(){
        flag=0;
        password=pass.getText().toString();
        repassword=repass.getText().toString();
        checkfornullfields();
        if( (flag==0)){
            progressDialog.setMessage("Wait for registration..");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Successfully Registered..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(),MainActivity.class));
                    }

                    else
                        Toast.makeText(getContext(), "Could not registered.Please try again..", Toast.LENGTH_SHORT).show();
                }
            });

           final UserProfileChangeRequest userprofle=new UserProfileChangeRequest.Builder()
                    .setDisplayName(fname.getText().toString()+" "+lname.getText().toString()).build();

            firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser()!=null){
                       FirebaseUser user=firebaseAuth.getCurrentUser();
                        user.updateProfile(userprofle);
                    }
                }
            });


        }
    }

    public void checkfornullfields(){
        if(TextUtils.isEmpty(fname.getText())){
            Toast.makeText(getContext(),"Please enter first name",Toast.LENGTH_SHORT).show();
            flag=1;
        }
        else if(TextUtils.isEmpty(lname.getText())){
            Toast.makeText(getContext(),"Please enter last name",Toast.LENGTH_SHORT).show();
            flag=1;
        }
        else if(TextUtils.isEmpty(email.getText())){
            Toast.makeText(getContext(),"Please enter email",Toast.LENGTH_SHORT).show();
            flag=1;
        }
        else if(TextUtils.isEmpty(pass.getText())){
            Toast.makeText(getContext(),"Please enter password",Toast.LENGTH_SHORT).show();
            flag=1;
        }
        else if(TextUtils.isEmpty(repass.getText())){
            Toast.makeText(getContext(),"Please enter Re-password ",Toast.LENGTH_SHORT).show();
            flag=1;
        }
        else if(!(password.contentEquals(repassword))){
            Toast.makeText(getContext(),"Please Not Matched ",Toast.LENGTH_SHORT).show();
            flag=1;
        }
    }
}

