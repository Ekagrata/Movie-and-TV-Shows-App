package com.example.rajneesh.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity {
    YouTubePlayerView youtubePlayer;
    YouTubePlayer.OnInitializedListener listner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        youtubePlayer = findViewById(R.id.youtubePlayer);
        Intent intent= getIntent();
        Bundle bundle= intent.getExtras();
        final String trailkey= bundle.getString("trailkey");




        listner= new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                     youTubePlayer.loadVideo(trailkey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youtubePlayer.initialize("AIzaSyBH_jBViR8kVtmwqWf4XN9d1WUnSSyPOaI",listner);

//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                youtubePlayer.initialize("AIzaSyBH_jBViR8kVtmwqWf4XN9d1WUnSSyPOaI",listner);
//            }
//        });



    }
}
