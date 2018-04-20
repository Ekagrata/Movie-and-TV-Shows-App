package com.example.rajneesh.movieapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by RAJNEESH on 05-04-2018.
 */
@Entity
public class Movie implements Serializable {
    @PrimaryKey
    int id;
    String title;
    String poster_path;
    String backdrop_path;
    float vote_average;
    int isFavourite;
    int isUpcoming;
    int isNowPlaying;
    int isTopRated;
    int isPopular;

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    public int getIsUpcoming() {
        return isUpcoming;
    }

    public void setIsUpcoming(int isUpcoming) {
        this.isUpcoming = isUpcoming;
    }

    public int getIsNowPlaying() {
        return isNowPlaying;
    }

    public void setIsNowPlaying(int isNowPlaying) {
        this.isNowPlaying = isNowPlaying;
    }

    public int getIsTopRated() {
        return isTopRated;
    }

    public void setIsTopRated(int isTopRated) {
        this.isTopRated = isTopRated;
    }

    public int getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(int isPopular) {
        this.isPopular = isPopular;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


}
