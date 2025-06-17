package com.tonala.mabeth.ui.Recursos.Computacion.RecCompu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.Recursos.Computacion.CompActivity;

public class CmasActivity extends AppCompatActivity {

    private LottieAnimationView animationView;

    private View volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmas);

        volver = (View) findViewById(R.id.viewResVolver);

        animationView = (LottieAnimationView) findViewById(R.id.animPrinci5);
        animationView.setAnimation(R.raw.programacion3);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), CompActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}