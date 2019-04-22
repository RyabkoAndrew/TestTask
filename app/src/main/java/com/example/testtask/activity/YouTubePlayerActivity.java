package com.example.testtask.activity;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Toast;


import com.example.testtask.Config;
import com.example.testtask.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_REQUEST = 1;
    public static final String VIDEO_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String EXTRA_REPLY =
            "com.example.testtask.REPLY";

    String videoId;

    private YouTubePlayerView mYoutubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_player);

        mYoutubePlayer = (YouTubePlayerView)findViewById(R.id.youtube_view);
        mYoutubePlayer.initialize(Config.YOUTUBE_API_KEY,this);

        videoId = getIntent().getStringExtra(VIDEO_ID);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this,RECOVERY_REQUEST).show();
        }else {
            String error = String.format(getString(R.string.player_error),youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST){
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY,this);
        }
    }
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return mYoutubePlayer;
    }

//    @Override
//    public void recyclerViewPosition(Example data) {
//        Intent replyIntent = new Intent();
//        String title = data.getSnippet().getTitle();
//        String description = data.getSnippet().getDescription();
//        Bundle bundle = new Bundle();
//       bundle.putString(KEY_TITLE,title);
//       bundle.putString(KEY_DESCRIPTION,description);
//        replyIntent.putExtra(EXTRA_REPLY, bundle);
//    }
}
