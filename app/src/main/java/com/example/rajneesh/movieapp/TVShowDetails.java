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
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetails extends AppCompatActivity {

    ImageView poster;
    ImageView posterSmall;
    TextView moviename;
    TextView genre;
    TextView rating;
    TextView overview;
    RecyclerView castsView;
    RecyclerView similarMovies;
    RecyclerView trailerList;
    //Button trailer;
    // YouTubePlayerView youtubePlayer;
    TVShowRecyclerAdapter adapter1;
    MovieTrailerListAdapter adapter2;
    ArrayList<TVShow> SimilarMovies;
    CastRecyclerAdapter adapter;
    ArrayList<cast> casts;
    ArrayList<MovieVideoResponse.Trailer> trailers;
    int height;
    int width;
    int id;


    //TextView genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        poster = findViewById(R.id.poster);
        moviename = findViewById(R.id.moviename);
        posterSmall = findViewById(R.id.posterSmall);
        genre = findViewById(R.id.genre);
        rating = findViewById(R.id.rating);
        overview = findViewById(R.id.overview);
        similarMovies = findViewById(R.id.similarmovies);
        trailers = new ArrayList<>();
        trailerList = findViewById(R.id.trailerList);
        // trailer= findViewById(R.id.trailer);


//        youtubePlayer= findViewById(R.id.youtubeview);

        castsView = findViewById(R.id.castsView);
        casts = new ArrayList<>();
        SimilarMovies = new ArrayList<>();

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getInt("showid");
        //Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();

        fetchTVResponse(id);
        fetchCast(id);
        fetchSimilarTV(id);
        fetchTrailer();


        adapter2= new MovieTrailerListAdapter(this, trailers, new MovieTrailerListAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {
                MovieVideoResponse.Trailer trail = trailers.get(position);
                String trailKey = trail.getKey();
                Toast.makeText(TVShowDetails.this,trailKey,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TVShowDetails.this, YoutubeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("trailkey", trailKey);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        },getWindowManager());

        trailerList.setAdapter(adapter2);
        trailerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailerList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        trailerList.setItemAnimator(new DefaultItemAnimator());

        adapter1 = new TVShowRecyclerAdapter(this, SimilarMovies, new TVShowRecyclerAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {
                Intent intent = new Intent(TVShowDetails.this, TVShowDetails.class);
                Bundle bundle = new Bundle();
                TVShow show = SimilarMovies.get(position);
                bundle.putInt("showid", show.getId());
                intent.putExtras(bundle);
                startActivity(intent);


            }
        },getWindowManager());
        similarMovies.setAdapter(adapter1);
        similarMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        similarMovies.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        similarMovies.setItemAnimator(new DefaultItemAnimator());

        adapter = new CastRecyclerAdapter(this, casts, new CastRecyclerAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {
                Intent intent1 = new Intent(TVShowDetails.this, CastDetails.class);
                Bundle bundle = new Bundle();
                bundle.putInt("castid", casts.get(position).getId());
                //Toast.makeText(MovieDetails.this,casts.get(position).getId()+"",Toast.LENGTH_LONG).show();
                intent1.putExtras(bundle);
                startActivity(intent1);
                // Toast.makeText(MovieDetails.this,"clicked"+casts.get(position).getName(),Toast.LENGTH_SHORT).show();

            }
        });
        castsView.setAdapter(adapter);
        castsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        castsView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        castsView.setItemAnimator(new DefaultItemAnimator());

        WindowManager windowManager = getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;


    }

    private void fetchSimilarTV(int id) {
        Call<TVShowResponse> call = APIClient.getInstance().getMovieApi().getSimilarShows(id + "", "9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                TVShowResponse response1 = response.body();
                ArrayList<TVShow> shows = response1.getTvShows();
                if (shows != null) {
                    SimilarMovies.clear();
                    SimilarMovies.addAll(shows);
                    adapter1.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                Toast.makeText(TVShowDetails.this, t.getMessage() + "", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void fetchCast(int id) {
        Call<CastResponse> call = APIClient.getInstance().getMovieApi().getTVCast(id + "", "9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                CastResponse response1 = response.body();
                ArrayList<cast> castsget = response1.getCast();
//                Log.d("list",response.body().casts.get(0).getName());

                if (castsget != null) {
                    casts.clear();

                    casts.addAll(castsget);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
                Toast.makeText(TVShowDetails.this, t.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchTVResponse(int id) {

        String ids = id + "";
        Call<TVShowDetailResponse> call = APIClient.getInstance().getMovieApi().getShowdetail(ids, "9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TVShowDetailResponse>() {
            @Override
            public void onResponse(Call<TVShowDetailResponse> call, Response<TVShowDetailResponse> response) {
                TVShowDetailResponse response1 = response.body();
                if (response.isSuccessful()) {
                    Picasso.get().load("http://image.tmdb.org/t/p/original" + response1.getBackdrop_path()).resize(width, 0).into(poster);
                    // Glide.with(MovieDetails.this).load("http://image.tmdb.org/t/p/original" + response1.getBackdrop_path()).override(width,0).into(poster);
                    // Log.d("backdrop", "http://image.tmdb.org/t/p/original" + response1.getBackdrop_path());
                    // Picasso.get().load("http://image.tmdb.org/t/p/w780" + response1.getPoster_path()).into(posterSmall);
                    Glide.with(TVShowDetails.this).load("http://image.tmdb.org/t/p/w780" + response1.getPoster_path()).into(posterSmall);


                    String title = response1.getName();
                    // Log.d("poster", "http://image.tmdb.org/t/p/w780" + response1.getPoster_path());
                    moviename.setText(title);
                    ArrayList<TVShowDetailResponse.Genres> genres = response1.getGenres();
                    String string = "";
                    for (int i = 0; i < genres.size(); i++) {
                        string = string + genres.get(i).getName() + ",";
                    }
                    genre.setText(string);
                    rating.setText(response1.getVote_average() + "");
                    overview.setText(response1.getOverview());


                } else
                    Toast.makeText(TVShowDetails.this, "Response Failed", Toast.LENGTH_SHORT).show();

//                ArrayList<MovieDetailResponse.Genres> genres=response1.getGenres();
//                String string=null;
//                for(int i=0;i<genres.size();i++){
//                    string= string+genres.get(i)+",";
//                }
//                genre.setText(string);
            }

            @Override
            public void onFailure(Call<TVShowDetailResponse> call, Throwable t) {

                Toast.makeText(TVShowDetails.this, t.getMessage() + "", Toast.LENGTH_LONG).show();


            }
        });

    }

    public void fetchTrailer() {
        Call<MovieVideoResponse> call = APIClient.getInstance().getMovieApi().getTVTrailers(id + "", "9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieVideoResponse>() {
            @Override
            public void onResponse(Call<MovieVideoResponse> call, Response<MovieVideoResponse> response) {
                MovieVideoResponse response1 = response.body();
                ArrayList<MovieVideoResponse.Trailer> trailersget = response1.getTrailers();
                if(trailersget!=null){
                    trailers.clear();

                    trailers.addAll(trailersget);
                    adapter2.notifyDataSetChanged();

                }


            }

            @Override
            public void onFailure(Call<MovieVideoResponse> call, Throwable t) {
                Toast.makeText(TVShowDetails.this, t.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        });


    }

}
