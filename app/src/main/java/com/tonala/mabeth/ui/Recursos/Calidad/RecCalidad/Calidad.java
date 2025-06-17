package com.tonala.mabeth.ui.Recursos.Calidad.RecCalidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.tonala.mabeth.R;

public class Calidad extends AppCompatActivity {

    private View volver;
    private LottieAnimationView animationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calidad);

        animationView = (LottieAnimationView) findViewById(R.id.animPrinci42);
        animationView.setAnimation(R.raw.work);

        animationView = (LottieAnimationView) findViewById(R.id.animPrinci43);
        animationView.setAnimation(R.raw.productividad);

        volver = (View) findViewById(R.id.viewResVolver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Calidad.class);
                startActivity(intent);
                finish();
            }
        });
    }
}