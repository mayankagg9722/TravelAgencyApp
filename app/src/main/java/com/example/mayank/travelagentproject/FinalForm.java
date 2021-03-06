package com.example.mayank.travelagentproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FinalForm extends AppCompatActivity {

    TextView travelname,travellocation,travelcontact,travelemail,userfrom,userto,username,
            useraddress,usercontact,useremail,pref,drop,date,time,cartype,carsize,price;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    static  Firebase firebase;
    String uid;
    Button booknow;
    String bookingid;
    String currentdate;
    ImageView shop,pamphlet;
    String priceentered;
    String imgoneurl,imgtwourl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_form);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Travel Agents");


        travelemail=(TextView)findViewById(R.id.travelemail);

        booknow=(Button)findViewById(R.id.booknow);
        travelname=(TextView)findViewById(R.id.travelagentname);
        travellocation=(TextView)findViewById(R.id.travellocation);
        travelcontact=(TextView)findViewById(R.id.travlecontact);
        username=(TextView)findViewById(R.id.username);
        useraddress=(TextView)findViewById(R.id.useraddress);
        useremail=(TextView)findViewById(R.id.useremail);
        usercontact=(TextView)findViewById(R.id.usercontact);
        userfrom=(TextView)findViewById(R.id.userfrom);
        userto=(TextView)findViewById(R.id.userto);
        pref=(TextView)findViewById(R.id.usertravelpref);
        drop=(TextView)findViewById(R.id.usercabtime);
        date=(TextView)findViewById(R.id.userdate);
        time=(TextView)findViewById(R.id.usertime);
        cartype=(TextView)findViewById(R.id.usercartype);
        carsize=(TextView)findViewById(R.id.usercarsize);

        shop=(ImageView)findViewById(R.id.ta_shop);
        pamphlet=(ImageView)findViewById(R.id.pamphlet);
        price=(TextView)findViewById(R.id.price);

        Bundle bundle=getIntent().getExtras();

        travelemail.setText(bundle.getString("agentemail"));
        travelname.setText(bundle.getString("agentname"));
        travellocation.setText(bundle.getString("agentaddress"));
        travelcontact.setText(bundle.getString("agentcontact"));
        username.setText(bundle.getString("name"));
        useraddress.setText(bundle.getString("address"));
        useremail.setText(bundle.getString("email"));
        usercontact.setText(bundle.getString("phone"));
        userfrom.setText(bundle.getString("from"));
        userto.setText(bundle.getString("to"));
        pref.setText(bundle.getString("travelpref"));
        drop.setText(bundle.getString("cabtime"));
        date.setText(bundle.getString("date"));
        time.setText(bundle.getString("time"));
        cartype.setText(bundle.getString("cartype"));
        carsize.setText(bundle.getString("carsize"));

        imgoneurl=bundle.getString("imgone");
        imgtwourl=bundle.getString("imgtwo");

        priceentered=bundle.getString("price");

        if (priceentered.equals("null")) {
            price.setText("Not Available!");
        }
        else{
         price.setText("INR "+priceentered);
        }

        StorageReference storageReference= FirebaseStorage.getInstance().getReferenceFromUrl(imgoneurl);
        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                shop.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(FinalForm.this,"Error..uploading images",Toast.LENGTH_SHORT).show();
            }
        });


        if(!(imgtwourl.equals("null"))){
            StorageReference storage= FirebaseStorage.getInstance().getReferenceFromUrl(imgtwourl);
            final long MEGABYTE = 1024 * 1024;
            storage.getBytes(MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    pamphlet.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(FinalForm.this,"Error..uploading images",Toast.LENGTH_SHORT).show();
                }
            });
        }


        Firebase.setAndroidContext(this);
        firebase=new Firebase("https://travelagentproject-40e3a.firebaseio.com/");

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.checknet(view.getContext());
                if(MainActivity.connected==true)
                showalertdialog();
            }
        });

    }

    public void showalertdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FinalForm.this);
        builder.setTitle("Booking");
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to book cab?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                final ProgressDialog pd = new ProgressDialog(FinalForm.this);
                pd.setMessage("Please Wait...");
                pd.setCancelable(false);
                pd.show();

                if(firebaseUser!=null){
                    uid=firebaseUser.getUid();

                    currentdate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    bookingid=uid.substring(2,8)+ String.valueOf(System.currentTimeMillis()/10000);

                    YourBookingPOJO yourBookingPOJO=new YourBookingPOJO(
                            travelemail.getText().toString(),
                            bookingid,
                            currentdate,
                            travelname.getText().toString(),
                            travellocation.getText().toString(),
                            travelcontact.getText().toString(),
                            username.getText().toString(),
                            useraddress.getText().toString(),
                            usercontact.getText().toString(),
                            useremail.getText().toString(),
                            userfrom.getText().toString(),
                            userto.getText().toString(),
                            date.getText().toString(),
                            time.getText().toString(),
                            pref.getText().toString(),
                            cartype.getText().toString(),
                            carsize.getText().toString(),
                            drop.getText().toString(),
                            uid.toString());

                    firebase.child("Booking Information").push().setValue(yourBookingPOJO, new Firebase.CompletionListener() {

                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if(firebaseError!=null){
                                Toast.makeText(FinalForm.this,"Unsuccessfull,Please Try Again..",Toast.LENGTH_SHORT).show();
                            }
                            else {

                                Toast.makeText(FinalForm.this, "Booking Successfull..", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(FinalForm.this,MainActivity.class);
                                startActivity(intent);

                                Toast toast=Toast.makeText(FinalForm.this, "Send Email To Confirm Booking", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                toast.show();

                                Intent intent1= new Intent(Intent.ACTION_SEND);
                                intent1.putExtra(Intent.EXTRA_EMAIL,new String[]{travelemail.getText().toString()});
                                intent1.putExtra(Intent.EXTRA_SUBJECT,"Booking By:"+username.getText());
                                intent1.putExtra(Intent.EXTRA_TEXT,
                                        "\nBooking id: "+bookingid+
                                        "\nBooking Date: "+currentdate+
                                        "\nUsername: "+username.getText().toString()+
                                        "\nUseraddress: "+useraddress.getText().toString()+
                                        "\nUsercontact: "+usercontact.getText().toString()+
                                        "\nUseremail: "+useremail.getText().toString()+
                                        "\nFrom: "+userfrom.getText().toString()+
                                        "\nTo: "+userto.getText().toString()+
                                        "\nTravel Date: "+date.getText().toString()+
                                        "\nTravel Time: "+time.getText().toString()+
                                        "\nShare/Ind: "+pref.getText().toString()+
                                        "\nCar Type: "+cartype.getText().toString()+
                                        "\nCar Size: "+carsize.getText().toString()+
                                        "\nWait/drop: "+drop.getText().toString()
                                        );
                                intent1.setType("message/rfc822");
                                startActivity(intent1);

                            }
                            pd.dismiss();
                        }
                    });
                }
                else{
                    pd.dismiss();
                    Toast.makeText(FinalForm.this,"Please Signin First..",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(FinalForm.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
             dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
