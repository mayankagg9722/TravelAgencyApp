package com.example.mayank.travelagentproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class contains the adapter to bind the card layout with recycler view
 * Created by UddishVerma on 21/08/16.
 */
public class TravelAgentsAdapter extends RecyclerView.Adapter<TravelAgentsAdapter.DetailsViewHolder>{

    Context ctx;

    ArrayList<TravelAgents_POJO.TravelAgentsDetails> details;

    public TravelAgentsAdapter(ArrayList<TravelAgents_POJO.TravelAgentsDetails> details, Context ctx) {
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
        TravelAgents_POJO.TravelAgentsDetails mDetails = details.get(position);
        holder.taName.setText(mDetails.tAName);
        holder.taLocation.setText(mDetails.tAAddress);
        holder.taLocation.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView img;
        TextView taName, taLocation;
        Context ctx;

        ArrayList<TravelAgents_POJO.TravelAgentsDetails> details = TravelAgents_POJO.getDetails();

        public DetailsViewHolder(View view, ArrayList<TravelAgents_POJO.TravelAgentsDetails> details,Context ctx) {
            super(view);
            this.details = details;
            this.ctx = ctx;
            view.setOnClickListener(this);
            img = (ImageView) view.findViewById(R.id.ta_image);
            taName = (TextView) view.findViewById(R.id.travelAgent_name);
            taLocation = (TextView) view.findViewById(R.id.ta_location);

        }
        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            TravelAgents_POJO.TravelAgentsDetails obj=this.details.get(position);
            Intent intent=new Intent(this.ctx,FinalForm.class);
            this.ctx.startActivity(intent);
        }
    }
}
