package com.tonala.mabeth.Inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.tonala.mabeth.IniciarSes.IniciarSes;
import com.tonala.mabeth.R;
import com.tonala.mabeth.Registro.Registrarse;

public class iniciara extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciara);
    }

    public void Siguiente(View view){
        Intent intent = new Intent(getApplicationContext(), Registrarse.class);
        startActivity(intent);
    }
}