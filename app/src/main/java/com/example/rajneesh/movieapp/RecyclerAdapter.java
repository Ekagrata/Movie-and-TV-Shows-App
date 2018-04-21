package com.example.rajneesh.movieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by RAJNEESH on 05-04-2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    int height,width;
    interface OnItemClickListner{
        void OnItemClicked(int position);
        void OnFavoriteSelected(int position);
    }

    Context context;
    ArrayList<Movie> movies;
    OnItemClickListner listner;
    WindowManager windowManager;

    public RecyclerAdapter(Context context, ArrayList<Movie> movies, OnItemClickListner listner,WindowManager windowManager) {
        this.context = context;
        this.movies = movies;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.row,parent,false);
        ViewHolder holder= new ViewHolder(itemView);
        getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Movie movie= movies.get(position);
        holder.movieName.setText(movie.getTitle());
        Log.d("fetchsizebind",movie.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());

            }
        });
        if(movie.getIsFavourite()==1){
            holder.fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        }

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.fav.getDrawable().getConstantState()== context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp).getConstantState()){
                    holder.fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                }
//
                else{
                    holder.fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));

                }
                listner.OnFavoriteSelected(holder.getAdapterPosition());

            }
        });

        Picasso.get().load("http://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).resize(width,0).into(holder.poster);
       // Glide.with(context).load("http://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).into(holder.poster);



    }




    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView movieName;
        ImageView poster;
        ImageView fav;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            movieName= itemView.findViewById(R.id.moviename);
            poster= itemView.findViewById(R.id.poster);
            fav= itemView.findViewById(R.id.fav);

        }
    }
}
