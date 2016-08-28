package com.example.mayank.travelagentproject;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

public class Modedislpay extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener {


    EditText autoCompleteTextViewfrom,name,email,address,phone;
    static EditText datefield,timefield;
    ImageButton done;
    static String to;
    String carsize="mini";
    ArrayAdapter arrayAdapter;
    Spinner spinner;
    static int PERMISSION_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    boolean enabled;
    ProgressDialog progressDialog;
    Button getlocation,button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modedisp);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Enter Details");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d96459")));

        getlocation=(Button)findViewById(R.id.getlocation);
        autoCompleteTextViewfrom = (EditText) findViewById(R.id.autoCompleteTextViewfrom);
        done=(ImageButton)findViewById(R.id.imageButton);
        spinner=(Spinner)findViewById(R.id.spinner);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        address=(EditText)findViewById(R.id.address);
        phone=(EditText)findViewById(R.id.phone);
        button=(Button)findViewById(R.id.button);

        datefield=(EditText)findViewById(R.id.datetext);
        timefield=(EditText)findViewById(R.id.timetext);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        progressDialog=new ProgressDialog(Modedislpay.this);

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

        button.setOnClickListener(new View.OnClickListener() {
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

        Intent intent=getIntent();
        final String mode=intent.getStringExtra("mode");

        if(mode.equals("Railways")){
            if(to.equals("Hyderabad")){
                String[]name={"Station Name:\tHyderabad Deccan railway station\nStation code:\tHYB"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Bangalore")){
                String[]name={"Station Name:\tBangalore City railway station\nStation code:\tSBC"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Tirupati")){
                String[]name={"Station Name:\tTirupati railway station\n" +"Station code:\tTPTY"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Chennai")){
                String [] name=getResources().getStringArray(R.array.chennai_stations);
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }

            else if(to.equals("Coimbatore")){
                String[]name={"Station Name:\tCoimbatore Junction railway station\n" +"Station code:\tCBE"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Madurai")){
                String[]name={"Station Name:\tMadurai Junction railway station\n" +"Station code:\tMDU"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Pondicherry")){
                String[]name={"Station Name:\tPuducherry\n" +"Station code:\tPDY"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Vellore")){
                String[]name={"Station Name:\tKatpadi Junction\n" +"Station code:\tKPD"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }else if(to.equals("Kodaikanal")){
                String[]name={"Station Name:\tKodaikanal Road railway station\n" +"Station code:\tKQN"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
               // listView.setAdapter(arrayAdapter);
            }else if(to.equals("Ooty")){
                String[]name={"Station Name:\tUdhagamandalam\n" +"Station code:\tUAM"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
             //   listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Erode")){
                String[]name={"Station Name:\tErode Junction\n" +"Station code:\tED"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
             //   listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Rameshwaram")){
                String[]name={"Station Name:\tRameswaram\n" +"Station code:\tRMM"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
        }


        //######################################## OPENING LIST ACTIVITY#################################################

        else if(mode.equals("Airways")){
            if(to.equals("Hyderabad")){
                String[]name={"Airport Name:\tRajiv Gandhi International Airport\nAirport code:\tHYD"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Bangalore")){
                String[]name={"Airport Name:\tKempegowda International Airport\nAirport code:\tBLR"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Tirupati")){
                String[]name={"Airport Name:\tTTirupati airport\nAirport code:\tTIR"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Chennai")){
                String [] name={"Airport Name:\tChennai International Airport\nAirport code:\tMAA"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }

            else if(to.equals("Coimbatore")){
                String[]name={"Airport Name:\tCoimbatore International Airport\nAirport code:\tCJB"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Madurai")){
                String[]name={"Airport Name:\tMadurai Airport\nAirport code:\tIXM"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Pondicherry")){
                String[]name={"Airport Name:\tPuducherry Airport"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Vellore")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n" +
                        " Nearest Airports:\n1.Chennai Airport\n" +
                        "2.Banglore Airport"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }else if(to.equals("Kodaikanal")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n Nearest Airports:\n1.Madurai Airport\n2.Coimbatore airport"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
               // listView.setAdapter(arrayAdapter);
            }else if(to.equals("Ooty")){
                String[]name={"No Available Airports!\nPlease Select Another City.\nNearest Airports:\nCoimbatore Airport)"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
             //   listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Erode")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n" +
                        "Nearest Airports:\n1.Coimbatore Airport)"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Rameshwaram")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n" +
                        "Nearest Airports:\n1.Madurai Airport)"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
                //listView.setAdapter(arrayAdapter);
            }
        }

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if((mode.equals("Airways")&&(to.equals("Rameshwaram")||to.equals("Erode")||to.equals("Ooty")||to.equals("Kodaikanal")||to.equals("Vellore"))))
                    Toast.makeText(getApplicationContext(),"Please Select Specified City and then try...",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext()," Please Select Destination",Toast.LENGTH_SHORT).show();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), TravelAgents.class);
                startActivity(i);
            }
        });

    }

    public void openTACardView(View view)   {
        Intent i = new Intent(this, TravelAgents.class);
        startActivity(i);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Places API connection failed with error code:" + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    public void callPlaceDetectionApi() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Modedislpay.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
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
                    address.setText(placeLikelihood.getPlace().getAddress().toString());
                    autoCompleteTextViewfrom.setText(placeLikelihood.getPlace().getAddress().toString());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Modedislpay.this);
        builder.setTitle("Enable GPS");
        builder.setMessage("Please enable GPS");
        builder.setCancelable(false);
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
        DatePicker datePicker=new DatePicker();
        datePicker.show(getSupportFragmentManager(),"date");
    }
    public void timeset(View view){
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
        alert.setView(editText);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    String waithours=editText.getText().toString();

                if(Integer.parseInt(waithours)>24&&Integer.parseInt(waithours)==0){
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
                String share=editText.getText().toString();
                if(carsize.equals("mini")&&Integer.parseInt(share)>4&&Integer.parseInt(share)==0){
                    Toast.makeText(getApplicationContext(),"People should be less than 4 for mini cab..",Toast.LENGTH_SHORT).show();
                    sharealert();
                }
                if(carsize.equals("large")&&Integer.parseInt(share)>7){
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

