package com.tonala.mabeth.ui.Notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.MenuSlideActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaNotasVozActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static Object[] array;
    public static List<String> mLista = new ArrayList<>();
    public static List<String> mid = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private ListView mListView;
    private FloatingActionButton fab, fab2;
    public static int sel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas_voz);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        } else {
            mListView = (ListView) findViewById(R.id.lista2);
            mListView.setOnItemClickListener(this);
            fab = findViewById(R.id.fab2);
            fab2 = findViewById(R.id.fabr);
            String path = getFilesDir().
                    getAbsolutePath() +  File.separator + "Notas" + File.separator + "NotasVoz" + File.separator;

            File carpeta = new File(path);
            if(!carpeta.exists()){
                carpeta.mkdirs();
            }
                String[] mLista = carpeta.list();
                mid = Arrays.asList(mLista);

                array = mid.toArray();


                mAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, mLista);
                mListView.setAdapter(mAdapter);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotaVozActivity.class));
                finish();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sel = position;
        Intent intent = new Intent(getApplicationContext(), LeerNotaVozActivity.class);
        startActivity(intent);
        finish();
    }
}