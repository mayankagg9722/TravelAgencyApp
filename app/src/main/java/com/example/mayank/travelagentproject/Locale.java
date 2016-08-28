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
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

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

public class Locale extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button button;
    Button getlocation,buttonlocation;
    static EditText datefield,timefield;
    String destination,cabtype="AC",cabtravelpref="Individual",cabsize="Mini",cabtime="Drop=Down";
    int flag=0;
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

        destination=uname.getText().toString()+","+uaddress.getText().toString();

    }

    public void openTACardView(View view)   {
        checknullfields()
        ;
        if(flag==1) {
            Toast.makeText(this, "Fill Form Completely", Toast.LENGTH_SHORT).show();
            flag=0;
        }
        else {

            Intent i = new Intent(this, TravelAgents.class);

            Bundle bundle=new Bundle();
            bundle.putString("name",name.getText().toString());
            bundle.putString("address",address.getText().toString());
            bundle.putString("email",email.getText().toString());
            bundle.putString("phone",phone.getText().toString());
            bundle.putString("from",from.getText().toString());
            bundle.putString("to",destination);
            bundle.putString("date",datefield.getText().toString());
            bundle.putString("time",timefield.getText().toString());
            bundle.putString("cartype",cabtype);
            bundle.putString("travelpref",cabtravelpref);
            bundle.putString("carsize",cabsize);
            bundle.putString("cabtime",cabtime);
            i.putExtras(bundle);
            startActivity(i);

            name.setText("");
            address.setText("");
            email.setText("");
            phone.setText("");
            uname.setText("");
            uaddress.setText("");
            from.setText("");
            datefield.setText("");
            timefield.setText("");
            flag = 0;
        }
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
                    cabtype="AC";
                break;
            case R.id.nonac:
                if (checked)
                    cabtype="NON-AC";
                break;
        }

    }
    public void carsize(View view) {
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.large:
                if (checked) {
                    carsize="large";
                    carsize="large";
                }
                break;
            case R.id.mini:
                if (checked){
                    carsize="mini";
                    cabsize="mini";
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
                    cabtravelpref="Individual";
                break;
        }
    }
    public void cartime(View view) {
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.drop:
                if (checked)
                    cabtime="Drop-Down";
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
        datePicker.show(getSupportFragmentManager(),"date");
    }
    public void timeset(View view){
        TimePicker.flag=1;
        TimePicker timePicker=new TimePicker();
        timePicker.show(getSupportFragmentManager(),"time");

    }

    public void alert(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        alert.setTitle("Wait-Hours");
        alert.setMessage("Enter wait Hours for cab:");
        final EditText editText=new EditText(this);
        editText.setHint("Enter no. of hours");
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(editText);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String waithours=editText.getText().toString();

                if(Integer.parseInt(waithours)>24||Integer.parseInt(waithours)==0){
                    Toast.makeText(getApplicationContext(),"Wait Hours should be less than a day..",Toast.LENGTH_SHORT).show();
                    alert();
                }
                else
                    cabtime="Wait For "+editText.getText().toString()+" hours";
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
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("Enter no. of person");
        alert.setView(editText);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String share=editText.getText().toString();
                if(carsize.equals("mini")&&(Integer.parseInt(share)>4||Integer.parseInt(share)==0)){
                    Toast.makeText(getApplicationContext(),"People should be less than 4 or at least 1 for mini cab..",Toast.LENGTH_SHORT).show();
                    sharealert();
                }
                else if(carsize.equals("large")&&(Integer.parseInt(share)>7||Integer.parseInt(share)==0)){
                    Toast.makeText(getApplicationContext(),"People should be less than 7 for large cab..",Toast.LENGTH_SHORT).show();
                    sharealert();
                }
                else
                    cabtravelpref="Share with "+editText.getText().toString()+" person";

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

    public void checknullfields(){
        if(TextUtils.isEmpty(from.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(destination.toString().toString()))
            flag=1;
        else if(TextUtils.isEmpty(name.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(address.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(email.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(phone.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(datefield.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(timefield.getText().toString()))
            flag=1;
    }

}