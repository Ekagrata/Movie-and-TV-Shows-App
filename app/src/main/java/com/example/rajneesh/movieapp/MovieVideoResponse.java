package com.example.rajneesh.movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAJNEESH on 11-04-2018.
 */

public class MovieVideoResponse {

    class Trailer{
        @SerializedName("id")
        String idtrailer;
        String key;
        String name;
        String type;

        public String getIdtrailer() {
            return idtrailer;
        }

        public void setIdtrailer(String idtrailer) {
            this.idtrailer = idtrailer;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    int id;
    @SerializedName("results")
    ArrayList<Trailer> trailers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
    }
}
