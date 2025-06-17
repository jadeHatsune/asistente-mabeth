package com.tonala.mabeth.ui.Notas;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.tonala.mabeth.adapters.NotesAdapter;
import com.tonala.mabeth.database.NotesDatabase;
import com.tonala.mabeth.entities.Note;
import com.tonala.mabeth.listeners.NotesListener;
import com.tonala.mabeth.ui.MenuSlideActivity;

import java.util.ArrayList;
import java.util.List;

public class ListaNotasTextoActivity extends AppCompatActivity implements NotesListener {

        public static final int REQUEST_CODE_ADD_NOTE = 1;
        public static final int REQUEST_CODE_UPDATE_NOTE = 2;
        public static final int REQUEST_CODE_SHOW_NOTES = 3;

        private FirebaseAuth mAuth;
        private DatabaseReference mDatabase;
        private RecyclerView lista3;
        private List<Note> noteList;
        private NotesAdapter notesAdapter;
        private ImageView imgP;
        public static int  variableinex;
        public static int  variableinex3;


        private int noteClickedPosition = -1;

        private FloatingActionButton fab3, fabr2;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas_texto);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        } else {
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            fab3 = findViewById(R.id.fab3);
            fabr2 = findViewById(R.id.fabr2);
            imgP = (ImageView) findViewById(R.id.imgPerfilData5);

            getInf();
            switch(getFirstTimeRun()){
                case 0:
                    showSuccesDialog2();
                    break;
                default:
                    variableinex=1;
                    break;
            }

            fab3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(getApplicationContext(), NotasTextoActivity.class), REQUEST_CODE_ADD_NOTE);
                    finish();
                }
            });

            fabr2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                    finish();
                }
            });

            lista3 = findViewById(R.id.lista3);
            lista3.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

            noteList = new ArrayList<>();
            notesAdapter = new NotesAdapter(noteList, this);
            lista3.setAdapter(notesAdapter);

            getNotes(REQUEST_CODE_SHOW_NOTES, false);

            EditText txtBuscar = findViewById(R.id.txtBuscar);
            txtBuscar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    notesAdapter.cancelTimer();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(noteList.size() != 0){
                        notesAdapter.searchNotes(s.toString());
                    }
                }
            });

        }
    }

    private int getFirstTimeRun() {
        SharedPreferences sp = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        int result, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0; else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        variableinex=0;
        variableinex3=0;
        return result;
    }

        private void showSuccesDialog2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListaNotasTextoActivity.this);
        View view = LayoutInflater.from(ListaNotasTextoActivity.this).inflate(R.layout.layout_dialog2, (ConstraintLayout)findViewById(R.id.layoutDialogContainer2));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showSuccesDialog3();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

        private void showSuccesDialog3(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListaNotasTextoActivity.this);
        View view = LayoutInflater.from(ListaNotasTextoActivity.this).inflate(R.layout.layout_dialog8, (ConstraintLayout)findViewById(R.id.layoutDialogContainer8));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showSuccesDialog4();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

        private void showSuccesDialog4(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListaNotasTextoActivity.this);
        View view = LayoutInflater.from(ListaNotasTextoActivity.this).inflate(R.layout.layout_dialog9, (ConstraintLayout)findViewById(R.id.layoutDialogContainer9));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog9).setOnClickListener(new View.OnClickListener() {
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


        @Override
        public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), NotasTextoActivity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("note", note);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
    }

        public void getNotes(final int requestCode, final boolean isNoteDeleted){
        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase.getDatabase(getApplicationContext()).noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if(requestCode == REQUEST_CODE_SHOW_NOTES){
                    noteList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();
                }else if(requestCode == REQUEST_CODE_ADD_NOTE){
                    noteList.add(0, notes.get(0));
                    notesAdapter.notifyItemInserted(0);
                    lista3.smoothScrollToPosition(0);
                }else if(requestCode == REQUEST_CODE_UPDATE_NOTE){
                    noteList.remove(noteClickedPosition);

                    if (isNoteDeleted){
                        notesAdapter.notifyItemRemoved(noteClickedPosition);
                    } else{
                        noteList.add(noteClickedPosition, notes.get(noteClickedPosition));
                        notesAdapter.notifyItemChanged(noteClickedPosition);
                    }
                }
            }
        }

        new GetNotesTask().execute();
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK){
            getNotes(REQUEST_CODE_ADD_NOTE, false);
        }else if(requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK){
            if(data != null){
                getNotes(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNoteDeleted", false));
            }
        }
    }

        public void getInf(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String URLFP = dataSnapshot.child("URL FP").getValue().toString();

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
    }
    }

