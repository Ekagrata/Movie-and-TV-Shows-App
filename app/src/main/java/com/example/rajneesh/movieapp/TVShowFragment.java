package com.example.rajneesh.movieapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment {


    RecyclerView airingtoday, latest, toprated, popular;
    TVShowRecyclerAdapter adapterAT, adapterL, adapterTR, adapterP;
    ProgressBar progressBar;
    ArrayList<TVShow> airingtodayList, topratedList, popularList, latestList;

    interface TVShowSelectedCallBack {
        void onTVShowSelected(TVShow show);
    }

    TVShowFragment.TVShowSelectedCallBack callBack;
    WindowManager windowManager;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callBack = (TVShowFragment.TVShowSelectedCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity should implement UserSelectedCallback");
        }

    }


    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        toprated = view.findViewById(R.id.TopRatedList);
        popular = view.findViewById(R.id.PopularList);
        latest = view.findViewById(R.id.latestList);
        airingtoday = view.findViewById(R.id.AiringTodayList);
        progressBar = view.findViewById(R.id.progressbar);
        airingtodayList = new ArrayList<>();
        topratedList = new ArrayList<>();
        popularList = new ArrayList<>();
        latestList = new ArrayList<>();
        windowManager=getActivity().getWindowManager();
        fetchAiringToday();
        adapterAT = new TVShowRecyclerAdapter(getContext(), airingtodayList, new TVShowRecyclerAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {
                TVShow show = airingtodayList.get(position);
                callBack.onTVShowSelected(show);

            }
        },windowManager);
        airingtoday.setAdapter(adapterAT);
        airingtoday.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        airingtoday.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        airingtoday.setItemAnimator(new DefaultItemAnimator());


        fetchTopRatedMovies();
        adapterTR = new TVShowRecyclerAdapter(getContext(), topratedList, new TVShowRecyclerAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {

                TVShow show = topratedList.get(position);
                callBack.onTVShowSelected(show);

            }
        },windowManager);
        toprated.setAdapter(adapterTR);
        toprated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        toprated.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        toprated.setItemAnimator(new DefaultItemAnimator());


        fetchPopular();
        adapterP = new TVShowRecyclerAdapter(getContext(), popularList, new TVShowRecyclerAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {

                TVShow show = popularList.get(position);
                callBack.onTVShowSelected(show);

            }
        },windowManager);
        popular.setAdapter(adapterP);
        popular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popular.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        popular.setItemAnimator(new DefaultItemAnimator());


        fetchOnAir();
        adapterL = new TVShowRecyclerAdapter(getContext(), latestList, new TVShowRecyclerAdapter.OnItemClickListner() {
            @Override
            public void OnItemClicked(int position) {

                TVShow show = latestList.get(position);
                callBack.onTVShowSelected(show);
            }
        },windowManager);
        latest.setAdapter(adapterL);
        latest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        latest.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        latest.setItemAnimator(new DefaultItemAnimator());
        return  view;

    }

    private void fetchOnAir() {
        Call<TVShowResponse> call = APIClient.getInstance().getMovieApi().getOn_The_Air("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                TVShowResponse response1= response.body();
                ArrayList<TVShow> showList= response1.getTvShows();
                if(showList!=null){
                    latestList.clear();
                    latestList.addAll(showList);
                    adapterL.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
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
                    popularList.clear();
                    popularList.addAll(showList);
                    adapterP.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
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
                    airingtodayList.clear();
                    airingtodayList.addAll(showList);
                    adapterAT.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }





    private void fetchTopRatedMovies() {
        Call<TVShowResponse> call = APIClient.getInstance().getMovieApi().getTopRatedTV("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                TVShowResponse response1= response.body();
                ArrayList<TVShow> showList= response1.getTvShows();
                if(showList!=null){
                    topratedList.clear();
                    topratedList.addAll(showList);
                    adapterTR.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }





}
