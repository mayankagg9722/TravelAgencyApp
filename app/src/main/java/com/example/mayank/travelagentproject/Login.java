package com.example.mayank.travelagentproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.cast.CastRemoteDisplayLocalService;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */

public class Login extends android.support.v4.app.Fragment {

    EditText email,pass;
    Button login,google;
    int flag=0;
    ProgressDialog progressDialog;
    ImageView eye;
    TextView forget;
    FirebaseAuth firebaseAuth;
    private GoogleApiClient mGoogleApiClient;
    public static final int RC_SIGN_IN=9001;
    public static final String TAG="tag";
    CallbackManager callbackManager;
    LoginButton facebook;
    static int t=0;

    private FirebaseAuth.AuthStateListener mAuthListener;

  @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getContext());
       // AppEventsLogger.activateApp(getContext());
        callbackManager=CallbackManager.Factory.create();
      super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_login, container, false);

        MainActivity.checknet(getContext());

        Firebase.goOnline();
        Firebase.setAndroidContext(getContext());


        firebaseAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient=new GoogleApiClient.Builder(getContext()).enableAutoManage(Login.this.getActivity(),0, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(getContext(),"Google play services error..",Toast.LENGTH_SHORT).show();
            }}).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();



        eye=(ImageView)view.findViewById(R.id.eye);
        email=(EditText)view.findViewById(R.id.email);
        pass=(EditText)view.findViewById(R.id.password);
        login=(Button)view.findViewById(R.id.login);
        google=(Button)view.findViewById(R.id.googlelogo);
        forget=(TextView)view.findViewById(R.id.forget);

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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(view.getContext());
                loginuser();
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if(!(TextUtils.isEmpty(email.getText()))) {

                    String emailAddress = email.getText().toString();

                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Check your email to reset your password..", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(getContext(), "Incorrect email !", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    Toast.makeText(getContext(), "Fill your email first..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(view.getContext());
                signIn();
            }
        });


        facebook.setFragment(this);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(view.getContext());
                facebook.setReadPermissions("email", "public_profile");
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        progressDialog.setMessage("Wait..");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });
        return view;
    }
    public void loginuser(){
        flag=0;

        checkfornullfields();

        if( (flag==0)){
            progressDialog.setMessage("Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        startActivity(new Intent(getContext(),MainActivity.class));
                    }
                    else
                        Toast.makeText(getContext(), "Could not login.Please try again..", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void checkfornullfields(){
        if(TextUtils.isEmpty(email.getText())){
            Toast.makeText(getContext(),"Please enter email",Toast.LENGTH_SHORT).show();
            flag=1;
        }
        else if(TextUtils.isEmpty(pass.getText())){
            Toast.makeText(getContext(),"Please enter password",Toast.LENGTH_SHORT).show();
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        super.onStart();

        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
        FacebookSdk.clearLoggingBehaviors();
    }

    @Override
    public void onStop() {

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        FacebookSdk.clearLoggingBehaviors();
        super.onStop();
    }

    public void handleFacebookAccessToken(AccessToken token) {
       // Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        progressDialog.dismiss();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            t=1;
                            startActivity(new Intent(getContext(),MainActivity.class));
                        }
                    }
                });
    }

}
