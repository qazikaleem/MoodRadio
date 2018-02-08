package com.IQz.eqazkal.moodradio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> songsList;
    boolean flag = true;
    MediaPlayer mediaPlayer;
    AdView myAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Variable Definition - Image, Drop-Down & Text for Clicking

        final ImageView discoPlay;
        final ImageView romanticPlay;
        final ImageView breakupPlay;
        final ImageView oldPlay;
        final ImageView retroPlay;
        final ImageView newsongsPlay;

        TextView tvDisco;
        TextView tvRomantic;
        TextView tvBreakup;
        TextView tvOld;

        discoPlay = findViewById(R.id.discoPlay);
        romanticPlay = findViewById(R.id.romanticPlay);
        breakupPlay = findViewById(R.id.breakupPlay);
        oldPlay = findViewById(R.id.oldPlay);
        retroPlay = findViewById(R.id.retroPlay);
        newsongsPlay = findViewById(R.id.newsongsPlay);

        tvDisco = findViewById(R.id.tvStation1);
        tvRomantic = findViewById(R.id.tvStation2);
        tvBreakup = findViewById(R.id.tvStation3);
        tvOld = findViewById(R.id.tvStation4);

        mediaPlayer = new MediaPlayer();

//AD DISPLAY COnfig//
        MobileAds.initialize(this,"ca-app-pub-3649661874695489~2571112628");
        myAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);

//On CLick Play Images
        discoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("value of I", discoPlay.getDrawable().toString()+"##########");
                //set radio image & provide URL
                if (checkFlag()) {
                    enableAll();
                } else {
                    String link = "http://69.175.94.98:8146/;stream.mp3";
                    //--- Pop Songs (Radio Garam Masala)//
                    discoPlay.setImageResource(R.drawable.stop);
                    //call mediaPlayer function and pass songlist to play
                    playSong(link);

                }
            }
        });
        romanticPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFlag()) {
                    enableAll();
                } else {
                    //set radio image & provide URL
                    String link = "http://144.217.203.184:9056/;stream.mp3";
                    //---- Romantic (gupshupcorner.com)//
                    romanticPlay.setImageResource(R.drawable.stop);
                    //call mediaPlayer function and pass songlist to play
                    playSong(link);

                }

            }

        });
        breakupPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFlag()) {
                    enableAll();
                } else {
                    //set radio image & provide URL
                    String link = "http://s0.desimusicmix.com:8012/;stream.mp3";
                    //--- Desi Mix (Desimusicmix.com)//
                    breakupPlay.setImageResource(R.drawable.stop);
                    //call mediaPlayer function and pass songlist to play
                    playSong(link);

                }
            }
        });

        oldPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFlag()) {
                    enableAll();
                } else {
                    //set radio image & provide URL
                    String link = "http://64.71.79.181:5124/;stream.mp3";
                    //--- Retro (retrobollywood.jimbo.com)//
                    oldPlay.setImageResource(R.drawable.stop);
                    //call mediaPlayer function and pass songlist to play
                    playSong(link);

                }
            }
        });

        retroPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFlag()) {
                    enableAll();
                } else {
                    //set radio image & provide URL
                    String link = "http://198.178.123.14:8216/;stream.mp3";
                    //--- Retro (retrobollywood.jimbo.com)//
                    retroPlay.setImageResource(R.drawable.stop);
                    //call mediaPlayer function and pass songlist to play
                    playSong(link);
                }


            }
        });

        newsongsPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFlag()) {
                    enableAll();
                } else {
                    //set radio image & provide URL
                    String link = "http://50.7.68.251:7064/;stream.mp3";
                    //--- Very New Songs (www.radiohsl.com)//
                    newsongsPlay.setImageResource(R.drawable.stop);
                    //call mediaPlayer function and pass songlist to play
                    playSong(link);
                }
            }
        });

// OnClick Definitions and call Intent (Next Page) by passing URL. OnClick on the Text.

        tvDisco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                //url = "http://chawkicentre.tk/discoSongs/";
                //sendIntent(url);
                //Log.d ("On Click", url);
            }
        });

        tvRomantic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                //url = "http://chawkicentre.tk/romanticSongs/";
                //sendIntent(url);
                //Log.d ("On Click", url);
            }
        });

        tvBreakup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                //url = "http://chawkicentre.tk/breakupSongs/";
                //sendIntent(url);
                //Log.d ("On Click", url);
            }
        });

        tvOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                //url = "http://chawkicentre.tk/oldSongs/";
                //sendIntent(url);
                //Log.d ("On Click", url);
            }
        });
    }

    @Override
    public void onBackPressed() {
        enableAll();
        toastStop();
        finish();
    }

///////////////////////////////////////////////////////////////////////////
/////////////////// START OF MEDIA PLAYER DEFINITIONS /////////////////////
//////////////////////////////////////////////////////////////////////////
    public void playSong(final String url) {
        //Log.d("URL", url);
        //String url1 = url + songsList.get(i);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"Loading", "Radio Loading Please wait...");
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Radio Started !", Toast.LENGTH_SHORT).show();
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    playSong(url);
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/////////////// END of MEDIA PLAYER DEFINITION /////////////////////
////////////////////////////////////////////////////////////////////

    // New Intent page called Display used and passed url variable
    public void sendIntent(String url) {
        //Log.d ("Under Intent", url);
        Intent i = new Intent(this, Display.class);
        i.putExtra("url", url);
        startActivity(i);
    }

    //Stop present playing mediaPlayer and restore drawable images
    public void enableAll() {
        mediaPlayer.stop();
        mediaPlayer.release();
        ImageView discoPlay = findViewById(R.id.discoPlay);
        ImageView romanticPlay = findViewById(R.id.romanticPlay);
        ImageView breakupPlay = findViewById(R.id.breakupPlay);
        ImageView oldPlay = findViewById(R.id.oldPlay);
        ImageView retroPlay = findViewById(R.id.retroPlay);
        ImageView newsongsPlay = findViewById(R.id.newsongsPlay);

        discoPlay.setImageResource(R.drawable.play_green);
        romanticPlay.setImageResource(R.drawable.play_blue);
        breakupPlay.setImageResource(R.drawable.play_orange);
        oldPlay.setImageResource(R.drawable.play_purple);
        retroPlay.setImageResource(R.drawable.play_yello);
        newsongsPlay.setImageResource(R.drawable.play_pink);

    }

    public void toastStop(){
        Toast.makeText(getApplicationContext(), "Radio Stopped !", Toast.LENGTH_SHORT).show();
    }

    public boolean checkFlag(){
        ImageView discoPlay = findViewById(R.id.discoPlay);
        ImageView romanticPlay = findViewById(R.id.romanticPlay);
        ImageView breakupPlay = findViewById(R.id.breakupPlay);
        ImageView oldPlay = findViewById(R.id.oldPlay);
        ImageView retroPlay = findViewById(R.id.retroPlay);
        ImageView newsongsPlay = findViewById(R.id.newsongsPlay);

        return ((discoPlay.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.stop).getConstantState()))
                | (romanticPlay.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.stop).getConstantState()))
                | (breakupPlay.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.stop).getConstantState()))
                | (oldPlay.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.stop).getConstantState()))
                | (retroPlay.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.stop).getConstantState()))
                | (newsongsPlay.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.stop).getConstantState()))
                );
    }

}
