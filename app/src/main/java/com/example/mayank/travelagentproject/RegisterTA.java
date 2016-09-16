package com.example.mayank.travelagentproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.ads.internal.overlay.zzo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class RegisterTA extends AppCompatActivity {

    EditText name,location,phone,email,price;
    Button register;
    Firebase firebase;
    ImageView imgone,imgtwo,cancelone,canceltwo;
    int flag=0;

    private int IMGONE=1;
    private int IMGTWO=2;

    String imgonedwnld;
    String imgtwodwnld="null";
    Uri imgurione=null,imguritwo=null;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ta);

        imgtwodwnld="null";
        imgurione=null;
        imguritwo=null;

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.show();

        name=(EditText) findViewById(R.id.name);
        location=(EditText) findViewById(R.id.location);
        email=(EditText) findViewById(R.id.email);
        phone=(EditText) findViewById(R.id.mobile);
        register=(Button)findViewById(R.id.register);
        price=(EditText)findViewById(R.id.price);


        imgone=(ImageView)findViewById(R.id.imgone);
        imgtwo=(ImageView)findViewById(R.id.imgtwo);
        cancelone=(ImageView)findViewById(R.id.cancelone);
        canceltwo=(ImageView)findViewById(R.id.canceltwo);

        cancelone.setClickable(false);
        canceltwo.setClickable(false);



        Firebase.setAndroidContext(this);
        FinalForm.firebase=new Firebase("https://travelagentproject-40e3a.firebaseio.com/");


        imgone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Picture"),IMGONE);
            }
        });

        imgtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Picture"),IMGTWO);
            }
        });

        cancelone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(view.getContext());
                progressDialog.setMessage("Wait..");
                progressDialog.show();
                storageReference=FirebaseStorage.getInstance().getReferenceFromUrl(imgonedwnld);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterTA.this,"Successfully Deleted..",Toast.LENGTH_SHORT).show();
                        imgone.setImageResource(R.drawable.ic_camera_alt_black_24dp);
                        cancelone.setVisibility(View.INVISIBLE);
                        cancelone.setClickable(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterTA.this,"Failed,Please Try Again..",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        canceltwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(view.getContext());
                progressDialog.setMessage("Wait..");
                progressDialog.show();
                storageReference=FirebaseStorage.getInstance().getReferenceFromUrl(imgtwodwnld);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterTA.this,"Successfully Deleted..",Toast.LENGTH_SHORT).show();
                        imgtwo.setImageResource(R.drawable.ic_camera_alt_black_24dp);
                        canceltwo.setVisibility(View.INVISIBLE);
                        canceltwo.setClickable(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterTA.this,"Failed,Please Try Again..",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgurione != null) {
                    flag = 0;
                    checknullfields();

                    if (flag == 0) {
                        MainActivity.checknet(view.getContext());
                        if (MainActivity.connected == true) {
                            showalertbox();
                        }
                    } else {
                        Toast.makeText(RegisterTA.this, "Fill Completely..", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterTA.this, "Upload images..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void showalertbox(){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Register");
        alert.setMessage("Are you sure you want to register your travel agency?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final ProgressDialog pd=new ProgressDialog(RegisterTA.this);
                pd.setMessage("Wait..");
                pd.show();

                TA_POJO ta=new TA_POJO(name.getText().toString(),
                        location.getText().toString(),phone.getText().toString(),email.getText().toString(),price.getText().toString(),
                        imgonedwnld,imgtwodwnld);

                FinalForm.firebase.child("TravelAgents").push().setValue(ta, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if(firebaseError==null){
                            Toast.makeText(RegisterTA.this, "Registration Successfull..", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(RegisterTA.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(RegisterTA.this, "Try Again..", Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();
                    }
                });
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.create().show();
    }

    public void checknullfields(){
        flag=0;
        if(TextUtils.isEmpty(name.getText()))
            flag=1;
        else if(TextUtils.isEmpty(location.getText()))
            flag=1;
        else if(TextUtils.isEmpty(phone.getText()))
            flag=1;
        else if(TextUtils.isEmpty(email.getText()))
            flag=1;
        else if(TextUtils.isEmpty(price.getText()))
            price.setText("null");
        else if(imguritwo==null)
            imgtwodwnld="null";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMGONE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Wait..");
            progressDialog.show();
            uploadimage(data.getData(),1);
        }
        if(requestCode==IMGTWO && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Wait..");
            progressDialog.show();
            uploadimage(data.getData(),2);
        }
    }

    public void uploadimage(final Uri uri, final int number){
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReferenceFromUrl("gs://travelagentproject-40e3a.appspot.com");
        StorageReference imgref=storageReference.child("TraveAgents").child("Shop").child(UUID.randomUUID().toString());

        ContentResolver cr = getBaseContext().getContentResolver();
        try {
            InputStream inputStream=cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            final byte[] data = baos.toByteArray();

            UploadTask uploadTask = imgref.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterTA.this,"Failed,Please Try Again..",Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterTA.this,"Upload Successfull..",Toast.LENGTH_SHORT).show();
                    if(number==1) {
                        imgone.setImageURI(uri);
                        imgonedwnld = taskSnapshot.getDownloadUrl().toString();
                        imgurione=taskSnapshot.getDownloadUrl();
                        cancelone.setVisibility(View.VISIBLE);
                        cancelone.setClickable(true);
                    }
                    else if(number==2){
                        imgtwo.setImageURI(uri);
                        imgtwodwnld = taskSnapshot.getDownloadUrl().toString();
                        imguritwo=taskSnapshot.getDownloadUrl();
                        canceltwo.setVisibility(View.VISIBLE);
                        canceltwo.setClickable(true);
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
