package com.nimsdev.servicerunner;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

public class myPlayService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {

    public MediaPlayer mp = new MediaPlayer();
    private String sntAudioLink;
    private String mainMediaLink = "http://artdevsign.com/music/";
    //private String mainMediaLink = "C:\Users\jugld\OneDrive\Music";

    @Override
    public void onCreate() {
        mp.setOnCompletionListener(this);
        mp.setOnErrorListener(this);
        mp.setOnPreparedListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnSeekCompleteListener(this);
        mp.setOnInfoListener(this);
        mp.reset();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sntAudioLink = intent.getExtras().getString("sentAudioLink");
        mp.reset();

        //if(!mp.isPlaying()) {
            try {
               // Toast.makeText(getApplicationContext(), "source was " + mainMediaLink + sntAudioLink,
               //         Toast.LENGTH_LONG).show();
                mp.setDataSource(mainMediaLink + sntAudioLink);

                mp.prepareAsync();

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        //}
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopMedia();
        stopSelf();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        switch(what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this, "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra,
                        Toast.LENGTH_LONG).show();
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(this, "MEDIA ERROR SERVER DIED " + extra,
                        Toast.LENGTH_LONG).show();
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this, "MEDIA ERROR UNKNOWN " + extra,
                        Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        playMedia();
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }

    public void playMedia() {
        if (!mp.isPlaying()) {
            Toast.makeText(getApplicationContext(), "START",
                    Toast.LENGTH_LONG).show();

            mp.start();
        }
    }

    public void stopMedia() {
        Toast.makeText(getApplicationContext(), "STOP",
                Toast.LENGTH_LONG).show();
        if (mp == null) return;
        if (mp.isPlaying()) {

            mp.stop();
        }
    }
}
