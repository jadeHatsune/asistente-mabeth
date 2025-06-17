package com.tonala.mabeth.ui.Recursos.Calidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.Recursos.Calidad.RecCalidad.Brainstorming;
import com.tonala.mabeth.ui.Recursos.Calidad.RecCalidad.Calidad;
import com.tonala.mabeth.ui.Recursos.Calidad.RecCalidad.CausaEfec;
import com.tonala.mabeth.ui.Recursos.Calidad.RecCalidad.Gestion;
import com.tonala.mabeth.ui.Recursos.Calidad.RecCalidad.Metrologia;
import com.tonala.mabeth.ui.Recursos.Calidad.RecCalidad.Normalizacion;
import com.tonala.mabeth.ui.Recursos.Calidad.RecCalidad.Pareto;

public class CalidadActivity extends AppCompatActivity {

    private View brain, concep, caefec, gestion, metro, norma, pareto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_calidad);

        brain = (View) findViewById(R.id.viewRes1);
        concep = (View) findViewById(R.id.viewRes2);
        caefec= (View) findViewById(R.id.viewRes3);
        gestion = (View) findViewById(R.id.viewRes5);
        metro = (View) findViewById(R.id.viewRes6);
        norma = (View) findViewById(R.id.viewRes7);
        pareto = (View) findViewById(R.id.viewRes9);



        brain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Brainstorming.class);
                startActivity(intent);
                finish();
            }
        });

        concep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Calidad.class);
                startActivity(intent);
                finish();
            }
        });

        caefec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CausaEfec.class);
                startActivity(intent);
                finish();
            }
        });

        gestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Gestion.class);
                startActivity(intent);
                finish();
            }
        });

        metro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Metrologia.class);
                startActivity(intent);
                finish();
            }
        });

        norma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Normalizacion.class);
                startActivity(intent);
                finish();
            }
        });

        pareto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Pareto.class);
                startActivity(intent);
                finish();
            }
        });
    }
}