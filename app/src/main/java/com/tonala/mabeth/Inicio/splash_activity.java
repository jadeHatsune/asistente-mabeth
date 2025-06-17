package com.tonala.mabeth.Inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.tonala.mabeth.R;

import java.util.Random;

public class splash_activity extends AppCompatActivity {


    private final int tm = 2000;


    private LottieAnimationView animationView;
    private int val;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        animationView = (LottieAnimationView) findViewById(R.id.AnimCarg);
        Random random = new Random();
        val = random.nextInt(2 + 1) + 1;

        switch (val){
            case 1:
                animationView.setAnimation(R.raw.carga1);
                break;
            case 2:
                animationView.setAnimation(R.raw.carga2);
                break;
            case 3:
                animationView.setAnimation(R.raw.carga3);
                break;

        }

        new Handler().postDelayed(new Runnable(){
            public void run (){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, tm);





    }
}