package com.example.rajneesh.movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAJNEESH on 07-04-2018.
 */

public class CastResponse {



    class Crew{
        String credit_id;
        String department;
        int gender;
        int id;
        String job;
        String name;
        String profile_path;
    }

    int id;
    @SerializedName("cast")
    ArrayList<cast> casts;
    @SerializedName("crew")
    ArrayList<Crew> crews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<cast> getCast() {
        return casts;
    }

    public void setCast(ArrayList<cast> casts) {
        this.casts = casts;
    }

    public ArrayList<Crew> getCrews() {
        return crews;
    }

    public void setCrews(ArrayList<Crew> crews) {
        this.crews = crews;
    }
}
