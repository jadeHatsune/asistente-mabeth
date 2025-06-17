package com.tonala.mabeth.ui.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.MenuSlideActivity;
import com.tonala.mabeth.ui.Principal.PrincipalFragment;

public class EditarAparienciaActivity extends AppCompatActivity {

    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_apariencia);


        guardar = (Button) findViewById(R.id.btnsetGuardar2);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });


        final Switch swi = findViewById(R.id.switchTema);
        swi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrincipalFragment xdMenu = new PrincipalFragment();
                if (swi.isChecked()){

                }else{

                }
            }

        });

    }

    private void guardarCambios(){
        Toast.makeText(getApplicationContext(), "Ajustes guardados con éxito", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
        finish();

    }

}