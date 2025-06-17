package com.tonala.mabeth.ui.Recursos.Computacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tonala.mabeth.ui.Recursos.Computacion.RecCompu.CActivity;
import com.tonala.mabeth.ui.Recursos.Computacion.RecCompu.CmasActivity;
import com.tonala.mabeth.ui.Recursos.Computacion.RecCompu.ControlActivity;
import com.tonala.mabeth.ui.Recursos.Computacion.RecCompu.CsharpActivity;
import com.tonala.mabeth.ui.Recursos.Computacion.RecCompu.CssActivity;
import com.tonala.mabeth.ui.Recursos.Computacion.RecCompu.DatosActivity;
import com.tonala.mabeth.ui.Recursos.Computacion.RecCompu.DiagraActivity;
import com.tonala.mabeth.ui.Recursos.Computacion.RecCompu.JavaActivity;
import com.tonala.mabeth.ui.Recursos.Computacion.RecCompu.NumeActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.Recursos.Computacion.RecCompu.htmlActivity;
import com.tonala.mabeth.ui.Recursos.Electronica.ElectroActivity;

public class CompActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private View cmas, c, csharp, html, css, java, control, datos, nume, diagra, electronica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_comp);

        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            cmas = (View) findViewById(R.id.viewRes1);
            c = (View) findViewById(R.id.viewRes2);
            html= (View) findViewById(R.id.viewRes3);
            css = (View) findViewById(R.id.viewRes5);
            java = (View) findViewById(R.id.viewRes6);
            csharp = (View) findViewById(R.id.viewRes7);
            control = (View) findViewById(R.id.viewRes9);
            datos = (View) findViewById(R.id.viewRes8);
            nume = (View) findViewById(R.id.viewRes11);
            diagra = (View) findViewById(R.id.viewRes12);
            electronica = (View) findViewById(R.id.viewResElec);
        }

        electronica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ElectroActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CmasActivity.class);
                startActivity(intent);
                finish();
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CActivity.class);
                startActivity(intent);
                finish();
            }
        });

        csharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CsharpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        html.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), htmlActivity.class);
                startActivity(intent);
                finish();
            }
        });

        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JavaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        css.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CssActivity.class);
                startActivity(intent);
                finish();
            }
        });

        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ControlActivity.class);
                startActivity(intent);
                finish();
            }
        });

        datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DatosActivity.class);
                startActivity(intent);
                finish();
            }
        });

        nume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NumeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        diagra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiagraActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}