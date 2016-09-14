package com.example.mayank.travelagentproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Criteria;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import android.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signup extends android.support.v4.app.Fragment {

    private GoogleApiClient mGoogleApiClient;
    EditText fname,lname,email,pass,repass;
    Button signup,google;
    int flag=0;
    ProgressDialog progressDialog;
    String password,repassword;
    ImageView eye;

    FirebaseAuth firebaseAuth;

    public static final int RC_SIGN_IN=9001;
    public static final String TAG="tag";


    CallbackManager callbackManager;
    LoginButton facebook;

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        view=inflater.inflate(R.layout.fragment_signup, container, false);


        Firebase.goOnline();
        Firebase.setAndroidContext(getContext());

        firebaseAuth=FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient=new GoogleApiClient.Builder(getContext()).enableAutoManage(Signup.this.getActivity(),1, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(getContext(),"Google play services error..",Toast.LENGTH_SHORT).show();
            }}).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();


        eye=(ImageView)view.findViewById(R.id.eye);
        fname= (EditText) view.findViewById(R.id.firstname);
        lname=(EditText)view.findViewById(R.id.lastname);
        email=(EditText)view.findViewById(R.id.email);
        pass=(EditText)view.findViewById(R.id.password);
        repass=(EditText)view.findViewById(R.id.repassword);
        signup=(Button)view.findViewById(R.id.register);
        google=(Button)view.findViewById(R.id.googlelogo);

        facebook=(LoginButton)view.findViewById(R.id.facebooklogo);


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


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(view.getContext());
                registeruser();
            }
        });


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(view.getContext());
                signIn();
            }
        });


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext()," Click LogIn Tab",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void registeruser(){
        flag=0;
        password=pass.getText().toString();
        repassword=repass.getText().toString();
        checkfornullfields();
        if( (flag==0)) {
            progressDialog.setMessage("Wait for registration..");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(fname.getText().toString()+" "+lname.getText().toString()).build();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User profile updated.");
                                                    startActivity(new Intent(getContext(),MainActivity.class));
                                                }
                                            }
                                        });
                            }
                            else{
                                Toast.makeText(getContext(), "Could not registered.Please try again..", Toast.LENGTH_SHORT).show();
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

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                }
            }
        }
        private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
            //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
            progressDialog.setMessage("Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (!task.isSuccessful()) {
                                Toast.makeText(getContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(getContext(),MainActivity.class));
                            }

                        }
                    });
        }


    @Override
    public void onStart() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
        super.onStart();

    }

    @Override
    public void onDestroy() {
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
        super.onDestroy();

    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

}


