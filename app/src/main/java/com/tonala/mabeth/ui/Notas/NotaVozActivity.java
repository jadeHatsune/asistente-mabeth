package com.tonala.mabeth.ui.Notas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.datatransport.runtime.BuildConfig;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.MenuSlideActivity;

import java.io.File;
import java.io.IOException;

public class NotaVozActivity extends AppCompatActivity {

    private MediaRecorder grabacion;
    private EditText titulo;
    private String output;
    private Button regresar;
    private int xdgatitomiau= MenuSlideActivity.variableinex7;
    private Button play, detener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_voz);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        }else{
            regresar = (Button) findViewById(R.id.btnGRegresar);
            titulo = (EditText) findViewById(R.id.txtTituloNotavoz);
            play = (Button) findViewById(R.id.btnPlay);
            detener = (Button) findViewById(R.id.btnStop);

            play.setEnabled(false);
            detener.setEnabled(false);

            if(xdgatitomiau==0){
            switch(getFirstTimeRun()) {
                case 0:
                    showSuccesDialog();
                    MenuSlideActivity.variableinex7 = 1;
                    break;
                default:
                    MenuSlideActivity.variableinex7 = 1;
                    break;
            }}

            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat .requestPermissions(NotaVozActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
            }
            regresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), ListaNotasVozActivity.class));
                    finish();
                }
            });
        }
    }

    @SuppressLint("WrongConstant")
    public void grabar(View view){
        if(titulo.getText().toString().isEmpty()){
            Toast.makeText(this, "Inserte un titulo", Toast.LENGTH_SHORT).show();
        }else{
            play.setEnabled(true);
            detener.setEnabled(true);
            String titulonota = titulo.getText().toString();
            output = getFilesDir().
                getAbsolutePath() +  File.separator + "Notas" + File.separator + "NotasVoz" + File.separator + "/"+titulonota+".mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            grabacion.setOutputFile(output);
            try {
                grabacion.prepare();
                grabacion.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "La grabación comenzó", Toast.LENGTH_LONG).show();
        }
    }

    private int getFirstTimeRun() {
        SharedPreferences sp = getSharedPreferences("MYAPP", 0);
        int result, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0; else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        xdgatitomiau++;
        return result;
    }

    private void showSuccesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NotaVozActivity.this);
        View view = LayoutInflater.from(NotaVozActivity.this).inflate(R.layout.layout_dialog12, (ConstraintLayout)findViewById(R.id.layoutDialogContainer12));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showSuccesDialog2();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showSuccesDialog2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NotaVozActivity.this);
        View view = LayoutInflater.from(NotaVozActivity.this).inflate(R.layout.layout_dialog13, (ConstraintLayout)findViewById(R.id.layoutDialogContainer13));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public void detener(View view) {
        if (grabacion != null) {
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            Toast.makeText(getApplicationContext(), "El audio  grabado con éxito", Toast.LENGTH_LONG).show();
        }
    }
    public void reproducir(View view) {
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
