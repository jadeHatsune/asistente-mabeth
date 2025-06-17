package com.tonala.mabeth.Inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.tonala.mabeth.IniciarSes.IniciarSes;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.MenuSlideActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

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
            setContentView(R.layout.activity_main);
            mAuth = FirebaseAuth.getInstance();
        }
    }

    public void IniciarSes(View view){
        Intent intent = new Intent(getApplicationContext(), IniciarSes.class);
        startActivity(intent);
    }

    public void Regis(View view){
        Intent intent = new Intent(getApplicationContext(), iniciara.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        }else{
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            String id = mAuth.getUid();
            if (!id.isEmpty()){
                startActivity(new Intent(MainActivity.this, MenuSlideActivity.class));
                finish();
            }
        }}
    }
}