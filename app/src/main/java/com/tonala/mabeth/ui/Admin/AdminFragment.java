package com.tonala.mabeth.ui.Admin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.adapters.ForosAdapter;
import com.tonala.mabeth.entities.Foros;

import java.util.ArrayList;
import java.util.List;

public class AdminFragment extends Fragment {

    private ImageView imagenForo;
    private TextView lblimagen;
    private EditText txtCuerpoForo, txtTituloForo;
    private RecyclerView listadoForos;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(getActivity(), errorActivity.class));
            getActivity().finish();
        } else {
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            listadoForos = (RecyclerView) view.findViewById(R.id.ViewForosLista);
            listadoForos.setLayoutManager(new LinearLayoutManager(getActivity()));
            ObtnerForos();
        }
        return view;
    }

    private void ObtnerForos(){
        List<Foros> listaForos = new ArrayList<>();
        mDatabase.child("Foros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Foros foros = null;
                    listaForos.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        foros = new Foros();
                        String Creador = ds.child("Creador").getValue().toString();
                        String Titulo = ds.child("Titulo").getValue().toString();
                        String Cuerpo = ds.child("Cuerpo").getValue().toString();
                        String Tema = ds.child("Tema").getValue().toString();
                        String Imagen = ds.child("URL Imagen").getValue().toString();
                        String IDForo = ds.child("ID foro").getValue().toString();

                        foros.setTitulo(Titulo);
                        foros.setCreador(Creador);
                        foros.setCuerpo(Cuerpo);
                        foros.setTema(Tema);
                        foros.setURLimagen(Imagen);
                        foros.setIDForo(IDForo);
                        listaForos.add(foros);
                    }
                    ForosAdapter adapter = new ForosAdapter(listaForos);
                    listadoForos.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}