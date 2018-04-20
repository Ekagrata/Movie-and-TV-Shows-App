package com.example.rajneesh.movieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by RAJNEESH on 20-04-2018.
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder>{
    interface OnItemClickListner{
        void OnItemClicked(int position);
    }

    Context context;
    ArrayList<Movie> movies;
    ProgressBar progressBar;
    SearchListAdapter.OnItemClickListner listner;


    public SearchListAdapter(Context context, ArrayList<Movie> movies, SearchListAdapter.OnItemClickListner listner) {
        this.context = context;
        this.movies = movies;
        this.listner = listner;

    }



    @Override
    public SearchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.searchresultrow,parent,false);
        SearchListAdapter.ViewHolder holder= new SearchListAdapter.ViewHolder(itemView);

        return holder;

    }

    @Override
    public void onBindViewHolder(final SearchListAdapter.ViewHolder holder, int position) {

        Movie movie= movies.get(position);
        holder.movieName.setText(movie.getTitle());
        //Log.d("fetchsizebind",movie.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());

            }
        });
        Picasso.get().load("http://image.tmdb.org/t/p/original"+movie.getPoster_path()).into(holder.poster, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                // progressBar.setVisibility(View.VISIBLE);

            }
        });
        Log.d("photo","http://image.tmdb.org/t/p/original"+movie.getPoster_path());
        //progressBar.setVisibility(View.GONE);
        // Glide.with(context).load("http://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).into(holder.poster);



    }




    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView movieName;
        ImageView poster;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            movieName= itemView.findViewById(R.id.Name);
            poster= itemView.findViewById(R.id.poster);
            progressBar= itemView.findViewById(R.id.progressbar);

        }
    }
}
