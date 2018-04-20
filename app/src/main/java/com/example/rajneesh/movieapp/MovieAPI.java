package com.example.rajneesh.movieapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by RAJNEESH on 06-04-2018.
 */

public interface MovieAPI {
    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String key);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRated(@Query("api_key") String key);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcoming(@Query("api_key") String key);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowplaying(@Query("api_key") String key);


    @GET("movie/{movie_id}")
    Call<MovieDetailResponse> getMoviedetail(@Path("movie_id") String id, @Query("api_key") String key);

    @GET("movie/{movie_id}/credits")
    Call<CastResponse> getCast(@Path("movie_id") String id,@Query("api_key") String key);

    @GET("person/{person_id}")
    Call<CastDetailResponse> getCastDetail(@Path("person_id") String id,@Query("api_key") String key);

    @GET("person/{person_id}/movie_credits")
    Call<CastMovieCreditResponse> getCastMovieCredits(@Path("person_id") String id,@Query("api_key") String key);

    @GET("movie/{movie_id}/similar")
    Call<MovieResponse> getSimilarMovies(@Path("movie_id") String id,@Query("api_key") String key);

    @GET("movie/{movie_id}/videos")
    Call<MovieVideoResponse> getTrailers(@Path("movie_id") String id,@Query("api_key") String key);

    @GET("tv/airing_today")
    Call<TVShowResponse> getAiring_Today(@Query("api_key") String key);

    @GET("tv/on_the_air")
    Call<TVShowResponse> getOn_The_Air(@Query("api_key") String key);

    @GET("tv/popular")
    Call<TVShowResponse> getPopular(@Query("api_key") String key);

    @GET("tv/top_rated")
    Call<TVShowResponse> getTopRatedTV(@Query("api_key") String key);

    @GET("tv/{tv_id}/videos")
    Call<MovieVideoResponse> getTVTrailers(@Path("tv_id") String id,@Query("api_key") String key);

    @GET("tv/{tv_id}/similar")
    Call<TVShowResponse> getSimilarShows(@Path("tv_id") String id,@Query("api_key") String key);

    @GET("tv/{tv_id}/credits")
    Call<CastResponse> getTVCast(@Path("tv_id") String id,@Query("api_key") String key);

    @GET("tv/{tv_id}")
    Call<TVShowDetailResponse> getShowdetail(@Path("tv_id") String id, @Query("api_key") String key);










}
