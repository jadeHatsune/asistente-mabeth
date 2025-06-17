package com.tonala.mabeth.ayuda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.Notas.NotasTextoActivity;
import com.tonala.mabeth.ui.Settings.SettingsActivity;

public class NotasAyuda2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_ayuda2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.textViewSettingsRegresar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AyudaActivity.class));
                finish();
            }
        });

        findViewById(R.id.textViewSettingsSiguienteNot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotasAyuda3Activity.class));
                finish();
            }
        });

        findViewById(R.id.textViewSettingsProbar2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotasTextoActivity.class));
                finish();
            }
        });
    }
}