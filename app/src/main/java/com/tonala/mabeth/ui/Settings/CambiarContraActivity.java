package com.tonala.mabeth.ui.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.tonala.mabeth.IniciarSes.IniciarSes;
import com.tonala.mabeth.IniciarSes.RecuperarActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.MenuSlideActivity;

public class CambiarContraActivity extends AppCompatActivity {

    private EditText gmail;
    private String correo;

    private TextView recuperar;
    private ProgressDialog progress;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contra);
        gmail = findViewById(R.id.messagedcambiarcontra);
        recuperar = findViewById(R.id.btnsetGuardarCon);

        auth = FirebaseAuth.getInstance();


        progress = new ProgressDialog(this);

        getRecuperar();

    }

    private void getRecuperar(){
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = gmail.getText().toString().trim();
                if (!correo.isEmpty()){
                    progress.setMessage("Espera un momento");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                    getEnviar();
                }else{
                    Toast.makeText(getApplicationContext(), "Ingresa un correo electrónico", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void getEnviar(){
        auth.setLanguageCode("es");
        auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Revisa tu correo para cambiar tu contraseña", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CambiarContraActivity.this, MenuSlideActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "No se pudo enviar el correo", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CambiarContraActivity.this, CambiarContraActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}