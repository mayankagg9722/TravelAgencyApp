package com.example.mayank.travelagentproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Modedislpay extends AppCompatActivity {



    AutoCompleteTextView autoCompleteTextView,autoCompleteTextViewfrom;
    ArrayAdapter<String> cityadapter;
    TextView textView;
    ImageButton done;
    int flag=0;
    static String to;
    ListView listView;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modedislpay);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Enter Details");

        listView=(ListView)findViewById(R.id.listView);
        textView=(TextView)findViewById(R.id.selectmodetext);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextViewfrom = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewfrom);
        done=(ImageButton)findViewById(R.id.imageButton);

        final String[] cityname = getResources().getStringArray(R.array.cityarray);
        cityadapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,cityname);
        autoCompleteTextView.setAdapter(cityadapter);
        autoCompleteTextView.setDropDownBackgroundResource(R.color.droplist);
        autoCompleteTextViewfrom.setAdapter(cityadapter);
        autoCompleteTextViewfrom.setDropDownBackgroundResource(R.color.droplist);



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCompleteTextView.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter city name!", Toast.LENGTH_SHORT).show();
                }
                else {
                    flag=0;
                    for(String n:cityname) {
                        if (autoCompleteTextView.getText().toString().equals(n)) {
                            flag=1;
                        }
                    }
                    if(flag==1){
                        flag=0;
                        Intent intent = new Intent(getApplicationContext(), Modedislpay.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Sorry!..We are not currently available here.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Intent intent=getIntent();
        final String mode=intent.getStringExtra("mode");

        if(mode.equals("Railways")){
            textView.setText("Select Railway Station");
            if(to.equals("Hyderabad")){
                String[]name={"Station Name:\tHyderabad Deccan railway station\nStation code:\tHYB"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Bangalore")){
                String[]name={"Station Name:\tBangalore City railway station\nStation code:\tSBC"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Tirupati")){
                String[]name={"Station Name:\tTirupati railway station\n" +"Station code:\tTPTY"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Chennai")){
                String [] name=getResources().getStringArray(R.array.chennai_stations);
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }

            else if(to.equals("Coimbatore")){
                String[]name={"Station Name:\tCoimbatore Junction railway station\n" +"Station code:\tCBE"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Madurai")){
                String[]name={"Station Name:\tMadurai Junction railway station\n" +"Station code:\tMDU"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Pondicherry")){
                String[]name={"Station Name:\tPuducherry\n" +"Station code:\tPDY"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Vellore")){
                String[]name={"Station Name:\tKatpadi Junction\n" +"Station code:\tKPD"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }else if(to.equals("Kodaikanal")){
                String[]name={"Station Name:\tKodaikanal Road railway station\n" +"Station code:\tKQN"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }else if(to.equals("Ooty")){
                String[]name={"Station Name:\tUdhagamandalam\n" +"Station code:\tUAM"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Erode")){
                String[]name={"Station Name:\tErode Junction\n" +"Station code:\tED"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Rameshwaram")){
                String[]name={"Station Name:\tRameswaram\n" +"Station code:\tRMM"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
        }


        //####### OPENING LIST ACTIVITY#######

        else if(mode.equals("Airways")){
            textView.setText("Select Airport");
            if(to.equals("Hyderabad")){
                String[]name={"Airport Name:\tRajiv Gandhi International Airport\nAirport code:\tHYD"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Bangalore")){
                String[]name={"Airport Name:\tKempegowda International Airport\nAirport code:\tBLR"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Tirupati")){
                String[]name={"Airport Name:\tTTirupati airport\nAirport code:\tTIR"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Chennai")){
                String [] name={"Airport Name:\tChennai International Airport\nAirport code:\tMAA"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }

            else if(to.equals("Coimbatore")){
                String[]name={"Airport Name:\tCoimbatore International Airport\nAirport code:\tCJB"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Madurai")){
                String[]name={"Airport Name:\tMadurai Airport\nAirport code:\tIXM"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Pondicherry")){
                String[]name={"Airport Name:\tPuducherry Airport"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Vellore")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n" +
                        " Nearest Airports:\n1.Chennai Airport\n" +
                        "2.Banglore Airport"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }else if(to.equals("Kodaikanal")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n Nearest Airports:\n1.Madurai Airport\n2.Coimbatore airport"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }else if(to.equals("Ooty")){
                String[]name={"No Available Airports!\nPlease Select Another City.\nNearest Airports:\nCoimbatore Airport)"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Erode")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n" +
                        "Nearest Airports:\n1.Coimbatore Airport)"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
            else if(to.equals("Rameshwaram")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n" +
                        "Nearest Airports:\n1.Madurai Airport)"};
                arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.row_layout,R.id.rowlayouttext,name);
                listView.setAdapter(arrayAdapter);
            }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!(mode.equals("Airways")&&(to.equals("Rameshwaram")||to.equals("Erode")||to.equals("Ooty")||to.equals("Kodaikanal")||to.equals("Vellore"))))
                autoCompleteTextView.setText(adapterView.getItemAtPosition(i).toString()+","+to.toString());
                else
                    Toast.makeText(getApplicationContext(),"Please Select Specified City.",Toast.LENGTH_SHORT).show();
            }
        });

        //mode of transport
        // autoCompleteTextViewfrom.setText(mode);

        //city entered in dialog
        //autoCompleteTextView.setText(to);

    }
}
