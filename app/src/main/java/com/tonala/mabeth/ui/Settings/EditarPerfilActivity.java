package com.tonala.mabeth.ui.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.MenuSlideActivity;

public class EditarPerfilActivity extends AppCompatActivity {

    private CheckBox chQuim, chComp, chCali;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String chkquim;
    private String chkcomp;
    private TextView lblNombreUsu;
    private ImageView imgP;
    private String chkcali;

    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lblNombreUsu = (TextView) findViewById(R.id.txtNombreUsuSett);
        imgP = (ImageView) findViewById(R.id.imgPerfilUsuSett);


        chQuim = (CheckBox) findViewById(R.id.chQuimset);

        chComp = (CheckBox) findViewById(R.id.chCompset);
        chCali = (CheckBox) findViewById(R.id.chCaliset);
        guardar = (Button) findViewById(R.id.btnsetGuardar);

        getInf();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });
    }

    private void guardarCambios(){
        if(chQuim.isChecked()){
            chkquim = "1";
        }else{
            chkquim = "0";
        }
        if(chComp.isChecked()){
            chkcomp = "1";
        }else{
            chkcomp = "0";
        }
        if(chCali.isChecked()){
            chkcali = "1";
        }else{
            chkcali = "0";
        }
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).child("rec_quim").setValue(chkquim);
        mDatabase.child("Usuarios").child(id).child("rec_comp").setValue(chkcomp);
        mDatabase.child("Usuarios").child(id).child("rec_cali").setValue(chkcali);
        Toast.makeText(getApplicationContext(), "Ajustes guardados con éxito", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
        finish();

    }

    private void getInf(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String chquim = dataSnapshot.child("rec_quim").getValue().toString();
                    String chcomp = dataSnapshot.child("rec_comp").getValue().toString();
                    String chcali = dataSnapshot.child("rec_cali").getValue().toString();
                    String name = dataSnapshot.child("Name").getValue().toString();
                    String URLFP = dataSnapshot.child("URL FP").getValue().toString();


                    lblNombreUsu.setText(name);
                    Picasso.get()
                            .load(URLFP)
                            .error(R.mipmap.ic_launcher_round)
                            .into(imgP);

                    chkquim = chquim;
                    chkcomp = chcomp;
                    chkcali = chcali;

                    int chquim2 = Integer.parseInt(chkquim);
                    int chcomp2 = Integer.parseInt(chkcomp);
                    int chcali2 = Integer.parseInt(chkcali);

                    if(chquim2 == 0){
                        chQuim.setChecked(false);
                    }
                    if(chcomp2 == 0){
                        chComp.setChecked(false);
                    }
                    if(chcali2 == 0){
                        chCali.setChecked(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}