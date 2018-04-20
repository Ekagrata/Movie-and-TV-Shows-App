package com.example.rajneesh.movieapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAJNEESH on 18-04-2018.
 */
@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieList(ArrayList<Movie> movie);

    @Query("Select* from Movie Where isPopular =1")
    List<Movie> getPopularMovies();

    @Query("Select* from Movie Where isTopRated =1 Order By vote_average DESC")
    List<Movie> getTopRatedMovies();

    @Query("Select* from Movie Where isUpcoming =1")
    List<Movie> getUpcoming();

    @Query("Select* from Movie Where isNowPlaying =1")
    List<Movie> getNowPlaying();

    @Query("Select* from Movie Where isFavourite =1")
    List<Movie> getFavourite();







}
