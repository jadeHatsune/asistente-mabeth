package com.tonala.mabeth.ui.Recursos.Quimica.RecQuim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.Recursos.Quimica.QuimActivity;

public class CodexAlimentariusActivity extends AppCompatActivity {

    private View volver;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codex_alimentarius);

        volver = (View) findViewById(R.id.viewResVolver);

        animationView = (LottieAnimationView) findViewById(R.id.animPrinci31);
        animationView.setAnimation(R.raw.bookloading);

        animationView = (LottieAnimationView) findViewById(R.id.animPrinci32);
        animationView.setAnimation(R.raw.doggo);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuimActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}