package com.example.haouesla.assynctask;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button launch = (Button) findViewById(R.id.button_launch);
        Button pause = (Button) findViewById(R.id.button_pause);
        Button stop = (Button) findViewById(R.id.button_stop);
        SeekBar progressBar = (SeekBar) findViewById(R.id.progressBar);
        mediaPlayer = mediaPlayer.create(this, R.raw.mymusic);
        mediaPlayer.setLooping(false);


        // définir la taille max de la prodressBar en fonction de la durée de la musique
        progressBar.setMax(mediaPlayer.getDuration());

        // lancer la musique
        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                BigCompute bc = new BigCompute(getApplicationContext(), (SeekBar)findViewById(R.id.progressBar), mediaPlayer);
                bc.execute();
            }
        });

        // mettre pause
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

        // arreter la musique
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
            }
        });

        // Changer la position de la progress bar manuellement en synchro avec l'avaancement de la musique
        try {
            progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }


        // Controler le son depuis la seekBar dédiée
        try
        {
            SeekBar volume = (SeekBar)findViewById(R.id.volumeSeekBar);
            final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
