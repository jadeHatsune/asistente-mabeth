package com.tonala.mabeth.ui.Recursos.Electronica.RecElectro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.Recursos.Electronica.ElectroActivity;

public class SenElecActivity extends AppCompatActivity {

    private View volver;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sen_elec);

        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        volver = (View) findViewById(R.id.viewResVolver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ElectroActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}