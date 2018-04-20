package com.example.rajneesh.movieapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    RecyclerView recyclerView, recyclerView1, recyclerView2, recyclerView3;
    RecyclerAdapter adapter, adapter1, adapter2, adapter3;
    ProgressBar progressBar;
    ArrayList<Movie> movies, toprated, upcoming, nowplaying;
    MovieSelectedCallBack callBack;
    MovieDao movieDao;
    WindowManager windowManager;
    MaterialSearchView searchView;




    interface MovieSelectedCallBack {
        void onMovieSelected(Movie movie);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callBack = (MovieSelectedCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity should implement UserSelectedCallback");
        }

    }


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = view.findViewById(R.id.movielist);
        recyclerView1 = view.findViewById(R.id.topratedmovielist);
        recyclerView2 = view.findViewById(R.id.upcomingmovielist);
        recyclerView3 = view.findViewById(R.id.nowplaying);
        progressBar = view.findViewById(R.id.progressbar);
        movies = new ArrayList<>();
        toprated = new ArrayList<>();
        upcoming = new ArrayList<>();
        nowplaying = new ArrayList<>();


        movieDao= AppDatabase.getInstance(getContext()).getMoviesDao();
        windowManager=getActivity().getWindowManager();
        searchView= view.findViewById(R.id.search_view);


        movies=(ArrayList<Movie>) movieDao.getPopularMovies();
        Log.d("fetchsize",movies.size()+"");
        if(movies.size()>0) {
            Log.d("fetchsize", movies.get(0).getTitle() + "");
            Log.d("fetchsize", movies.get(0).backdrop_path + "");
//            Log.d("fetchsize", movies.size() + "");
//            Log.d("fetchsize", movies.size() + "");
        }
        toprated=(ArrayList<Movie>) movieDao.getTopRatedMovies();
        upcoming=(ArrayList<Movie>) movieDao.getUpcoming();
        nowplaying=(ArrayList<Movie>) movieDao.getNowPlaying();


        adapter = new RecyclerAdapter(getContext(), movies, new RecyclerAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {
                Movie movie = movies.get(position);
                callBack.onMovieSelected(movie);

            }
        },windowManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();


        adapter1 = new RecyclerAdapter(getContext(), toprated, new RecyclerAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {

                Movie movie = toprated.get(position);
                callBack.onMovieSelected(movie);

            }
        },windowManager);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());



        adapter2 = new RecyclerAdapter(getContext(), upcoming, new RecyclerAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {

                Movie movie = upcoming.get(position);
                callBack.onMovieSelected(movie);

            }
        },windowManager);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());



        adapter3 = new RecyclerAdapter(getContext(), nowplaying, new RecyclerAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {

                Movie movie = nowplaying.get(position);
                callBack.onMovieSelected(movie);
            }
        },windowManager);
        recyclerView3.setAdapter(adapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView3.setItemAnimator(new DefaultItemAnimator());

        fetchMovies();
        fetchNowPlaying();
        fetchTopRatedMovies();
        fetchUpcoming();



        return  view;

    }

    private void fetchNowPlaying() {
        recyclerView3.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> call = APIClient.getInstance().getMovieApi().getNowplaying("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1 = response.body();
                ArrayList<Movie> movieList = response1.getMovies();
                if (movieList != null) {
                    nowplaying.clear();
                    for(int i=0;i<movieList.size();i++){
                        Movie m= movieList.get(i);
                        m.setIsNowPlaying(1);
                    }
                    nowplaying.addAll(movieList);

                    adapter3.notifyDataSetChanged();
                    movieDao.insertMovieList(nowplaying);
                    Log.d("insert",nowplaying.get(0).getTitle());
                }

                recyclerView3.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void fetchUpcoming() {
        recyclerView2.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> call = APIClient.getInstance().getMovieApi().getUpcoming("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1 = response.body();
                ArrayList<Movie> movieList = response1.getMovies();
                if (movieList != null) {
                    upcoming.clear();
                    for(int i=0;i<movieList.size();i++){
                        Movie m= movieList.get(i);
                        m.setIsUpcoming(1);
                    }

                    upcoming.addAll(movieList);

                    adapter2.notifyDataSetChanged();
                    movieDao.insertMovieList(upcoming);
                }

                recyclerView2.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void fetchTopRatedMovies() {
        recyclerView1.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> call = APIClient.getInstance().getMovieApi().getTopRated("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1 = response.body();
                ArrayList<Movie> movieList = response1.getMovies();
                if (movieList != null) {
                    toprated.clear();
                    for(int i=0;i<movieList.size();i++){
                        Movie m= movieList.get(i);
                        m.setIsTopRated(1);
                    }
                    toprated.addAll(movieList);

                    adapter1.notifyDataSetChanged();
                    movieDao.insertMovieList(toprated);
                }

                recyclerView1.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }



    public void fetchMovies() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> call = APIClient.getInstance().getMovieApi().getMovies("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1= response.body();
                ArrayList<Movie> movieList= response1.getMovies();
                if(movieList != null){
                    movies.clear();
                    for(int i=0;i<movieList.size();i++){
                        Movie m= movieList.get(i);
                        m.setIsPopular(1);
                    }
                    movies.addAll(movieList);
                    adapter.notifyDataSetChanged();

                    movieDao.insertMovieList(movies);
                }

                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }
        });

    }




}
