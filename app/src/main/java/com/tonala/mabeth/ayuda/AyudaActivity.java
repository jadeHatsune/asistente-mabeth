package com.tonala.mabeth.ayuda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.Settings.SettingsActivity;

public class AyudaActivity extends AppCompatActivity {

    private ImageView atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        atras = (ImageView) findViewById(R.id.atrasset);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                finish();
            }
        });

        findViewById(R.id.viewCalendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CalendarioAyudaActivity.class));
                finish();
            }
        });

        findViewById(R.id.viewGestor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GestorAyudaActivity.class));
                finish();
            }
        });

        findViewById(R.id.viewNotas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotasAyudaActivity.class));
                finish();
            }
        });

        findViewById(R.id.viewForos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForosAyudaActivity.class));
                finish();
            }
        });

        findViewById(R.id.viewRecursos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RecursosAyudaActivity.class));
                finish();
            }
        });

    }
}