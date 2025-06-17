package com.tonala.mabeth.ui.Recursos.Computacion.RecCompu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.Recursos.Computacion.CompActivity;

public class CActivity extends AppCompatActivity {

    private View volver;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private LottieAnimationView animationView;
    private ImageView imgAnimacionC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cactivity);

        animationView = (LottieAnimationView) findViewById(R.id.animPrinci2);
        animationView.setAnimation(R.raw.programar);

        animationView = (LottieAnimationView) findViewById(R.id.animPrinci3);
        animationView.setAnimation(R.raw.orange2);

        animationView = (LottieAnimationView) findViewById(R.id.animPrinci4);
        animationView.setAnimation(R.raw.orange);

        volver = (View) findViewById(R.id.viewResVolver);

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