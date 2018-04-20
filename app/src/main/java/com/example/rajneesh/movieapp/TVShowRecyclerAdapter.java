package com.example.rajneesh.movieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by RAJNEESH on 15-04-2018.
 */

public class TVShowRecyclerAdapter extends RecyclerView.Adapter<TVShowRecyclerAdapter.ViewHolder> {

    int height,width;

    interface OnItemClickListner{
        void OnItemClicked(int position);
    }

    Context context;
    ArrayList<TVShow> shows;
    TVShowRecyclerAdapter.OnItemClickListner listner;
    WindowManager windowManager;

    public TVShowRecyclerAdapter(Context context, ArrayList<TVShow> shows, TVShowRecyclerAdapter.OnItemClickListner listner, WindowManager windowManager) {
        this.context = context;
        this.shows = shows;
        this.listner = listner;
        this.windowManager=windowManager;
    }

    public void getScreenSize(){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        height=displayMetrics.heightPixels;
        width=displayMetrics.widthPixels;
    }

    @Override
    public TVShowRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.row,parent,false);
        TVShowRecyclerAdapter.ViewHolder holder= new TVShowRecyclerAdapter.ViewHolder(itemView);
        getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final TVShowRecyclerAdapter.ViewHolder holder, int position) {

        TVShow show= shows.get(position);
        holder.showName.setText(show.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());

            }
        });

        Picasso.get().load("http://image.tmdb.org/t/p/original"+show.getBackdrop_path()).resize(width,0).into(holder.poster);
        // Glide.with(context).load("http://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).into(holder.poster);



    }




    @Override
    public int getItemCount() {
        return shows.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView showName;
        ImageView poster;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            showName= itemView.findViewById(R.id.moviename);
            poster= itemView.findViewById(R.id.poster);

        }
    }

}
