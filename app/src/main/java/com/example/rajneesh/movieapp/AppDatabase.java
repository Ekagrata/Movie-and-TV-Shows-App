package com.example.rajneesh.movieapp;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by RAJNEESH on 18-04-2018.
 */
@Database(entities = {Movie.class,TVShow.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private  static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){

        if(INSTANCE== null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"movieapp_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return  INSTANCE;
    }

    abstract  MovieDao getMoviesDao();

}
