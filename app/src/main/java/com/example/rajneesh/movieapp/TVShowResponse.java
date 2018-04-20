package com.example.rajneesh.movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAJNEESH on 15-04-2018.
 */

public class TVShowResponse {
    int page;
    int total_pages;

    @SerializedName("results")
    ArrayList<TVShow> tvShows;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<TVShow> getTvShows() {
        return tvShows;
    }

    public void setTvShows(ArrayList<TVShow> tvShows) {
        this.tvShows = tvShows;
    }
}
