package com.tonala.mabeth.ui.Foros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.adapters.ForosAdapter;
import com.tonala.mabeth.adapters.RespuestasAdapter;
import com.tonala.mabeth.entities.Foros;
import com.tonala.mabeth.entities.Respuestas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RespForoActivity extends AppCompatActivity {

    private String id;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String name;
    private int idResp;

    private TextView viewCreador, viewCuerpo, viewnumLikes, viewTema, viewTitulo;
    private ImageView viewImagen;
    private ImageButton viewEnviar;
    private RecyclerView listadoRespuestas;
    private EditText viewRespuesta;
    private LottieAnimationView viewLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resp_foro);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        } else {
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();

            viewCreador = findViewById(R.id.CreadorForoResp);
            viewCuerpo = findViewById(R.id.CuerpoForoResp);
            viewImagen = findViewById(R.id.ImagenForoResp);
            viewLike = findViewById(R.id.animLikeForoResp);
            viewnumLikes = findViewById(R.id.lblNumLikeForoResp);
            viewTema = findViewById(R.id.TemaForoResp);
            viewTitulo = findViewById(R.id.TituloForoResp);
            viewEnviar = findViewById(R.id.btnEnviarResp);
            viewRespuesta = findViewById(R.id.txtResponderForo);
            listadoRespuestas = (RecyclerView) findViewById(R.id.ViewRespForosLista);
            listadoRespuestas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            viewLike.setAnimation(R.raw.likeforos);
            viewLike.setScale(200);
            viewLike.setSpeed(2);
            viewLike.setMinAndMaxFrame(0, 99);

            if(savedInstanceState == null){
                Bundle extras = getIntent().getExtras();
                if (extras == null){
                    id = String.valueOf(null);
                }else{
                    id = extras.getString("ID");
                }
            }else{
                id = (String) savedInstanceState.getSerializable("ID");
            }

            getInf();
            getInfForo();
            ObtenerNumLikes();
            verificarLike();
            ObtenerRespuestas();

            viewEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    if(!isConnected){
                        startActivity(new Intent(getApplicationContext(), errorActivity.class));
                        finish();
                    }else{
                        EnviarRespuesta();
                        ObtenerRespuestas();
                        incrementarRespuestas();
                    }
                }
            });

            viewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!viewLike.isAnimating()){
                        if(viewLike.getFrame() == 99){
                            decrementarLikes();
                            viewLike.cancelAnimation();
                            viewLike.setFrame(0);
                        }else{
                            incrementarLikes();
                            viewLike.playAnimation();
                        }
                    }
                }
            });
        }
    }

    public void EnviarRespuesta(){
        if(!viewRespuesta.getText().toString().equals("")){
            String iduser = mAuth.getCurrentUser().getUid();
            String texto = viewRespuesta.getText().toString();

            Map<String, Object> map = new HashMap<>();
            map.put("Respuesta", texto);
            map.put("Creador", name);
            map.put("UID creador", iduser);
            map.put("ID foro", id);
            map.put("ID resp", String.valueOf(idResp));
            map.put("likes", "0");
            mDatabase.child("Respuestas").child(String.valueOf(idResp)).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task4) {
                    if (task4.isSuccessful()) {
                        viewRespuesta.setText("");
                        Toast.makeText(getApplicationContext(), "Respuesta enviada", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error al enviar respuesta", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }else{
            Toast.makeText(getApplicationContext(), "Rellene los campos necesarios", Toast.LENGTH_SHORT).show();
        }
    }

    public void ObtenerRespuestas(){
        List<Respuestas> listaRespuestas = new ArrayList<>();
        mDatabase.child("Respuestas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Respuestas respuestas;
                    listaRespuestas.clear();
                    idResp = 1;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String IDForo = ds.child("ID foro").getValue().toString();
                        idResp++;
                        if(IDForo.equals(id)){
                            String Respuesta = ds.child("Respuesta").getValue().toString();
                            String Creador = ds.child("Creador").getValue().toString();
                            String IDCreador = ds.child("UID creador").getValue().toString();
                            String IDRespuesta = ds.child("ID resp").getValue().toString();

                            respuestas = new Respuestas();
                            respuestas.setRespuesta(Respuesta);
                            respuestas.setCreador(Creador);
                            respuestas.setIDCreador(IDCreador);
                            respuestas.setIDForo(IDForo);
                            respuestas.setIDRespuesta(IDRespuesta);
                            listaRespuestas.add(respuestas);
                        }
                    }
                    RespuestasAdapter adapter = new RespuestasAdapter(listaRespuestas);
                    listadoRespuestas.setAdapter(adapter);
                }else{
                    idResp = 1;
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void getInfForo(){
        mDatabase.child("Foros").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String Creador = dataSnapshot.child("Creador").getValue().toString();
                    String Titulo = dataSnapshot.child("Titulo").getValue().toString();
                    String Cuerpo = dataSnapshot.child("Cuerpo").getValue().toString();
                    String Tema = dataSnapshot.child("Tema").getValue().toString();
                    String Imagen = dataSnapshot.child("URL Imagen").getValue().toString();

                    viewCreador.setText("Creado por: " + Creador);
                    viewTitulo.setText(Titulo);
                    viewCuerpo.setText(Cuerpo);
                    viewTema.setText(Tema);

                    if(Imagen.equals("null")){
                        viewImagen.setVisibility(View.GONE);
                    }else{
                        Picasso.get()
                                .load(Imagen)
                                .error(R.mipmap.ic_launcher_round)
                                .into(viewImagen);
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void ObtenerNumLikes(){
        mDatabase.child("Foros").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lik = dataSnapshot.child("likes").getValue().toString();
                viewnumLikes.setText(lik);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void incrementarLikes(){
        String iduser = mAuth.getCurrentUser().getUid();
        mDatabase.child("Likes").child(id).child(iduser).setValue(1);
        mDatabase.child("Foros").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lik = dataSnapshot.child("likes").getValue().toString();
                int news = Integer.parseInt(lik) + 1;
                mDatabase.child("Foros").child(id).child("likes").setValue(String.valueOf(news));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        ObtenerNumLikes();
    }

    public void decrementarLikes(){
        String iduser = mAuth.getCurrentUser().getUid();
        mDatabase.child("Likes").child(id).child(iduser).removeValue();
        mDatabase.child("Foros").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lik = dataSnapshot.child("likes").getValue().toString();
                int news = Integer.parseInt(lik) - 1;
                mDatabase.child("Foros").child(id).child("likes").setValue(String.valueOf(news));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        ObtenerNumLikes();
    }

    public void verificarLike(){
        String iduser = mAuth.getCurrentUser().getUid();
        mDatabase.child("Likes").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child(iduser).exists()){
                        viewLike.setFrame(99);
                    }else{
                        viewLike.setFrame(0);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getInf(){
        String iduser = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(iduser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    name = dataSnapshot.child("Name").getValue().toString();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void incrementarRespuestas(){
        mDatabase.child("Foros").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String Respuestas = dataSnapshot.child("respuestas").getValue().toString();
                    int numRespuestas = Integer.parseInt(Respuestas) + 1;
                    mDatabase.child("Foros").child(id).child("respuestas").setValue(numRespuestas);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}