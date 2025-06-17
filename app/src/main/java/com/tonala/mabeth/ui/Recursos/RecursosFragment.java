package com.tonala.mabeth.ui.Recursos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
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
import com.tonala.mabeth.BuildConfig;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.Gestor.GestorFragment;
import com.tonala.mabeth.ui.MenuSlideActivity;
import com.tonala.mabeth.ui.Recursos.Calidad.CalidadActivity;
import com.tonala.mabeth.ui.Recursos.Computacion.CompActivity;
import com.tonala.mabeth.ui.Recursos.Quimica.QuimActivity;

import java.util.Random;

public class RecursosFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView lblNombre, txtProgra, txtQuimica, txtCalidad, txtRes;
    private ImageView imgP;
    private LottieAnimationView imgRes;
    private ImageView computo, quimica, calidad;
    private View view2, view3, view4;
    private int val;
    public static int variable;
    public static int variable2;

    private int chquim2, chcomp2, chcali2;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recursos, container, false);
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(getActivity(), errorActivity.class));
            getActivity().finish();
        } else {
            Boolean verdad = new Boolean(true);



            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            lblNombre = (TextView) view.findViewById(R.id.txtNombreUsuRec);
            txtProgra = (TextView) view.findViewById(R.id.txtProgra);
            txtQuimica = (TextView) view.findViewById(R.id.txtQuimica);
            txtCalidad = (TextView) view.findViewById(R.id.txtCalidad);
            imgP = (ImageView) view.findViewById(R.id.imgPerfilRec);
            computo = (ImageView) view.findViewById(R.id.imgCompu);
            quimica = (ImageView) view.findViewById(R.id.imgQuimica);
            calidad = (ImageView) view.findViewById(R.id.imgCalidad);
            view2 = (View) view.findViewById(R.id.view2);
            view3 = (View) view.findViewById(R.id.view3);
            view4 = (View) view.findViewById(R.id.view4);
            txtRes = (TextView) view.findViewById(R.id.textViewRes);


            getInf();

            switch(getFirstTimeRun()){
                case 0:
                    showSuccesDialog(view);
                    break;
                default:
                    variable=1;
                    variable2=1;
                    break;
            }


            final String texto[] = {
                    "El trabajo duro vence al talento cuando el talento no trabaja duro.",
                    "Tus talentos y habilidades irán mejorando con el tiempo, pero para eso has de empezar.",
                    "La verdadera educación consiste en obtener lo mejor de uno mismo.",
                    "Aprender sin pensar es inútil. Pensar sin aprender, peligroso.",
                    "La calidad no es un acto, sino un hábito.",
                    "La mejor forma de predecir el futuro es crearlo.",
                    "A quien teme preguntar le da vergüenza aprender.",
                    "Nadie que haya dado lo mejor de sí mismo lo ha lamentado nunca."
            };

            if (verdad == true){
                Random random = new Random();
                int textos = random.nextInt(8);

                txtRes.setText(texto[textos]);
            }

            imgRes = (LottieAnimationView) view.findViewById(R.id.imageViewRes);
            Random random = new Random();
            val = random.nextInt(6 + 1) + 1;

            switch (val){
                case 1:
                    imgRes.setAnimation(R.raw.educacion1);
                    break;
                case 2:
                    imgRes.setAnimation(R.raw.educacion2);
                    break;
                case 3:
                    imgRes.setAnimation(R.raw.educacion4);
                    break;
                case 4:
                    imgRes.setAnimation(R.raw.educacion5);
                    break;
                case 5:
                    imgRes.setAnimation(R.raw.educacion6);
                    break;
                case 6:
                    imgRes.setAnimation(R.raw.educacion7);
                    break;
                case 7:
                    imgRes.setAnimation(R.raw.educacion8);
                    break;

            }

            quimica.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            txtQuimica.setVisibility(View.VISIBLE);
            quimica.setEnabled(true);

            computo.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
            txtProgra.setVisibility(View.VISIBLE);
            computo.setEnabled(true);

            calidad.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            txtCalidad.setVisibility(View.VISIBLE);
            calidad.setEnabled(true);

            computo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CompActivity.class);
                    startActivity(intent);
                }
            });

            quimica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), QuimActivity.class);
                    startActivity(intent);
                }
            });

            calidad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CalidadActivity.class);
                    startActivity(intent);
                }
            });
        }
        return view;

    }


    private int getFirstTimeRun() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int result, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0; else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        variable=0;
        variable2=0;
        return result;
    }

    private void showSuccesDialog(View xd){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog14, (ConstraintLayout)xd.findViewById(R.id.layoutDialogContainer14));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showSuccesDialog2(xd);
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showSuccesDialog2(View xd){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog15, (ConstraintLayout)xd.findViewById(R.id.layoutDialogContainer15));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog15).setOnClickListener(new View.OnClickListener() {
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

    public void getInf() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            startActivity(new Intent(getActivity(), errorActivity.class));
            getActivity().finish();
        }else{
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("Name").getValue().toString();
                    String URLFP = dataSnapshot.child("URL FP").getValue().toString();
                    String chquim = dataSnapshot.child("rec_quim").getValue().toString();
                    String chcomp = dataSnapshot.child("rec_comp").getValue().toString();
                    String chcali = dataSnapshot.child("rec_cali").getValue().toString();

                    chquim2 = Integer.parseInt(chquim);
                    chcomp2 = Integer.parseInt(chcomp);
                    chcali2 = Integer.parseInt(chcali);

                    if(chquim2 == 0){
                        quimica.setVisibility(View.INVISIBLE);
                        view3.setVisibility(View.INVISIBLE);
                        txtQuimica.setVisibility(View.INVISIBLE);
                        quimica.setEnabled(false);
                    }
                    if(chcomp2 == 0){
                        computo.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.INVISIBLE);
                        txtProgra.setVisibility(View.INVISIBLE);
                        computo.setEnabled(false);
                    }
                    if(chcali2 == 0){
                        calidad.setVisibility(View.INVISIBLE);
                        view4.setVisibility(View.INVISIBLE);
                        txtCalidad.setVisibility(View.INVISIBLE);
                        calidad.setEnabled(false);
                    }


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





}