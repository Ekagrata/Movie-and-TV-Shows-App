package com.example.rajneesh.movieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by RAJNEESH on 09-04-2018.
 */

public class CastMovieCreditAdapter extends RecyclerView.Adapter<CastMovieCreditAdapter.ViewHolder> {

    interface OnItemClickListner{
        void OnItemClicked(int position);
    }

    Context context;
    ArrayList<CastMovieCreditResponse.casts> movies;
    CastMovieCreditAdapter.OnItemClickListner listner;

    public CastMovieCreditAdapter(Context context, ArrayList<CastMovieCreditResponse.casts> movies, CastMovieCreditAdapter.OnItemClickListner listner) {
        this.context = context;
        this.movies = movies;
        this.listner = listner;
    }

    @Override
    public CastMovieCreditAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.cast_movie_credit_row,parent,false);
        CastMovieCreditAdapter.ViewHolder holder= new CastMovieCreditAdapter.ViewHolder(itemView);
        return holder;

    }

    @Override
    public void onBindViewHolder(final CastMovieCreditAdapter.ViewHolder holder, int position) {


        CastMovieCreditResponse.casts cast= movies.get(position);
        holder.movieName.setText(cast.getTitle());
        holder.character.setText("as "+cast.getCharacter());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());

            }
        });

        Picasso.get().load("http://image.tmdb.org/t/p/original"+cast.getPoster_path()).into(holder.poster);
        // Glide.with(context).load("http://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).into(holder.poster);



    }




    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView movieName;
        ImageView poster;
        TextView character;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            movieName= itemView.findViewById(R.id.moviename);
            poster= itemView.findViewById(R.id.poster);
            character= itemView.findViewById(R.id.character);

        }
    }
}
