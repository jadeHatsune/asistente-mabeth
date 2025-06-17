package com.tonala.mabeth.ui.Notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.tonala.mabeth.R;

import java.io.File;
import java.io.IOException;

public class LeerNotaVozActivity extends AppCompatActivity {

    private final Object[] array = ListaNotasVozActivity.array;
    private final int seleccion = ListaNotasVozActivity.sel;

    private Button eliminar;
    private String output;
    private Button regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_leer_nota_voz);
        eliminar = (Button) findViewById(R.id.btnEliminarNotaVoz);

        regresar = (Button) findViewById(R.id.btnGRegresar2);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                output = getFilesDir().
                        getAbsolutePath() +  File.separator + "Notas" + File.separator+ File.separator + "NotasVoz" + File.separator + "/";
                new File(output, (array[seleccion]).toString()).delete();
                Toast.makeText(getApplicationContext(), "Nota eliminada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ListaNotasVozActivity.class));
                finish();

            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListaNotasVozActivity.class));
                finish();
            }
        });

    }

    public void reproducir(View view) {
        output = getFilesDir().
                getAbsolutePath() +  File.separator + "Notas" + File.separator+File.separator + "NotasVoz" + File.separator + "/"+array[seleccion];
        MediaPlayer m = new MediaPlayer();
        try {
            m.setDataSource(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            m.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        m.start();
        Toast.makeText(getApplicationContext(), "reproducción de audio", Toast.LENGTH_LONG).show();
    }


}