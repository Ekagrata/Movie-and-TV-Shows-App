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
 * Created by RAJNEESH on 11-04-2018.
 */

public class MovieTrailerListAdapter  extends RecyclerView.Adapter<MovieTrailerListAdapter.ViewHolder> {
    interface OnItemClickListner{
        void OnItemClicked(int position);
    }
    int height,width;
    Context context;
    ArrayList<MovieVideoResponse.Trailer> trailers;
    MovieTrailerListAdapter.OnItemClickListner listner;
    WindowManager windowManager;

    public MovieTrailerListAdapter(Context context, ArrayList<MovieVideoResponse.Trailer> trailers, MovieTrailerListAdapter.OnItemClickListner listner, WindowManager windowManager) {
        this.context = context;
        this.trailers = trailers;
        this.listner = listner;
        this.windowManager=windowManager;
    }

    @Override
    public MovieTrailerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.row,parent,false);
        MovieTrailerListAdapter.ViewHolder holder= new MovieTrailerListAdapter.ViewHolder(itemView);
        getScreenSize();
        return holder;

    }
    public void getScreenSize(){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        height=displayMetrics.heightPixels;
        width=displayMetrics.widthPixels;
    }

    @Override
    public void onBindViewHolder(final MovieTrailerListAdapter.ViewHolder holder, int position) {


        MovieVideoResponse.Trailer trailer= trailers.get(position);
        holder.movieName.setText(trailer.getName());
        holder.fav.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());

            }
        });

        Picasso.get().load("https://img.youtube.com/vi/"+trailer.getKey()+"/mqdefault.jpg").resize(width,0).into(holder.poster);
        // Glide.with(context).load("http://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).into(holder.poster);



    }




    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView movieName;
        ImageView poster;
        View itemView;
        ImageView fav;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            movieName= itemView.findViewById(R.id.moviename);
            poster= itemView.findViewById(R.id.poster);
            fav= itemView.findViewById(R.id.fav);

        }
    }

}
