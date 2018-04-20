package com.example.rajneesh.movieapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RAJNEESH on 06-04-2018.
 */

public class APIClient {
    MovieAPI movieAPI;
    static APIClient client;
    public MovieAPI getMovieApi() {
        return movieAPI;
    }




    private APIClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieAPI = retrofit.create(MovieAPI.class);
    }



    public static APIClient getInstance() {
        if(client == null){
            client = new APIClient();
        }
        return client;
    }
}
