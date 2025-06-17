package com.tonala.mabeth.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;

public class Registrarse extends AppCompatActivity {


    private EditText txtNombre, txtMail, txtPass, txtPassconf;
    private Button btnSiguiente;

    public static String name = "";
    public static String mail = "";
    public static String pass = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        }else{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtNombre = (EditText) findViewById(R.id.txtNombreUsu);
        txtMail = (EditText) findViewById(R.id.txtMail);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtPassconf = (EditText) findViewById(R.id.txtPassConf);
        btnSiguiente = (Button) findViewById(R.id.btnSig);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtNombre.getText().toString();
                mail = txtMail.getText().toString();
                if(txtPass.getText().toString().equals(txtPassconf.getText().toString())){
                    pass = txtPass.getText().toString();
                }else{
                    Toast.makeText(Registrarse.this, "Contraseñas diferentes", Toast.LENGTH_SHORT).show();
                }

                if(!name.isEmpty() && !mail.isEmpty() && !pass.isEmpty()){
                    if(pass.length() >= 6){
                        startActivity(new Intent(Registrarse.this, Registrarse2.class));
                        finish();
                    }else{
                        Toast.makeText(Registrarse.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Registrarse.this, "Complete los campos solicitados", Toast.LENGTH_SHORT).show();
                }
            }
        });}

    }

}