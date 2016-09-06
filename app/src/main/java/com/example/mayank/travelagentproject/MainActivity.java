package com.example.mayank.travelagentproject;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookBroadcastReceiver;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModeTransport> list=new ArrayList<ModeTransport>();
    TextView navname;
    TextView navemail;
    Toolbar toolbar;
    int flag=0;
    AutoCompleteTextView autoCompleteTextView;
    int [] image={R.drawable.newrailway,R.drawable.newairway,R.drawable.newlocal};
    String [] name={"Railways","Airways","Locale"};


    CoordinatorLayout coordinatorLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Firebase.goOnline();

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cordinatorlayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(user!=null){
                toolbar.setTitle(user.getDisplayName().toString());
        }
        else {
            toolbar.setTitle("Guest");
        }

        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        navname=(TextView)header.findViewById(R.id.accountname);
        navemail=(TextView)header.findViewById(R.id.accountemail);


        Calendar c=Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(MainActivity.this,"Google play services error..",Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        if(user!=null){
            navname.setText(user.getDisplayName().toString());
            navemail.setText(user.getEmail().toString());
            if(timeOfDay >= 0 && timeOfDay < 12){
                    Snackbar.make(coordinatorLayout, "Good Morning,"+user.getDisplayName().toString(), Snackbar.LENGTH_LONG).show();
                }else if(timeOfDay >= 12 && timeOfDay < 16){
                    Snackbar.make(coordinatorLayout, "Good Afternoon,"+user.getDisplayName().toString(), Snackbar.LENGTH_LONG).show();
                }
                else if(timeOfDay >= 16 && timeOfDay < 21){
                    Snackbar.make(coordinatorLayout, "Good Evening,"+user.getDisplayName().toString(), Snackbar.LENGTH_LONG).show();
                }else if(timeOfDay >= 21 && timeOfDay < 24){
                    Snackbar.make(coordinatorLayout, "Welcome,"+user.getDisplayName().toString(), Snackbar.LENGTH_LONG).show();
                }
        }
        else{
            if(timeOfDay >= 0 && timeOfDay < 12){
                Snackbar.make(coordinatorLayout, "Good Morning,You Logged In As Guest.", Snackbar.LENGTH_LONG).show();
            }else if(timeOfDay >= 12 && timeOfDay < 16){
                Snackbar.make(coordinatorLayout, "Good Afternoon,You Logged In As Guest.", Snackbar.LENGTH_LONG).show();
            }
            else if(timeOfDay >= 16 && timeOfDay < 21){
                Snackbar.make(coordinatorLayout, "Good Evening,You Logged In As Guest.", Snackbar.LENGTH_LONG).show();
            }else if(timeOfDay >= 21 && timeOfDay < 24){
                Snackbar.make(coordinatorLayout, "Welcome,You Logged In As Guest.", Snackbar.LENGTH_LONG).show();
            }
        }

        int count=0;

        for(String n:name){
            ModeTransport modeTransport=new ModeTransport(image[count],n);
            list.add(modeTransport);
            count++;
        }
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new ModeAdapter(list,this);
        recyclerView.setAdapter(adapter);

    }

    //******
    // onCreate ends..
    //******
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            if(item.isChecked())
                item.setChecked(false);
            else
            item.setChecked(true);
        }
        if(id==R.id.location) {
            enterlocation();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.registeruser) {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.registertravelagent) {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_logout){
            if(user!= null){
                navname.setText("FirstName LastName");
                navemail.setText("android.studio@android.com");
                toolbar.setTitle("Guest");
                firebaseAuth.signOut();


                 Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });

                LoginManager.getInstance().logOut();


                Toast.makeText(MainActivity.this, "You are logged out.", Toast.LENGTH_SHORT).show();

            }
            else
                Toast.makeText(MainActivity.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.linking) {

        } else if (id == R.id.booking) {

        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

     public void  enterlocation(){

        AlertDialog.Builder alertBuilder =new AlertDialog.Builder(this);
        alertBuilder.setTitle("Enter City name for which you want to book a cab : ");
        alertBuilder.setCancelable(false);
        autoCompleteTextView=new AutoCompleteTextView(this);
        autoCompleteTextView.setSingleLine();
        autoCompleteTextView.setMaxLines(1);
        autoCompleteTextView.setDropDownBackgroundResource(R.color.droplist);
        autoCompleteTextView.setHint("Enter city..");
        autoCompleteTextView.setHintTextColor(Color.parseColor("#000000"));
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.cityarray)));

        alertBuilder.setView(autoCompleteTextView);

        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (autoCompleteTextView.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter city name!", Toast.LENGTH_SHORT).show();
                    enterlocation();
                }
                else {
                    flag=0;
                    for(String n:getResources().getStringArray(R.array.cityarray)) {
                        if (autoCompleteTextView.getText().toString().equals(n)) {
                            flag=1;
                        }
                    }
                    if(flag==1){
                        flag=0;
                        ModeAdapter.start=true;
                        Modedislpay.to=autoCompleteTextView.getText().toString();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Sorry!..We are not currently in this City",
                                Toast.LENGTH_SHORT).show();
                        enterlocation();
                    }
                }
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
            }
        });
        alertBuilder.create().show();
    }
}
