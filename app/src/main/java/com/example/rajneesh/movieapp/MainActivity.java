package com.example.rajneesh.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieFragment.MovieSelectedCallBack, TVShowFragment.TVShowSelectedCallBack{

     ViewPagerAdapter adapter;
     ViewPager viewPager;
     TabLayout tabLayout;
     MaterialSearchView searchView;

     ArrayList<Movie> movieList,isfound;
     ArrayList<TVShow> showsList,isTvfound;




     private final static String pageTitle[]= {"Movies","TV Shows"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager= findViewById(R.id.View_pager);
        adapter= new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new MovieFragment(),pageTitle[0]);
        adapter.AddFragment(new TVShowFragment(),pageTitle[1]);
        viewPager.setAdapter(adapter);
        tabLayout= findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        searchView= findViewById(R.id.search_view);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.cardview_dark_background));
        movieList = new ArrayList<>();
        isfound= new ArrayList<>();
         showsList= new ArrayList<>();
         isTvfound= new ArrayList<>();
        fetchMovies();
        fetchNowPlaying();
        fetchTopRatedMovies();
        fetchUpcoming();
        fetchOnAir();
        fetchPopular();
        fetchTopRated();
        fetchAiringToday();


        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {


            }

            @Override
            public void onSearchViewClosed() {


                //  viewPager= findViewById(R.id.View_pager);
                //adapter= new ViewPagerAdapter(getSupportFragmentManager());
                adapter.AddFragment(new MovieFragment(),pageTitle[0]);
                adapter.AddFragment(new TVShowFragment(),pageTitle[1]);
                viewPager.setAdapter(adapter);
                //tabLayout= findViewById(R.id.tab_layout);
                tabLayout.setupWithViewPager(viewPager);
                //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                //tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.cardview_dark_background));

//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                adapter.notifyDataSetChanged();
//
//                recyclerView1.setAdapter(adapter1);
//                recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//                recyclerView1.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
//                recyclerView1.setItemAnimator(new DefaultItemAnimator());
//
//                recyclerView2.setAdapter(adapter2);
//                recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//                recyclerView2.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
//                recyclerView2.setItemAnimator(new DefaultItemAnimator());
//
//                recyclerView3.setAdapter(adapter3);
//                recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//                recyclerView3.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
//                recyclerView3.setItemAnimator(new DefaultItemAnimator());
//
//                fetchMovies();
//                fetchNowPlaying();
//                fetchTopRatedMovies();
//                fetchUpcoming();



            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent= new Intent(MainActivity.this,SearchActivity.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("movielist",(Serializable)isfound);
                bundle.putSerializable("showlist",(Serializable)isTvfound);
                intent.putExtras(bundle);
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText!=null && !newText.isEmpty()){
                    isfound= new ArrayList<>();
                    isTvfound= new ArrayList<>();
                    Toast.makeText(MainActivity.this,"init",Toast.LENGTH_LONG).show();
                    for(int i=0;i<movieList.size();i++){

                        String name= movieList.get(i).getTitle();
                        if(name.contains(newText)){
                            isfound.add(movieList.get(i));
                            Log.d("iterate",isfound.get(0).getTitle());
                           // Log.d("iterate",isfound.get(1).getTitle());
                            Log.d("text change",isfound.get(0).getTitle());
                        }
                    }

                    for(int i=0;i<showsList.size();i++){

                        String name= showsList.get(i).getName();
                        if(name.contains(newText)){
                            isTvfound.add(showsList.get(i));
                           // Log.d("iterate",isfound.get(0).getTitle());
                            // Log.d("iterate",isfound.get(1).getTitle());
                           // Log.d("text change",isfound.get(0).getTitle());
                        }
                    }
                }



                return true;
            }
        });






    }



    @Override
    public void onMovieSelected(Movie movie) {
        Intent intent= new Intent(MainActivity.this,MovieDetails.class);
        Bundle bundle= new Bundle();

        bundle.putInt("movieid",movie.getId());
        intent.putExtras(bundle);
        startActivity(intent);


    }

    @Override
    public void onTVShowSelected(TVShow show) {
        Intent intent= new Intent(MainActivity.this,TVShowDetails.class);
        Bundle bundle= new Bundle();

        bundle.putInt("showid",show.getId());
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem item= menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    private void fetchNowPlaying() {

        Call<MovieResponse> call = APIClient.getInstance().getMovieApi().getNowplaying("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1 = response.body();
                ArrayList<Movie> movieList = response1.getMovies();
                if (movieList != null) {
                   MainActivity.this.movieList.addAll(movieList);
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fetchUpcoming() {
        Call<MovieResponse> call = APIClient.getInstance().getMovieApi().getUpcoming("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1 = response.body();
                ArrayList<Movie> movieList = response1.getMovies();
                if (movieList != null) {
                    MainActivity.this.movieList.addAll(movieList);
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void fetchTopRatedMovies() {
        Call<MovieResponse> call = APIClient.getInstance().getMovieApi().getTopRated("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1 = response.body();
                ArrayList<Movie> movieList = response1.getMovies();
                if (movieList != null) {
                   MainActivity.this.movieList.addAll(movieList);
                }


            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void fetchMovies() {

        Call<MovieResponse> call = APIClient.getInstance().getMovieApi().getMovies("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1= response.body();
                ArrayList<Movie> movieList= response1.getMovies();
                if(movieList != null){
                    MainActivity.this.movieList.addAll(movieList);
                }




            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void fetchOnAir() {
        Call<TVShowResponse> call = APIClient.getInstance().getMovieApi().getOn_The_Air("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                TVShowResponse response1= response.body();
                ArrayList<TVShow> showList= response1.getTvShows();
                if(showList!=null){
                  showsList.addAll(showList);

                }
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchPopular() {
        Call<TVShowResponse> call = APIClient.getInstance().getMovieApi().getPopular("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                TVShowResponse response1= response.body();
                ArrayList<TVShow> showList= response1.getTvShows();
                if(showList!=null){
                    showsList.addAll(showList);

                }
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchAiringToday() {
        Call<TVShowResponse> call = APIClient.getInstance().getMovieApi().getAiring_Today("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                TVShowResponse response1= response.body();
                ArrayList<TVShow> showList= response1.getTvShows();
                if(showList!=null){
                    showsList.addAll(showList);

                }
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }





    private void fetchTopRated() {
        Call<TVShowResponse> call = APIClient.getInstance().getMovieApi().getTopRatedTV("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                TVShowResponse response1= response.body();
                ArrayList<TVShow> showList= response1.getTvShows();
                if(showList!=null){
                    showsList.addAll(showList);

                }
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }






}
