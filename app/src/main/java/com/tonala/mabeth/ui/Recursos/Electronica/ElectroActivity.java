package com.tonala.mabeth.ui.Recursos.Electronica;

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
import com.tonala.mabeth.ui.Recursos.Computacion.CompActivity;
import com.tonala.mabeth.ui.Recursos.Electronica.RecElectro.ConcepElecActivity;
import com.tonala.mabeth.ui.Recursos.Electronica.RecElectro.DispAnaloActivity;
import com.tonala.mabeth.ui.Recursos.Electronica.RecElectro.LeyOhmActivity;
import com.tonala.mabeth.ui.Recursos.Electronica.RecElectro.SenElecActivity;
import com.tonala.mabeth.ui.Recursos.Electronica.RecElectro.SisElecActivity;
import com.tonala.mabeth.ui.Recursos.Electronica.RecElectro.TensionActivity;
import com.tonala.mabeth.ui.Recursos.Electronica.RecElectro.UnidMedActivity;
import com.tonala.mabeth.ui.Recursos.Electronica.RecElectro.ValorFormOndaActivity;

public class ElectroActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private View concep, disp, ohm, sen, sis, tension, unid, resprogra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electro);

            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            concep = (View) findViewById(R.id.viewRes1);
            disp = (View) findViewById(R.id.viewRes2);
            ohm= (View) findViewById(R.id.viewRes3);
            sen = (View) findViewById(R.id.viewRes5);
            sis = (View) findViewById(R.id.viewRes6);
            tension = (View) findViewById(R.id.viewRes7);
            unid = (View) findViewById(R.id.viewRes9);
            resprogra = (View) findViewById(R.id.viewResProgra);


        concep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConcepElecActivity.class);
                startActivity(intent);
                finish();
            }
        });

        disp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DispAnaloActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ohm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeyOhmActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SenElecActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SisElecActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TensionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        unid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UnidMedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        resprogra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CompActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}