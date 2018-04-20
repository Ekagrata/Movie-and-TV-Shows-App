package com.example.rajneesh.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView, recyclerView1;
    SearchListAdapter adapter;
    SearchTVListAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent= getIntent();
        Bundle bundle= intent.getExtras();
        recyclerView= findViewById(R.id.search_result);
        recyclerView1= findViewById(R.id.tv_result);
        final ArrayList<Movie> movieList= (ArrayList<Movie>)bundle.getSerializable("movielist");
        final ArrayList<TVShow> showList= (ArrayList<TVShow>)bundle.getSerializable("showlist");

        adapter= new SearchListAdapter(this, movieList, new SearchListAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {
                Movie movie= movieList.get(position);
                Intent intent= new Intent(SearchActivity.this,MovieDetails.class);
                Bundle bundle= new Bundle();

                bundle.putInt("movieid",movie.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();


        adapter1= new SearchTVListAdapter(this, showList, new SearchTVListAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {
                TVShow show= showList.get(position);
                Intent intent= new Intent(SearchActivity.this,TVShowDetails.class);
                Bundle bundle= new Bundle();

                bundle.putInt("showid",show.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView1.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        adapter1.notifyDataSetChanged();





    }
}
