package com.tonala.mabeth.Registro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.tonala.mabeth.Inicio.MainActivity;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Registrarse3 extends AppCompatActivity {

    private int nY, nM, nD, sY, sM, sD;
    static final int DATE_ID = 0;

    Calendar c= Calendar.getInstance();

    private Button btnRegistrarse, btnCambiarProf;
    private EditText t1;
    private CheckBox chQuim, chComp, chCali;
    private ImageView imgProf;
    Uri FileUri;

    Spinner opciones;

    private final String nameF = Registrarse.name;
    private final String mailF = Registrarse.mail;
    private final String passF = Registrarse.pass;
    private String fnF;
    private int op = 0;
    private String gradoF;
    private String name_file;
    private String fotoPerfilURL = "https://firebasestorage.googleapis.com/v0/b/mabeth-e00e0.appspot.com/o/Users%2Fprofile_img%2Fprof_pred.png?alt=media&token=451d0488-9860-4796-9c3b-16db1d74d92a";
    private String chkquim = "0";
    private String chkcomp = "0";
    private String chkcali = "0";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        }else{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse3);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();




        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        t1 = (EditText) findViewById(R.id.txtFN);
        btnCambiarProf = (Button) findViewById(R.id.btnCambiarProf);
        imgProf = (ImageView) findViewById(R.id.imgProf);
        chQuim = (CheckBox) findViewById(R.id.chQuim);
        chComp = (CheckBox) findViewById(R.id.chComp);
        chCali = (CheckBox) findViewById(R.id.chCali);

        opciones = (Spinner) findViewById(R.id.sp01);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.opciones, android.R.layout.simple_spinner_item);
        opciones.setAdapter(adapter);

        sM = c.get(Calendar.MONTH);
        sD = c.get(Calendar.DAY_OF_MONTH);
        sY = c.get(Calendar.YEAR);


        Picasso.get()
                .load(fotoPerfilURL)
                .error(R.mipmap.ic_launcher_round)
                .into(imgProf);



        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });


        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(op == 1){
                    registrarUsuario();
                }else{
                    Toast.makeText(Registrarse3.this, "Seleccione una imagen de usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCambiarProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });}




    }

    private void abrirGaleria() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/");
        startActivityForResult(gallery.createChooser(gallery, "Selecciona la aplicación"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(resultCode == RESULT_OK){
                FileUri = data.getData();
                imgProf.setImageURI(FileUri);
                op = 1;
            }
        }
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            nY = year;
            nM = month;
            nD = dayOfMonth;
            t1.setText((nM + 1)+"-"+nD+"-"+nY+"");
        }
    };

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sY, sM, sD);
        }
        return null;
    }



    private void registrarUsuario(){
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        }else{
        fnF = t1.getText().toString();
        gradoF = opciones.getSelectedItem().toString();

        if(chQuim.isChecked()){
            chkquim = "1";
        }
        if(chComp.isChecked()){
            chkcomp = "1";
        }
        if(chCali.isChecked()){
            chkcali = "1";
        }
        StorageReference Folder = FirebaseStorage.getInstance().getReference().child("Users/profile_img");
        StorageReference file_name = Folder.child("file"+FileUri.getLastPathSegment()+nameF);
        UploadTask uploadTask = file_name.putFile(FileUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task2) throws Exception {
                if (!task2.isSuccessful()) {
                    throw task2.getException();
                }
                return file_name.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task3) {
                if (task3.isSuccessful()) {
                    Uri downloadUri = task3.getResult();
                    fotoPerfilURL = downloadUri.toString();
                    Toast.makeText(Registrarse3.this, "Imagen subida con exito", Toast.LENGTH_SHORT).show();
                    mAuth.createUserWithEmailAndPassword(mailF, passF).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                name_file = file_name.toString();
                                Map<String, Object> map = new HashMap<>();
                                map.put("Name", nameF);
                                map.put("Email", mailF);
                                map.put("Password", passF);
                                map.put("Fecha de nacimiento", fnF);
                                map.put("Grado academico", gradoF);
                                map.put("URL FP", fotoPerfilURL);
                                map.put("name_file", name_file);
                                map.put("rec_quim", chkquim);
                                map.put("rec_comp", chkcomp);
                                map.put("rec_cali", chkcali);
                                map.put("Prioridad", 0);
                                String id = mAuth.getCurrentUser().getUid();
                                mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task4) {
                                        if(task4.isSuccessful()){
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            user.sendEmailVerification();
                                            Toast.makeText(Registrarse3.this, "Correo de verificación enviado", Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                            startActivity(new Intent(Registrarse3.this, MainActivity.class));
                                            finish();
                                        }else{
                                            Toast.makeText(Registrarse3.this, "Error en la creación de datos", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(Registrarse3.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Registrarse3.this, "Error al subir la imagen, intente con otra", Toast.LENGTH_SHORT).show();
                }
            }
        });}

    }
}