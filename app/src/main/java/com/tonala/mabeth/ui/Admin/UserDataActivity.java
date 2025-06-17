package com.tonala.mabeth.ui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.MenuSlideActivity;


public class UserDataActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private final Object[] array = UsersFragment.array;
    private final int seleccion = UsersFragment.sel;
    private final String id = (String) array[seleccion];
    private int prio;
    private String link;

    private TextView lblNombreData, lblCorreoData;
    private ImageView imgPData;
    private Button btnElminar, btnAdm, btnAdm2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        }else{

        mDatabase = FirebaseDatabase.getInstance().getReference();

        lblNombreData = (TextView) findViewById(R.id.lblNombreData);
        lblCorreoData = (TextView) findViewById(R.id.lblCorreoData);
        btnElminar = (Button) findViewById(R.id.btnElminarUsu);
        btnAdm = (Button) findViewById(R.id.btnAdmin);
        btnAdm2 = (Button) findViewById(R.id.btnAdmin2);
        imgPData = (ImageView) findViewById(R.id.imgPerfilData);

        GetInf();

        btnAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(!isConnected){
                    startActivity(new Intent(getApplicationContext(), errorActivity.class));
                    finish();
                }else{
                HacerAdmin();}

            }
        });
        btnAdm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(!isConnected){
                    startActivity(new Intent(getApplicationContext(), errorActivity.class));
                    finish();
                }else{
                QuitarAdmin();}

            }
        });

        btnElminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(!isConnected){
                    startActivity(new Intent(getApplicationContext(), errorActivity.class));
                    finish();
                }else{
                    Eliminar();}
            }
        });}

    }

    public void Eliminar(){
        StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(link);
        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabase.child("Usuarios").child(id).removeValue();
                finish();
                Toast.makeText(UserDataActivity.this, "Usuario eliminado con exito", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(UserDataActivity.this, "Un error ha ocurrido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void HacerAdmin() {
        mDatabase.child("Usuarios").child(id).child("Prioridad").setValue(1);
        Toast.makeText(UserDataActivity.this, "Usuario modificado con exito", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
        finish();
    }

    public void QuitarAdmin(){
        mDatabase.child("Usuarios").child(id).child("Prioridad").setValue(0);
        Toast.makeText(UserDataActivity.this, "Usuario modificado con exito", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
        finish();
    }

    public void GetInf(){
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        }else{
        mDatabase.child("Usuarios").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String name = dataSnapshot.child("Name").getValue().toString();
                    String correo = dataSnapshot.child("Email").getValue().toString();
                    String URLFP = dataSnapshot.child("URL FP").getValue().toString();
                    String prioridad = dataSnapshot.child("Prioridad").getValue().toString();

                    prio = Integer.parseInt(prioridad);

                    link = URLFP;

                    lblNombreData.setText(name);
                    lblCorreoData.setText(correo);
                    Picasso.get()
                            .load(URLFP)
                            .error(R.mipmap.ic_launcher_round)
                            .into(imgPData);
                }
                switch (prio){
                    case 0:
                        btnAdm2.setEnabled(false);
                        btnAdm.setEnabled(true);
                        break;
                    case 1:
                        btnAdm.setEnabled(false);
                        btnAdm2.setEnabled(true);
                        break;
                }
                if(id.equals("jLqRnUiUJcRENYC3n0KfsPZB78L2")){
                    btnElminar.setEnabled(false);
                    btnAdm.setEnabled(false);
                    btnAdm2.setEnabled(false);
                    btnAdm.setText("Acceso denegado");
                    btnAdm2.setText("Acceso denegado");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });}

    }

}
