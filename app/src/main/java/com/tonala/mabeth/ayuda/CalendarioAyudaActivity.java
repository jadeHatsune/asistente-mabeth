package com.tonala.mabeth.ayuda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.tonala.mabeth.R;
import com.tonala.mabeth.ayuda.CalendarioAyuda2Activity;
import com.tonala.mabeth.ui.Settings.SettingsActivity;

public class CalendarioAyudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendario_ayuda);

        findViewById(R.id.textViewSetRegresar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AyudaActivity.class));
                finish();
            }
        });

        findViewById(R.id.textViewSettingsSig).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CalendarioAyuda2Activity.class));
                finish();
            }
        });
    }


}