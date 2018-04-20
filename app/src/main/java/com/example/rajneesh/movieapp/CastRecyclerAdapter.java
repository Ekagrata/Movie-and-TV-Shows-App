package com.example.rajneesh.movieapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by RAJNEESH on 08-04-2018.
 */

public class CastRecyclerAdapter extends RecyclerView.Adapter<CastRecyclerAdapter.ViewHolder>{

    interface OnItemClickListner{
        void OnItemClicked(int position);
    }

    Context context;
    ArrayList<cast> casts;
    CastRecyclerAdapter.OnItemClickListner listner;

    public CastRecyclerAdapter(Context context, ArrayList<cast> casts, CastRecyclerAdapter.OnItemClickListner listner) {
        this.context = context;
        this.casts = casts;
        this.listner = listner;
    }

    @Override
    public CastRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.cast_row,parent,false);
        CastRecyclerAdapter.ViewHolder holder= new CastRecyclerAdapter.ViewHolder(itemView);
        return holder;

    }

    @Override
    public void onBindViewHolder(final CastRecyclerAdapter.ViewHolder holder, int position) {


        cast cast= casts.get(position);
        holder.castName.setText(cast.getName());
        Log.d("set",cast.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());

            }
        });

        Picasso.get().load("http://image.tmdb.org/t/p/original"+cast.getProfile_path()).into(holder.castPhoto);
        // Glide.with(context).load("http://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).into(holder.poster);



    }




    @Override
    public int getItemCount() {
        return casts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView castName;
        ImageView castPhoto;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            castName= itemView.findViewById(R.id.castName);
            castPhoto= itemView.findViewById(R.id.castPhoto);

        }
    }
}
