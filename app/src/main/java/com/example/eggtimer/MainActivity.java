package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTv;
    SeekBar mySeekbar;
    CountDownTimer countDownTimer;
    boolean counterIsActive = false;
    Button goButton;

    public void resetTimer(){
        timerTv.setText("00:30");
        mySeekbar.setProgress(30);
        goButton.setText("GO !");
        mySeekbar.setEnabled(true);
        counterIsActive = false;
        countDownTimer.cancel();
    }
    public void startTimer(View view) {

        if(counterIsActive){
            resetTimer();
        }
        else{
            counterIsActive = true;
            mySeekbar.setEnabled(false);
            goButton.setText("STOP !");

             countDownTimer = new CountDownTimer(mySeekbar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updatetimer((int)millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }


    }

    public void updatetimer(int secondsLeft) {
        int minutes = secondsLeft/60;
        int seconds = secondsLeft - (minutes*60);
        String secondsString = Integer.toString(seconds);
        if(secondsString.equals("0")){
            secondsString = "00";
        }
        if(Integer.parseInt(secondsString) < 10){
            secondsString = "0"+seconds;
        }
        timerTv.setText(Integer.toString(minutes) + ":" + secondsString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySeekbar = findViewById(R.id.mySeekBar);
        timerTv = findViewById(R.id.timerTv);
        goButton = findViewById(R.id.goButton);

        mySeekbar.setMax(600);
        mySeekbar.setProgress(30);

        mySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updatetimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
