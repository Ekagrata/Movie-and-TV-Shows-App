package com.example.rajneesh.movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAJNEESH on 06-04-2018.
 */

public class MovieResponse {
    int page;
    int total_pages;

    @SerializedName("results")
    ArrayList<Movie> movies;

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

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public MovieResponse(int page, int total_pages, ArrayList<Movie> movies) {

        this.page = page;
        this.total_pages = total_pages;
        this.movies = movies;
    }
}
