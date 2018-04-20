package com.example.rajneesh.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CastDetails extends AppCompatActivity {
    ImageView castPhoto;
    ReadMoreTextView biography;
    TextView birthday;
    TextView birth_place;
    TextView name;
    RecyclerView castMovieCredits;
    CastMovieCreditAdapter adapter;
    ArrayList<CastMovieCreditResponse.casts> movieCreditlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        castPhoto= findViewById(R.id.castPhoto);
        biography= findViewById(R.id.biography);
        birth_place= findViewById(R.id.birthplace);
        birthday= findViewById(R.id.birthday);
        name= findViewById(R.id.name);
        castMovieCredits= findViewById(R.id.moviecast);
        movieCreditlist= new ArrayList<>();


        Intent intent= getIntent();
        Bundle bundle= intent.getExtras();
        int id= bundle.getInt("castid");
       // Toast.makeText(this,id+"",Toast.LENGTH_LONG).show();

        fetchCastDetail(id);
        fetchCastMovieCredits(id);

        adapter= new CastMovieCreditAdapter(this, movieCreditlist, new CastMovieCreditAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {
                Intent intent= new Intent(CastDetails.this,MovieDetails.class);
                Bundle bundle= new Bundle();
                bundle.putInt("movieid",movieCreditlist.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            //Toast.makeText(CastDetails.this,"clicked",Toast.LENGTH_SHORT);
            }
        });

        castMovieCredits.setAdapter(adapter);
        castMovieCredits.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        castMovieCredits.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        castMovieCredits.setItemAnimator(new DefaultItemAnimator());


    }

    private void fetchCastMovieCredits(int id) {

        Call<CastMovieCreditResponse> call= APIClient.getInstance().getMovieApi().getCastMovieCredits(id+"","9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<CastMovieCreditResponse>() {
            @Override
            public void onResponse(Call<CastMovieCreditResponse> call, Response<CastMovieCreditResponse> response) {
                CastMovieCreditResponse response1= response.body();
                ArrayList<CastMovieCreditResponse.casts> movielist= response1.getCast();
                Toast.makeText(CastDetails.this,"init",Toast.LENGTH_LONG).show();
                if(movielist!=null){
                    movieCreditlist.clear();
                    movieCreditlist.addAll(movielist);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<CastMovieCreditResponse> call, Throwable t) {
                Toast.makeText(CastDetails.this, t.getMessage()+"", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchCastDetail(int id) {

        Call<CastDetailResponse> call= APIClient.getInstance().getMovieApi().getCastDetail(id+"","9e88cc754362f676e652e8856be5d62d");
        //Toast.makeText(CastDetails.this,"init",Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<CastDetailResponse>() {
            @Override
            public void onResponse(Call<CastDetailResponse> call, Response<CastDetailResponse> response) {
                CastDetailResponse response1= response.body();
                Picasso.get().load("http://image.tmdb.org/t/p/original/"+response1.getProfile_path()).into(castPhoto);
                biography.setText(response1.getBiography());
                birth_place.setText(response1.getPlace_of_birth());
                birthday.setText(response1.getBirthday());
               // String name1= response1.getName();
                name.setText(response1.getName());

            }

            @Override
            public void onFailure(Call<CastDetailResponse> call, Throwable t) {
                Toast.makeText(CastDetails.this, t.getMessage()+"", Toast.LENGTH_LONG).show();

            }
        });


    }

}
