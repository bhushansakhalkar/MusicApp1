package com.example.musicapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayMusic extends AppCompatActivity {
    private SeekBar seekBarMusic;
    private Button buttonPlay;
    private Button buttonPause;
    private Button buttonStop;
    private MediaPlayer mediaPlayer;
    private TextView textViewTime;
    private String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

// Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
// Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

// Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        }   else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

// return timer string
        return finalTimerString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int id = intent.getIntExtra("res",0);

        buttonPause = findViewById(R.id.button_pause);
        buttonPlay = findViewById(R.id.button_play);
        buttonStop = findViewById(R.id.button_stop);
        seekBarMusic = findViewById(R.id.seek_bar_music);
        textViewTime = findViewById(R.id.text_view_time);



        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                int id = intent.getIntExtra("res",0);
                if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                mediaPlayer = MediaPlayer.create(PlayMusic.this,id);
                mediaPlayer.start();
                seekBarMusic.setMax(mediaPlayer.getDuration());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mediaPlayer.isPlaying()){
                            seekBarMusic.setProgress(mediaPlayer.getCurrentPosition());
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            textViewTime.setText(milliSecondsToTimer(seekBarMusic.getProgress()));

                        }

                    }
                }).start();

            }
        });
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                int id = intent.getIntExtra("res",0);
                if(mediaPlayer!=null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    seekBarMusic.setProgress(mediaPlayer.getCurrentPosition());
                    textViewTime.setText(milliSecondsToTimer(seekBarMusic.getProgress()));
                }else{
                    mediaPlayer.start();
                    seekBarMusic.setProgress(mediaPlayer.getCurrentPosition());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (mediaPlayer.isPlaying()){
                                seekBarMusic.setProgress(mediaPlayer.getCurrentPosition());
                                textViewTime.setText(milliSecondsToTimer(seekBarMusic.getProgress()));
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }).start();

                }


            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mediaPlayer.stop();
                    seekBarMusic.setProgress(0);
                    textViewTime.setText(milliSecondsToTimer(seekBarMusic.getProgress()));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}
