package com.tonala.mabeth.ui.Gestor;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.adapters.ListaEventosAdapter;
import com.tonala.mabeth.database.DbEvents;
import com.tonala.mabeth.database.DbHelperEvents;
import com.tonala.mabeth.entities.Eventos;
import com.tonala.mabeth.ui.MenuSlideActivity;

import java.util.ArrayList;
import java.util.List;

public class GestorFragment extends Fragment {

    private TextView agregar;
    private RecyclerView listaEventos;
    private ImageView regresarxd;
    private String color;
    private ListaEventosAdapter adapter;
    private int dx = MenuSlideActivity.variablea;
    private List<Eventos> listaArrayEventos;
    private Spinner filtro, filtro23;


    public void onStart() {
        super.onStart();
        DbEvents dbEvents = new DbEvents(getActivity());
        DbHelperEvents dbHelper = new DbHelperEvents(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db != null){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.filtroAct, android.R.layout.simple_spinner_item);
            filtro.setAdapter(adapter2);

            filtro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch(position){
                        case 0:
                            adapter = new ListaEventosAdapter(dbEvents.mostrarEventos());
                            listaEventos.setAdapter(adapter);
                            break;
                        case 1:
                            adapter = new ListaEventosAdapter(dbEvents.mostrarEventosCompletados());
                            listaEventos.setAdapter(adapter);
                            break;
                        case 2:
                            adapter = new ListaEventosAdapter(dbEvents.mostrarEventosSinCompletar());
                            listaEventos.setAdapter(adapter);
                            break;
                        case 3:
                            adapter = new ListaEventosAdapter(dbEvents.mostrarEventosExpirados(System.currentTimeMillis()));
                            listaEventos.setAdapter(adapter);
                            break;
                        case 4:
                            adapter = new ListaEventosAdapter(dbEvents.mostrarEventosNoExpirados(System.currentTimeMillis()));
                            listaEventos.setAdapter(adapter);
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    filtro.setSelection(0);
                }
            });

            ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.filtroActCol, android.R.layout.simple_spinner_item);
            filtro23.setAdapter(adapter3);

            filtro23.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch(position){
                        case 0:
                            adapter = new ListaEventosAdapter(dbEvents.mostrarEventos());
                            listaEventos.setAdapter(adapter);
                            break;
                        case 1:
                            adapter = new ListaEventosAdapter(dbEvents.mostrarEventosMorado());
                            listaEventos.setAdapter(adapter);
                            break;
                        case 2:
                            adapter = new ListaEventosAdapter(dbEvents.mostrarEventosPurpura());
                            listaEventos.setAdapter(adapter);
                            break;
                        case 3:
                            adapter = new ListaEventosAdapter(dbEvents.mostrarEventosSalmon());
                            listaEventos.setAdapter(adapter);
                            break;
                        case 4:
                            adapter = new ListaEventosAdapter(dbEvents.mostrarEventosAzul());
                            listaEventos.setAdapter(adapter);
                            break;
                        case 5:
                            adapter = new ListaEventosAdapter(dbEvents.mostrarEventosRojo());
                            listaEventos.setAdapter(adapter);
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    filtro23.setSelection(0);
                }
            });
        }

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filtro.setSelection(0);
        filtro23.setSelection(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gestor, container, false);
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(getActivity(), errorActivity.class));
            getActivity().finish();
        } else {
            agregar = view.findViewById(R.id.btnCrearEvento);
            listaEventos = view.findViewById(R.id.listaEventos);
            listaEventos.setLayoutManager(new LinearLayoutManager(getActivity()));
            regresarxd = view.findViewById(R.id.btnGestorRegresarxd);
            filtro = view.findViewById(R.id.spFiltAct);
            filtro23 = view.findViewById(R.id.spFiltAct23);

            switch (dx) {
                case 0:
                    showSuccesDialog(view);
                    MenuSlideActivity.variablea = 1;
                    break;
                default:
                    break;
            }

            DbEvents dbEvents = new DbEvents(getActivity());
            adapter = new ListaEventosAdapter(dbEvents.mostrarEventos());


            regresarxd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), MenuSlideActivity.class));
                    getActivity().finish();
                }
            });



            agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),GestorNotyActivity.class));
                    getActivity().finish();
                }
            });

            EditText txtBuscarTarea = view.findViewById(R.id.txtBuscarTarea);
            txtBuscarTarea.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.cancelTimer();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    listaArrayEventos = new ArrayList<>();
                    listaArrayEventos = dbEvents.mostrarEventos();
                    if(listaArrayEventos.size() != 0){
                        adapter.searchActividades(s.toString());
                    }
                }
            });

        }

        return view;
    }

    private void showSuccesDialog(View xd){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog17, (ConstraintLayout)xd.findViewById(R.id.layoutDialogContainer17));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog17).setOnClickListener(new View.OnClickListener() {
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



}