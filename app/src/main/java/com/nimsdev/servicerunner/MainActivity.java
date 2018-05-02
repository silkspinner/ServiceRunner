package com.nimsdev.servicerunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;





public class MainActivity extends Activity {

    Intent serviceIntent;
    private Button playStopButton;
    private boolean musicPlaying = false;
    String audioLink = "wonderful-world.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playStopButton=(Button)findViewById(R.id.playStopButton);

        try {
            serviceIntent = new Intent(this, myPlayService.class);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    e.getClass().getName() + " " + e.getMessage(),
                    Toast.LENGTH_LONG).show();

        }
    }

    public void clickPlayStopButton(View view) {
        if(!musicPlaying) {
            playStopButton.setBackgroundResource(R.mipmap.stop);
            playAudio();
        } else {
            playStopButton.setBackgroundResource(R.mipmap.play);
            stopMyPlayService();
        }
    }

    private void playAudio() {
        serviceIntent.putExtra("sentAudioLink", audioLink);
        try {
            startService(serviceIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    e.getClass().getName() + " " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        musicPlaying = true;
    }

    private void stopMyPlayService() {
        try {
            stopService(serviceIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    e.getClass().getName() + " " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        musicPlaying = false;
    }
}
