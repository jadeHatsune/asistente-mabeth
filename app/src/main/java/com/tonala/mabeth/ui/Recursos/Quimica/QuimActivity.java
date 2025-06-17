package com.tonala.mabeth.ui.Recursos.Quimica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.Recursos.Computacion.RecCompu.CmasActivity;
import com.tonala.mabeth.ui.Recursos.Quimica.RecQuim.AlimentosActivity;
import com.tonala.mabeth.ui.Recursos.Quimica.RecQuim.CodexAlimentariusActivity;
import com.tonala.mabeth.ui.Recursos.Quimica.RecQuim.ElementosTablaActivity;
import com.tonala.mabeth.ui.Recursos.Quimica.RecQuim.FDActivity;
import com.tonala.mabeth.ui.Recursos.Quimica.RecQuim.FarmacopeaActivity;
import com.tonala.mabeth.ui.Recursos.Quimica.RecQuim.FarmacosActivity;
import com.tonala.mabeth.ui.Recursos.Quimica.RecQuim.IndustrialActivity;
import com.tonala.mabeth.ui.Recursos.Quimica.RecQuim.NomActivity;

public class QuimActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private View farmaco, farmacopea, fda, nom, industrial, alimentos, codex, propiedades, elementos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_quim);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        farmaco = (View) findViewById(R.id.viewRes1);
        alimentos = (View) findViewById(R.id.viewRes2);
        industrial= (View) findViewById(R.id.viewRes3);
        elementos = (View) findViewById(R.id.viewRes5);
        nom = (View) findViewById(R.id.viewRes6);
        fda = (View) findViewById(R.id.viewRes7);
        codex = (View) findViewById(R.id.viewRes9);
        farmacopea = (View) findViewById(R.id.viewRes8);

        farmaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FarmacosActivity.class);
                startActivity(intent);
                finish();
            }
        });

        alimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AlimentosActivity.class);
                startActivity(intent);
                finish();
            }
        });

        industrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IndustrialActivity.class);
                startActivity(intent);
                finish();
            }
        });

        elementos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ElementosTablaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        nom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NomActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FDActivity.class);
                startActivity(intent);
                finish();
            }
        });

        codex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CodexAlimentariusActivity.class);
                startActivity(intent);
                finish();
            }
        });

        farmacopea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FarmacopeaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}