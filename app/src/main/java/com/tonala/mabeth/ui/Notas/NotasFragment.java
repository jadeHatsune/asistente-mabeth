package com.tonala.mabeth.ui.Notas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.Notas.ListaNotasTextoActivity;
import com.tonala.mabeth.ui.Notas.ListaNotasVozActivity;

public class NotasFragment extends Fragment{

    private Button voz, texto;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notas, container, false);
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(getActivity(), errorActivity.class));
            getActivity().finish();
        } else {


            voz = (Button) view.findViewById(R.id.btnNotaVoz);
            texto = (Button) view.findViewById(R.id.btnNotaTexto);



            voz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ListaNotasVozActivity.class));
                    getActivity().finish();
                }
            });
            texto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ListaNotasTextoActivity.class));
                    getActivity().finish();
                }
            });
        }
        return view;
    }


}