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

public class SearchTVListAdapter extends RecyclerView.Adapter<SearchTVListAdapter.ViewHolder> {
    interface OnItemClickListner{
        void OnItemClicked(int position);
    }

    Context context;
    ArrayList<TVShow> shows;
    ProgressBar progressBar;
    SearchTVListAdapter.OnItemClickListner listner;


    public SearchTVListAdapter(Context context, ArrayList<TVShow> shows, SearchTVListAdapter.OnItemClickListner listner) {
        this.context = context;
        this.shows = shows;
        this.listner = listner;

    }



    @Override
    public SearchTVListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.searchresultrow,parent,false);
        SearchTVListAdapter.ViewHolder holder= new SearchTVListAdapter.ViewHolder(itemView);

        return holder;

    }

    @Override
    public void onBindViewHolder(final SearchTVListAdapter.ViewHolder holder, int position) {

        TVShow show= shows.get(position);
        holder.movieName.setText(show.getName());
        //Log.d("fetchsizebind",movie.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());

            }
        });
        Picasso.get().load("http://image.tmdb.org/t/p/original"+show.getPoster_path()).into(holder.poster, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                // progressBar.setVisibility(View.VISIBLE);

            }
        });
        Log.d("photo","http://image.tmdb.org/t/p/original"+show.getPoster_path());
        //progressBar.setVisibility(View.GONE);
        // Glide.with(context).load("http://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).into(holder.poster);



    }




    @Override
    public int getItemCount() {
        return shows.size();
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
