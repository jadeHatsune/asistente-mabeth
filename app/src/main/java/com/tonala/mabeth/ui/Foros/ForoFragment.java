package com.tonala.mabeth.ui.Foros;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewKt;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.tonala.mabeth.BuildConfig;
import com.tonala.mabeth.Inicio.MainActivity;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.Registro.Registrarse3;
import com.tonala.mabeth.adapters.ForosAdapter;
import com.tonala.mabeth.adapters.ListaEventosAdapter;
import com.tonala.mabeth.entities.Eventos;
import com.tonala.mabeth.entities.Foros;
import com.tonala.mabeth.ui.MenuSlideActivity;
import com.tonala.mabeth.ui.Recursos.RecursosFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ForoFragment extends Fragment {

    Uri FileUri;
    private ImageView imagenForo;
    private TextView lblimagen;
    private EditText txtCuerpoForo, txtTituloForo;
    private Spinner spTemas;
    private Spinner spFiltro;
    private String name;
    private int op = 0;
    private int dx2 = MenuSlideActivity.variableb;
    private LinearLayout layoutCrearForo;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private RecyclerView listadoForos;
    private int idForo;
    private int chquim2, chcomp2, chcali2;


    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    public void onStart() {
        super.onStart();
        getInf();
        String[] temas = new String[]{
                "Otros",
                "Programación",
                "Electrónica",
                "Química",
                "Calidad"
        };
        final List<String> temasList = new ArrayList<>(Arrays.asList(temas));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinnerforo, temasList){
            @Override
            public boolean isEnabled(int position){
                if((chcomp2 == 0 && position == 1) || (chcomp2 == 0 && position == 2) || (chquim2 == 0 && position == 3) || (chcali2 == 0 && position == 4)){
                    return false;
                }else{
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if((chcomp2 == 0 && position == 1) || (chcomp2 == 0 && position == 2) || (chquim2 == 0 && position == 3) || (chcali2 == 0 && position == 4)) {
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinnerforo);
        spFiltro.setAdapter(spinnerArrayAdapter);
        spFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        ObtnerForosOtro();
                        break;
                    case 1:
                        ObtnerForosProgra();
                        break;
                    case 2:
                        ObtnerForosElec();
                        break;
                    case 3:
                        ObtnerForosQuimica();
                        break;
                    case 4:
                        ObtnerForosCalidad();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_foro, container, false);
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
            spFiltro = view.findViewById(R.id.spFiltForo);
            mostrarCrearForo(view);
            ObtnerForos();
            switch(dx2){
                case 0:
                    showSuccesDialog(view);
                    MenuSlideActivity.variableb = 1;
                    break;
                default:
                    break;
            }


        }
        return view;
    }

    private void showSuccesDialog(View xd){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog23, (ConstraintLayout)xd.findViewById(R.id.layoutDialogContainer23));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog23).setOnClickListener(new View.OnClickListener() {
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog24, (ConstraintLayout)xd.findViewById(R.id.layoutDialogContainer24));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog24).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showSuccesDialog3(xd);
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showSuccesDialog3(View xd){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog25, (ConstraintLayout)xd.findViewById(R.id.layoutDialogContainer25));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog25).setOnClickListener(new View.OnClickListener() {
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


    private void mostrarCrearForo(View view){
        layoutCrearForo = view.findViewById(R.id.layout_CrearForo);
        bottomSheetBehavior = BottomSheetBehavior.from(layoutCrearForo);
        imagenForo = layoutCrearForo.findViewById(R.id.ImagenForo);
        lblimagen = layoutCrearForo.findViewById(R.id.lblsinImagen);
        txtCuerpoForo = layoutCrearForo.findViewById(R.id.txtCuerpoForo);
        txtTituloForo = layoutCrearForo.findViewById(R.id.txtTituloForo);

        spTemas = layoutCrearForo.findViewById(R.id.sp02);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.temas, android.R.layout.simple_spinner_item);
        spTemas.setAdapter(adapter);

        layoutCrearForo.findViewById(R.id.txtCrearForo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        layoutCrearForo.findViewById(R.id.btnEnviarForo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(!isConnected){
                    startActivity(new Intent(getActivity(), errorActivity.class));
                    getActivity().finish();
                }else{
                    publicarForo();
                    switch (spFiltro.getSelectedItem().toString()){
                        case "Programación":
                            ObtnerForosProgra();
                            break;
                        case "Electrónica":
                            ObtnerForosElec();
                            break;
                        case "Química":
                            ObtnerForosQuimica();
                            break;
                        case "Calidad":
                            ObtnerForosCalidad();
                            break;
                        case "Otro":
                            ObtnerForosOtro();
                            break;
                    }

                }
            }
        });
        layoutCrearForo.findViewById(R.id.btnCambiarImagenForo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
    }

    private void abrirGaleria() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/");
        startActivityForResult(gallery.createChooser(gallery, "Selecciona la aplicación"), 10);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(resultCode == RESULT_OK){
                FileUri = data.getData();
                imagenForo.setImageURI(FileUri);
                lblimagen.setVisibility(View.INVISIBLE);
                op = 1;
            }
        }
    }

    public void publicarForo(){
        if(!txtTituloForo.getText().toString().equals("") && !txtCuerpoForo.getText().toString().equals("")){
            if(op == 0){
                String id = mAuth.getCurrentUser().getUid();

                String texto = txtCuerpoForo.getText().toString();
                String titulo = txtTituloForo.getText().toString();
                String tema = spTemas.getSelectedItem().toString();
                Map<String, Object> map = new HashMap<>();
                map.put("Titulo", titulo);
                map.put("Cuerpo", texto);
                map.put("Tema", tema);
                map.put("Imagen", "null");
                map.put("URL Imagen", "null");
                map.put("Creador", name);
                map.put("UID creador", id);
                map.put("likes", "0");
                map.put("respuestas", "0");
                map.put("ID foro", String.valueOf(numForos()));
                mDatabase.child("Foros").child(String.valueOf(numForos())).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task4) {
                        if (task4.isSuccessful()) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            txtTituloForo.setText("");
                            txtCuerpoForo.setText("");
                            Toast.makeText(getActivity(), "Foro creado con éxito", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Error al crear foro", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }else if(op == 1) {
                String id = mAuth.getCurrentUser().getUid();
                StorageReference Folder = FirebaseStorage.getInstance().getReference().child("Foros");
                StorageReference file_name = Folder.child("file" + FileUri.getLastPathSegment() + String.valueOf(idForo));
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
                            String imagenForoURL = downloadUri.toString();
                            String texto = txtCuerpoForo.getText().toString();
                            String titulo = txtTituloForo.getText().toString();
                            String tema = spTemas.getSelectedItem().toString();
                            Toast.makeText(getActivity(), "Imagen subida con exito", Toast.LENGTH_SHORT).show();
                            String name_file = file_name.toString();
                            Map<String, Object> map = new HashMap<>();
                            map.put("Titulo", titulo);
                            map.put("Cuerpo", texto);
                            map.put("Tema", tema);
                            map.put("Imagen", name_file);
                            map.put("URL Imagen", imagenForoURL);
                            map.put("Creador", name);
                            map.put("UID creador", id);
                            map.put("likes", "0");
                            map.put("respuestas", "0");
                            map.put("ID foro", String.valueOf(numForos()));
                            mDatabase.child("Foros").child(String.valueOf(numForos())).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task4) {
                                    if (task4.isSuccessful()) {
                                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                        txtTituloForo.setText("");
                                        txtCuerpoForo.setText("");
                                        op = 0;
                                        lblimagen.setVisibility(View.VISIBLE);
                                        imagenForo.setVisibility(View.INVISIBLE);
                                        FileUri = null;
                                        Toast.makeText(getActivity(), "Foro creado con éxito", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getActivity(), "Error al crear foro", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                        }
                    }
                });
            }
        }else{
            Toast.makeText(getActivity(), "Rellene los campos necesarios", Toast.LENGTH_SHORT).show();
        }
    }

    public void getInf(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    name = dataSnapshot.child("Name").getValue().toString();
                    String chquim = dataSnapshot.child("rec_quim").getValue().toString();
                    String chcomp = dataSnapshot.child("rec_comp").getValue().toString();
                    String chcali = dataSnapshot.child("rec_cali").getValue().toString();

                    chquim2 = Integer.parseInt(chquim);
                    chcomp2 = Integer.parseInt(chcomp);
                    chcali2 = Integer.parseInt(chcali);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    private void ObtnerForosProgra(){
        List<Foros> listaForos = new ArrayList<>();
        mDatabase.child("Foros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Foros foros = null;
                    listaForos.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        foros = new Foros();
                        if(ds.child("Tema").getValue().toString().equals("Programación")){
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

    private void ObtnerForosElec(){
        List<Foros> listaForos = new ArrayList<>();
        mDatabase.child("Foros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Foros foros = null;
                    listaForos.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        foros = new Foros();
                        if(ds.child("Tema").getValue().toString().equals("Electrónica")){
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

    private void ObtnerForosQuimica(){
        List<Foros> listaForos = new ArrayList<>();
        mDatabase.child("Foros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Foros foros = null;
                    listaForos.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        foros = new Foros();
                        if(ds.child("Tema").getValue().toString().equals("Química")){
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

    private void ObtnerForosCalidad() {
        List<Foros> listaForos = new ArrayList<>();
        mDatabase.child("Foros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Foros foros = null;
                    listaForos.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        foros = new Foros();
                        if (ds.child("Tema").getValue().toString().equals("Calidad")) {
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

    private void ObtnerForosOtro(){
        List<Foros> listaForos = new ArrayList<>();
        mDatabase.child("Foros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Foros foros = null;
                    listaForos.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        foros = new Foros();
                        if(ds.child("Tema").getValue().toString().equals("Otro")){
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

    public int numForos(){
        mDatabase.child("Foros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    idForo = -1;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        idForo--;
                    }
                }else{
                    idForo = -1;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return idForo;
    }
}