package com.example.mayank.travelagentproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class contains the adapter to bind the card layout with recycler view
 * Created by UddishVerma on 21/08/16.
 */

public class TravelAgentsAdapter extends RecyclerView.Adapter<TravelAgentsAdapter.DetailsViewHolder>{

    Context ctx;

    ArrayList<TA_POJO> details=new ArrayList<TA_POJO>();

    public TravelAgentsAdapter(ArrayList<TA_POJO> details, Context ctx) {
        this.details = details;
        this.ctx = ctx;
    }

    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.travelagents_card_layout, parent, false);
        DetailsViewHolder viewHolder = new DetailsViewHolder(view, details, ctx);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, int position) {
        TA_POJO mDetails = details.get(position);

        holder.name.setText(mDetails.getName());
        holder.location.setText(mDetails.getLocation());
        holder.location.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name,location;
        Context ctx;
        LinearLayout linearLayout;

        ArrayList<TA_POJO> details ;

        public DetailsViewHolder(View view, final ArrayList<TA_POJO> details, final Context ctx) {
            super(view);
            this.details = details;
            this.ctx = ctx;
            img = (ImageView) view.findViewById(R.id.ta_image);
            name = (TextView) view.findViewById(R.id.travelAgent_name);
            location = (TextView) view.findViewById(R.id.ta_location);
            linearLayout=(LinearLayout)view.findViewById(R.id.linearlayout);

            view.setClickable(true);
            view.setFocusableInTouchMode(true);


            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    TA_POJO obj=details.get(position);
                    Intent intent=new Intent(ctx,FinalForm.class);
                    Bundle bundle=TravelAgents.bundle;
                    bundle.putString("agentname",obj.getName());
                    bundle.putString("agentaddress",obj.getLocation());
                    bundle.putString("agentcontact",obj.getNumber());
                    bundle.putString("agentemail",obj.getEmail());

                    bundle.putString("price",obj.getPrice());
                    bundle.putString("imgone",obj.getImgoneurl());
                    bundle.putString("imgtwo",obj.getImgtwourl());

                    intent.putExtras(bundle);
                    ctx.startActivity(intent);

                }
            });


        }

    }
}
