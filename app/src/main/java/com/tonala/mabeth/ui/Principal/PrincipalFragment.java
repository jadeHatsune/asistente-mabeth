package com.tonala.mabeth.ui.Principal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.adapters.ListaEventosAdapter;
import com.tonala.mabeth.database.DbEvents;
import com.tonala.mabeth.entities.Eventos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class PrincipalFragment extends Fragment{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private RecyclerView listaEventos;
    private ArrayList<Eventos> listaArrayEventos;

    private TextView lblNombre, lblHorario;
    private ImageView imgP;
    private LottieAnimationView animationView;
    private int valnoche, valdia, valtarde, valmedio;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(getActivity(), errorActivity.class));
            getActivity().finish();
        } else {

            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            lblNombre = (TextView) view.findViewById(R.id.txtNombre1);
            imgP = (ImageView) view.findViewById(R.id.imgPerfil);
            lblHorario = (TextView) view.findViewById(R.id.txtBuendia);
            animationView = (LottieAnimationView) view.findViewById(R.id.animPrinci);

            getInf();
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            String greeting = "";
            Random random = new Random();

            if (hour >= 12 && hour < 17) {
                greeting = "Buenas tardes, haz tus deberes!";
                valtarde = random.nextInt(7 + 1) + 1;

                switch (valtarde) {
                    case 1:
                        animationView.setAnimation(R.raw.tarde1);
                        break;
                    case 2:
                        animationView.setAnimation(R.raw.tarde2);
                        break;
                    case 3:
                        animationView.setAnimation(R.raw.tarde3);
                        break;
                    case 4:
                        animationView.setAnimation(R.raw.tarde4);
                        break;
                    case 5:
                        animationView.setAnimation(R.raw.tarde5);
                        break;
                    case 6:
                        animationView.setAnimation(R.raw.tarde6);
                        break;
                    case 7:
                        animationView.setAnimation(R.raw.tarde7);
                        break;
                    case 8:
                        animationView.setAnimation(R.raw.tarde8);
                        break;


                }
            } else if (hour >= 17 && hour < 21) {
                greeting = "Buenas tardes, termina con tus deberes";
                valmedio = random.nextInt(7 + 1) + 1;

                switch (valmedio) {
                    case 1:
                        animationView.setAnimation(R.raw.medio1);
                        break;
                    case 2:
                        animationView.setAnimation(R.raw.medio2);
                        break;
                    case 3:
                        animationView.setAnimation(R.raw.medio3);
                        break;
                    case 4:
                        animationView.setAnimation(R.raw.medio5);
                        break;
                    case 5:
                        animationView.setAnimation(R.raw.medio6);
                        break;
                    case 6:
                        animationView.setAnimation(R.raw.medio7);
                        break;
                    case 7:
                        animationView.setAnimation(R.raw.medio8);
                        break;
                    case 8:
                        animationView.setAnimation(R.raw.medio9);
                        break;

                }
            } else if (hour >= 21 && hour < 24) {
                greeting = "Buena noche, duerme bien!";
                valnoche = random.nextInt(10 + 1) + 1;

                switch (valnoche) {
                    case 1:
                        animationView.setAnimation(R.raw.dormir1);
                        break;
                    case 2:
                        animationView.setAnimation(R.raw.dormir2);
                        break;
                    case 3:
                        animationView.setAnimation(R.raw.dormir3);
                        break;
                    case 4:
                        animationView.setAnimation(R.raw.dormir4);
                        break;
                    case 5:
                        animationView.setAnimation(R.raw.dormir5);
                        break;
                    case 6:
                        animationView.setAnimation(R.raw.dormir6);
                        break;
                    case 7:
                        animationView.setAnimation(R.raw.dormir7);
                        break;
                    case 8:
                        animationView.setAnimation(R.raw.dormir8);
                        break;
                    case 9:
                        animationView.setAnimation(R.raw.dormir9);
                        break;
                    case 10:
                        animationView.setAnimation(R.raw.dormir10);
                        break;
                    case 11:
                        animationView.setAnimation(R.raw.dormir11);
                        break;

                }
            } else {
                greeting = "Ten un buen día!";
                valdia = random.nextInt(8 + 1) + 1;

                switch (valdia) {
                    case 1:
                        animationView.setAnimation(R.raw.dia1);
                        break;
                    case 2:
                        animationView.setAnimation(R.raw.dia2);
                        break;
                    case 3:
                        animationView.setAnimation(R.raw.dia3);
                        break;
                    case 4:
                        animationView.setAnimation(R.raw.dia4);
                        break;
                    case 5:
                        animationView.setAnimation(R.raw.dia5);
                        break;
                    case 6:
                        animationView.setAnimation(R.raw.dia6);
                        break;
                    case 7:
                        animationView.setAnimation(R.raw.dia7);
                        break;
                    case 8:
                        animationView.setAnimation(R.raw.dia8);
                        break;
                    case 9:
                        animationView.setAnimation(R.raw.dia9);
                        break;

                }

            }
            lblHorario.setText(greeting);
        }
        listaEventos = view.findViewById(R.id.listaInicio);
        listaEventos.setLayoutManager(new LinearLayoutManager(getActivity()));

        DbEvents dbEvents = new DbEvents(getActivity());

        listaArrayEventos = new ArrayList<>();

        ListaEventosAdapter adapter = new ListaEventosAdapter(dbEvents.mostrarEventosNoExpirados(System.currentTimeMillis()));
        listaEventos.setAdapter(adapter);
        return view;

    }

    public void setDayNight(int mode){
        if (mode==0){
            ConstraintLayout bgElement = (ConstraintLayout) getView().findViewById(R.id.constraintLayoutxddelmenuahorita);
            ConstraintLayout bgElement2 = (ConstraintLayout) getView().findViewById(R.id.layoutprincipal);
            TextView textView = (TextView) getView().findViewById(R.id.textViewSettingsAc);

            textView.setTextColor(Color.WHITE);
            bgElement2.setBackgroundColor(R.drawable.colornegro);
            bgElement.setBackgroundColor(R.drawable.fondo2);

        }else{
            ConstraintLayout bgElement = (ConstraintLayout) getView().findViewById(R.id.constraintLayoutxddelmenuahorita);
            ConstraintLayout bgElement2 = (ConstraintLayout)getView().findViewById(R.id.layoutprincipal);
            TextView textView = (TextView) getView().findViewById(R.id.textViewSettingsAc);

            textView.setTextColor(Color.parseColor("#888890"));
            bgElement2.setBackgroundColor(R.drawable.colornegro);
            bgElement.setBackgroundColor(R.drawable.fondo2);
        }
    }

    public void getInf(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String name = dataSnapshot.child("Name").getValue().toString();
                    String URLFP = dataSnapshot.child("URL FP").getValue().toString();


                    lblNombre.setText(name);
                    Picasso.get()
                            .load(URLFP)
                            .error(R.mipmap.ic_launcher_round)
                            .into(imgP);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }}