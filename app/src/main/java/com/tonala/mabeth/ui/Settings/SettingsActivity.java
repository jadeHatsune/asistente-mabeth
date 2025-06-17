package com.tonala.mabeth.ui.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tonala.mabeth.ayuda.AyudaActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.MenuSlideActivity;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private CheckBox chQuim, chComp, chCali;
    private Button guardar;

    private String chkquim;
    private String chkcomp;
    private LinearLayout ayudas, perfil, apariencia, contra;
    private TextView lblNombreUsu;
    private ImageView imgP;
    private String chkcali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lblNombreUsu = (TextView) findViewById(R.id.txtNombreUsuSet);
        ayudas = (LinearLayout) findViewById(R.id.layoutAyuda);
        perfil = (LinearLayout) findViewById(R.id.layoutnombre);
        apariencia = (LinearLayout) findViewById(R.id.layoutapariencia);
        imgP = (ImageView) findViewById(R.id.imgPerfilUsuSet);
        contra = (LinearLayout) findViewById(R.id.layoutcontra);

        chQuim = (CheckBox) findViewById(R.id.chQuimset);

        chComp = (CheckBox) findViewById(R.id.chCompset);
        chCali = (CheckBox) findViewById(R.id.chCaliset);
        guardar = (Button) findViewById(R.id.btnsetGuardar);

        getInf();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });

        ayudas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AyudaActivity.class));
                finish();
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditarPerfilActivity.class));
                finish();
            }
        });

        apariencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditarAparienciaActivity.class));
                finish();
            }
        });

        contra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CambiarContraActivity.class));
                finish();
            }
        });



    }

    private void guardarCambios(){
        Toast.makeText(getApplicationContext(), "Ajustes guardados con éxito", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
        finish();

    }

    private void getInf(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("Name").getValue().toString();
                    String URLFP = dataSnapshot.child("URL FP").getValue().toString();


                    lblNombreUsu.setText(name);
                    Picasso.get()
                            .load(URLFP)
                            .error(R.mipmap.ic_launcher_round)
                            .into(imgP);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}