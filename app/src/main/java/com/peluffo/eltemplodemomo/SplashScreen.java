package com.peluffo.eltemplodemomo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends Activity {
    private ProgressBar progressBar;
    private Timer timer;
    private int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView logoS = findViewById(R.id.ivLogoS);
        ImageView iconoS = findViewById(R.id.ivIconoS);
        progressBar = findViewById(R.id.pbSplash);
        logoS.setImageResource(R.drawable.logo);
        iconoS.setImageResource(R.drawable.icono);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(i<100){
                    runOnUiThread(() -> {

                    });
                    progressBar.setProgress(i);
                    i++;
                }else{
                    timer.cancel();
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 0, 50);
    }
}