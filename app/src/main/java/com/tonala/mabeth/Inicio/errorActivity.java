package com.tonala.mabeth.Inicio;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.tonala.mabeth.R;

import java.util.Random;

public class errorActivity extends AppCompatActivity {

    private LottieAnimationView animationView;
    private int val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        animationView = (LottieAnimationView) findViewById(R.id.AnimError);
        Random random = new Random();
        val = random.nextInt(27 + 1) + 1;

        switch (val){
            case 1:
                animationView.setAnimation(R.raw.error11);
                break;
            case 2:
                animationView.setAnimation(R.raw.error22);
                break;
            case 3:
                animationView.setAnimation(R.raw.error3);
                break;
            case 4:
                animationView.setAnimation(R.raw.error4);
                break;
            case 5:
                animationView.setAnimation(R.raw.error5);
                break;
            case 6:
                animationView.setAnimation(R.raw.error6);
                break;
            case 7:
                animationView.setAnimation(R.raw.error7);
                break;
            case 8:
                animationView.setAnimation(R.raw.error8);
                break;
            case 9:
                animationView.setAnimation(R.raw.error9);
                break;
            case 10:
                animationView.setAnimation(R.raw.error10);
                break;
            case 11:
                animationView.setAnimation(R.raw.error11);
                break;
            case 12:
                animationView.setAnimation(R.raw.error12);
                break;
            case 13:
                animationView.setAnimation(R.raw.error13);
                break;
            case 14:
                animationView.setAnimation(R.raw.error14);
                break;
            case 15:
                animationView.setAnimation(R.raw.error15);
                break;
            case 16:
                animationView.setAnimation(R.raw.error16);
                break;
            case 17:
                animationView.setAnimation(R.raw.error17);
                break;
            case 18:
                animationView.setAnimation(R.raw.error18);
                break;
            case 19:
                animationView.setAnimation(R.raw.error19);
                break;
            case 20:
                animationView.setAnimation(R.raw.error20);
                break;
            case 21:
                animationView.setAnimation(R.raw.error21);
                break;
            case 22:
                animationView.setAnimation(R.raw.error22);
                break;
            case 23:
                animationView.setAnimation(R.raw.error23);
                break;
            case 24:
                animationView.setAnimation(R.raw.error24);
                break;
            case 25:
                animationView.setAnimation(R.raw.error25);
                break;
            case 26:
                animationView.setAnimation(R.raw.error26);
                break;
            case 27:
                animationView.setAnimation(R.raw.error27);
                break;
            case 28:
                animationView.setAnimation(R.raw.error28);
                break;

        }
    }
}