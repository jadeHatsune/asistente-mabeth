package com.tonala.mabeth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tonala.mabeth.ui.MenuSlideActivity;

public class EliminarForoActivity extends AppCompatActivity {

    private String id;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String name;
    private int idResp;

    private TextView viewCreador, viewCuerpo, viewnumLikes, viewTema, viewTitulo;
    private ImageView viewImagen;
    private Button viewEliminar;
    private RecyclerView listadoRespuestas;
    private EditText viewRespuesta;
    private LottieAnimationView viewLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_foro);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        viewCreador = findViewById(R.id.CreadorForoResp2);
        viewCuerpo = findViewById(R.id.CuerpoForoResp2);
        viewImagen = findViewById(R.id.ImagenForoResp2);
        viewTema = findViewById(R.id.TemaForoResp2);
        viewTitulo = findViewById(R.id.TituloForoResp2);
        viewEliminar = findViewById(R.id.btnEliminarForo2);

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

        getInfForo();

        viewEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Foros").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        mDatabase.child("Likes").child(id).removeValue();
                        Toast.makeText(getApplicationContext(), "Foro eliminado con exito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MenuSlideActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
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

}