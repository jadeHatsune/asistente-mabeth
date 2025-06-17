package com.tonala.mabeth.IniciarSes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.MenuSlideActivity;

public class RecuperarActivity extends AppCompatActivity {

    private EditText gmail;
    private String correo;

    private TextView recuperar, cancelar;
    private ProgressDialog progress;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        gmail = findViewById(R.id.messagedrecuperar);
        cancelar = findViewById(R.id.txtCancel22);
        recuperar = findViewById(R.id.txtRecuperar);

        auth = FirebaseAuth.getInstance();


        progress = new ProgressDialog(this);

        getRecuperar();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), IniciarSes.class));
                finish();
            }
        });
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
                    Toast.makeText(getApplicationContext(), "Revisa tu correo para recuperar la contraseña", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RecuperarActivity.this, IniciarSes.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "No se pudo enviar el correo", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RecuperarActivity.this, IniciarSes.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}