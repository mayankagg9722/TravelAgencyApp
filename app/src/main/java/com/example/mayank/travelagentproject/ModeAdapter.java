package com.example.mayank.travelagentproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by mayank on 10-08-2016.
 */

public class ModeAdapter extends RecyclerView.Adapter<ModeAdapter.MyViewHolder> {


    ArrayList<ModeTransport> modelist = new ArrayList<ModeTransport>();
    Context ctx;
    static Boolean start=false;

    public ModeAdapter(ArrayList<ModeTransport> mode, Context context) {
        this.modelist = mode;
        this.ctx=context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewmode,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view,ctx,modelist);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModeTransport modeTransport=modelist.get(position);
        holder.textView.setText(modeTransport.getName());
        holder.imageView.setImageResource(modeTransport.getImage());
    }

    @Override
    public int getItemCount() {
        return modelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        ImageView imageView;
        TextView textView;
        Context ctx;
        ArrayList<ModeTransport> list=new ArrayList<ModeTransport>();
        public MyViewHolder(View itemView,Context ctx,ArrayList<ModeTransport> list) {
            super(itemView);
            this.list=list;
            this.ctx=ctx;
            itemView.setOnClickListener(this);
            imageView=(ImageView)itemView.findViewById(R.id.modeimage);
            textView=(TextView)itemView.findViewById(R.id.modetext);
        }
        @Override
        public void onClick(View view) {
            if(MainActivity.user!=null){
                int position = getAdapterPosition();
                ModeTransport modeTransport = this.list.get(position);

                if(modeTransport.getName().equals("Locale")) {
                    Intent intent = new Intent(ctx,Locale.class);
                    ctx.startActivity(intent);
                }
                else {
                    if (start) {
                        Intent intent = new Intent(ctx, Modedislpay.class);
                        intent.putExtra("mode", modeTransport.getName());
                        ctx.startActivity(intent);
                    } else {
                        Toast.makeText(ctx, "Please First Enter Location At Top", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else{
                Toast.makeText(ctx, "Please Login to book a cab.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


