package com.example.mayank.travelagentproject;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;

public class Locale extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button button;
    Button getlocation,buttonlocation;
    static EditText datefield,timefield;
    ImageButton done;
    String carsize="mini";
    EditText uname, uaddress, from,name,email,address,phone;
    static int PLACE_PICKER_REQUEST = 1;
    static int PERMISSION_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    boolean enabled;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locale);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Enter Location");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d96459")));

        button = (Button) findViewById(R.id.pickbutton);
        uname = (EditText) findViewById(R.id.namepicked);
        uaddress = (EditText) findViewById(R.id.addresspicked);
        from = (EditText) findViewById(R.id.autoCompleteTextViewfrom);
        getlocation=(Button)findViewById(R.id.getlocation);

        done=(ImageButton)findViewById(R.id.imageButton);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        address=(EditText)findViewById(R.id.address);
        phone=(EditText)findViewById(R.id.phone);
        buttonlocation=(Button)findViewById(R.id.button);

        datefield=(EditText)findViewById(R.id.datetext);
        timefield=(EditText)findViewById(R.id.timetext);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        progressDialog=new ProgressDialog(Locale.this);

        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please Wait!!");
                progressDialog.setMax(2);
                progressDialog.setCancelable(true);
                progressDialog.show();
                LocationManager locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!enabled) {
                    showalertdialog();
                }
                else {
                    callPlaceDetectionApi();
                }
            }
        });

        buttonlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please Wait!!");
                progressDialog.setMax(2);
                progressDialog.setCancelable(true);
                progressDialog.show();
                LocationManager locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!enabled) {
                    showalertdialog();
                }
                else {
                    callPlaceDetectionApi();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(Locale.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void openTACardView(View view)   {
        Intent i = new Intent(this, TravelAgents.class);
        startActivity(i);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                uname.setText(place.getName());
                uaddress.setText(place.getAddress());
            }
        }

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Places API connection failed with error code:" + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }


    public void callPlaceDetectionApi() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Locale.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {
                for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                    from.setText(placeLikelihood.getPlace().getAddress().toString());
                    address.setText(placeLikelihood.getPlace().getAddress().toString());
                    progressDialog.dismiss();
                }
                placeLikelihoods.release();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPlaceDetectionApi();
            }
        }
    }
    public void showalertdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Locale.this);
        builder.setTitle("Enable GPS");
        builder.setCancelable(false);
        builder.setMessage("Please enable GPS");
        builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                progressDialog.dismiss();
            }
        });
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                progressDialog.dismiss();
            }
        });
        builder.create().show();
    }


    public void accar(View view) {
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.ac:
                if (checked)
                    Toast.makeText(this, "ac", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nonac:
                if (checked)
                    Toast.makeText(this, "nonac", Toast.LENGTH_SHORT).show();
                break;
        }

    }
    public void carsize(View view) {
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.large:
                if (checked) {
                    carsize="large";
                    Toast.makeText(this, "large", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mini:
                if (checked){
                    carsize="mini";
                    Toast.makeText(this, "mini", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void traveltype(View view)
    {
        Boolean checked=((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.share:
                if(checked){
                    sharealert();
                }
                break;
            case R.id.individual:
                if(checked)
                    Toast.makeText(this,"indiv",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void cartime(View view) {
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.drop:
                if (checked)
                    Toast.makeText(this, "drop", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wait:
                if (checked) {
                    alert();
                    break;
                }
        }
    }

    public void dateenter(View view){
        DatePicker.flag=1;
        DatePicker datePicker=new DatePicker();
        datePicker.show(getSupportFragmentManager(),"d");
        DatePicker.flag=0;
    }
    public void timeset(View view){
        TimePicker.flag=1;
        TimePicker timePicker=new TimePicker();
        timePicker.show(getSupportFragmentManager(),"t");
        TimePicker.flag=0;
    }

    public void alert(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Wait-Hours");
        alert.setCancelable(false);
        alert.setMessage("Enter wait Hours for cab:");
        final EditText editText=new EditText(this);
        editText.setHint("Enter no. of hours");
        alert.setView(editText);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String waithours=editText.getText().toString();

                if(Integer.parseInt(waithours)>24){
                    Toast.makeText(getApplicationContext(),"Wait Hours should be less than a day..",Toast.LENGTH_SHORT).show();
                    alert();
                }
            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alert();
            }
        });
        alert.create().show();
    }
    public void sharealert(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Cab Share");
        alert.setCancelable(false);
        alert.setMessage("Enter no. of person for cab share:");
        final EditText editText=new EditText(this);
        editText.setHint("Enter no. of person");
        alert.setView(editText);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String waithours=editText.getText().toString();
                if(carsize.equals("mini")&&Integer.parseInt(waithours)>4){
                    Toast.makeText(getApplicationContext(),"People should be less than 4 for mini cab..",Toast.LENGTH_SHORT).show();
                    sharealert();
                }
                if(carsize.equals("large")&&Integer.parseInt(waithours)>7){
                    Toast.makeText(getApplicationContext(),"People should be less than 7 for large cab..",Toast.LENGTH_SHORT).show();
                    sharealert();
                }
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sharealert();
            }
        });
        alert.create().show();
    }

}