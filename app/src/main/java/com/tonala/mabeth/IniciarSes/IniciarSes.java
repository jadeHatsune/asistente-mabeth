package com.tonala.mabeth.IniciarSes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tonala.mabeth.Inicio.MainActivity;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.Registro.Registrarse;
import com.tonala.mabeth.ui.MenuSlideActivity;

public class IniciarSes extends AppCompatActivity {


    private EditText txtEmail;
    private EditText txtPass;
    private Button btnIniciar;
    private EditText gmail;
    private String correo;
    private TextView recuperar;
    private ProgressDialog progress;
    FirebaseAuth auth;
    private TextView lblRegis, lblPassFor;

    private String email = "";
    private String pass = "";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_ses);
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        }else{

            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();

            lblPassFor = (TextView) findViewById(R.id.lblPassFor);
            txtEmail = (EditText) findViewById(R.id.txtEmailInic);
            txtPass = (EditText) findViewById(R.id.txtPassInic);
            gmail = findViewById(R.id.messagedrecuperar);
            recuperar = findViewById(R.id.txtRecuperar);

            auth = FirebaseAuth.getInstance();

            progress = new ProgressDialog(this);
            btnIniciar = (Button) findViewById(R.id.btnLogin);
            lblRegis = (TextView) findViewById(R.id.lbl2);

            btnIniciar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    email = txtEmail.getText().toString();
                    pass = txtPass.getText().toString();
                    if (!email.isEmpty() && !pass.isEmpty()){
                        loginUser();
                    }else {
                        Toast.makeText(IniciarSes.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            lblRegis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(IniciarSes.this, Registrarse.class));
                    finish();
                }
            });

            lblPassFor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(IniciarSes.this, RecuperarActivity.class));
                    finish();
                }
            });
        }
    }


    private void loginUser(){
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        }else{
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    mDatabase.child("Usuarios").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                if(user.isEmailVerified()){
                                    startActivity(new Intent(IniciarSes.this, MenuSlideActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(IniciarSes.this, "Por favor verifique su Correo electrónico", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                user.delete();
                                Toast.makeText(IniciarSes.this, "El usuario ha sido eliminado", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                }else{
                    Toast.makeText(IniciarSes.this, "Usuario no valido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }}

}