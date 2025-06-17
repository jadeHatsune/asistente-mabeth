package com.tonala.mabeth.ui.Notas;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.datatransport.runtime.BuildConfig;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.tonala.mabeth.R;
import com.tonala.mabeth.database.NotesDatabase;
import com.tonala.mabeth.entities.Eventos;
import com.tonala.mabeth.entities.Note;
import com.tonala.mabeth.ui.MenuSlideActivity;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotasTextoActivity extends AppCompatActivity{

    private EditText titulo, cuerpo;
    private ImageButton regresar, guardar;
    private TextView textFecha, textWebURL;
    private View viewColorcitoxd;
    private Note alreadyAvailableNote;
    private AlertDialog dialogDeleteNote;
    private ImageView imageNote;
    private String selectedNoteColor;
    private String selectedImagePath;
    private LinearLayout layoutWebURL;
    private int xd2 = ListaNotasTextoActivity.variableinex;



    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    private AlertDialog dialogAddURL, dialogOutNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_texto);


        titulo = findViewById(R.id.txtTituloTexto);
        regresar = (ImageButton) findViewById(R.id.btnGRegresar4);
        cuerpo = findViewById(R.id.txtCuerpoTexto);
        textFecha = findViewById(R.id.txtFecha2);
        guardar = (ImageButton) findViewById(R.id.btnGuardar);
        viewColorcitoxd = findViewById(R.id.viewColorcitoxd);
        imageNote = findViewById(R.id.imageNote);
        textWebURL = findViewById(R.id.textWebURL);
        layoutWebURL = findViewById(R.id.layoutWebURL);

        switch(xd2){
            case 0:
                showSuccesDialog();
                ListaNotasTextoActivity.variableinex = 1;
                break;
            default:
                ListaNotasTextoActivity.variableinex = 1;
                break;
        }

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListaNotasTextoActivity.class));
                finish();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
                startActivity(new Intent(getApplicationContext(), ListaNotasTextoActivity.class));
                finish();
            }
        });

        findViewById(R.id.btnGRegresar4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!titulo.getText().toString().trim().isEmpty() || !cuerpo.getText().toString().trim().isEmpty() || !textWebURL.getText().toString().trim().isEmpty()) {
                    showOutNoteDialog();
                }else{
                        startActivity(new Intent(getApplicationContext(), ListaNotasTextoActivity.class));
                        finish();
                    }
                }
        });


        textFecha.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date())
        );

        selectedNoteColor = "#7182A4";
        selectedImagePath ="";

        if (getIntent().getBooleanExtra("isViewOrUpdate", false)){
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            setViewOrUpdateNote();
        }

        findViewById(R.id.imageRemoveURL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textWebURL.setText(null);
                layoutWebURL.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.imageRemoveImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageNote.setImageBitmap(null);
                imageNote.setVisibility(View.GONE);
                findViewById(R.id.imageRemoveImage).setVisibility(View.GONE);
                selectedImagePath ="";
            }
        });

        initVariado();
        setviewColorcitoxd();
    }

    private int getFirstTimeRun() {
        SharedPreferences sp = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        int result, currentVersionCode = com.tonala.mabeth.BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0; else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        return result;
    }

    private void setViewOrUpdateNote(){
        titulo.setText(alreadyAvailableNote.getTitle());
        cuerpo.setText(alreadyAvailableNote.getNotetext());
        textFecha.setText(alreadyAvailableNote.getDate());

        if(alreadyAvailableNote.getNombredevariablepromedio() != null && !alreadyAvailableNote.getNombredevariablepromedio().trim().isEmpty()) {
            imageNote.setImageBitmap(BitmapFactory.decodeFile(alreadyAvailableNote.getNombredevariablepromedio()));
            imageNote.setVisibility(View.VISIBLE);
            findViewById(R.id.imageRemoveImage).setVisibility(View.VISIBLE);
            selectedImagePath = alreadyAvailableNote.getNombredevariablepromedio();
        }

        if(alreadyAvailableNote.getWeblink() != null && !alreadyAvailableNote.getWeblink().trim().isEmpty()){
            textWebURL.setText(alreadyAvailableNote.getWeblink());
            layoutWebURL.setVisibility(View.VISIBLE);
        }

    }

        private void saveNote() {
            if (titulo.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Favor de ingresar un título", Toast.LENGTH_SHORT).show();
                return;
            } else if (cuerpo.toString().trim().isEmpty()) {
                Toast.makeText(this, "La nota no puede estar vacía", Toast.LENGTH_SHORT).show();
                return;
            }

            final Note note = new Note();
            note.setTitle(titulo.getText().toString());
            note.setNotetext(cuerpo.getText().toString());
            note.setDate(textFecha.getText().toString());
            note.setColor(selectedNoteColor);
            note.setNombredevariablepromedio(selectedImagePath);

            if (layoutWebURL.getVisibility() == View.VISIBLE){
                note.setWeblink(textWebURL.getText().toString());
            }

            if (alreadyAvailableNote != null){
                note.setId(alreadyAvailableNote.getId());
            }

            @SuppressLint("StaticFieldLeak")
            class SaveNoteTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {
                    NotesDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            new SaveNoteTask().execute();

        }


        private void initVariado(){
        final LinearLayout layoutColor = findViewById(R.id.layoutcolores);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutColor);
        layoutColor.findViewById(R.id.txtcolores).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

            final ImageView ImageColor1 = layoutColor.findViewById(R.id.ImageColor1);
            final ImageView ImageColor2 = layoutColor.findViewById(R.id.ImageColor2);
            final ImageView ImageColor3 = layoutColor.findViewById(R.id.ImageColor3);
            final ImageView ImageColor4 = layoutColor.findViewById(R.id.ImageColor4);
            final ImageView ImageColor5 = layoutColor.findViewById(R.id.ImageColor5);

            layoutColor.findViewById(R.id.Color1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedNoteColor = "#6E727A";
                    ImageColor1.setImageResource(R.drawable.ic_check);
                    ImageColor2.setImageResource(0);
                    ImageColor3.setImageResource(0);
                    ImageColor4.setImageResource(0);
                    ImageColor5.setImageResource(0);
                    setviewColorcitoxd();
                }
            });

            layoutColor.findViewById(R.id.Color2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedNoteColor = "#83CB75";
                    ImageColor1.setImageResource(0);
                    ImageColor2.setImageResource(R.drawable.ic_check);
                    ImageColor3.setImageResource(0);
                    ImageColor4.setImageResource(0);
                    ImageColor5.setImageResource(0);
                    setviewColorcitoxd();
                }
            });

            layoutColor.findViewById(R.id.Color3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedNoteColor = "#EE5353";
                    ImageColor1.setImageResource(0);
                    ImageColor2.setImageResource(0);
                    ImageColor3.setImageResource(R.drawable.ic_check);
                    ImageColor4.setImageResource(0);
                    ImageColor5.setImageResource(0);
                    setviewColorcitoxd();
                }
            });

            layoutColor.findViewById(R.id.Color4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedNoteColor = "#92A3C5";
                    ImageColor1.setImageResource(0);
                    ImageColor2.setImageResource(0);
                    ImageColor3.setImageResource(0);
                    ImageColor4.setImageResource(R.drawable.ic_check);
                    ImageColor5.setImageResource(0);
                    setviewColorcitoxd();
                }
            });

            layoutColor.findViewById(R.id.Color5).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedNoteColor = "#FF9F43";
                    ImageColor1.setImageResource(0);
                    ImageColor2.setImageResource(0);
                    ImageColor3.setImageResource(0);
                    ImageColor4.setImageResource(0);
                    ImageColor5.setImageResource(R.drawable.ic_check);
                    setviewColorcitoxd();
                }
            });

            layoutColor.findViewById(R.id.layoutAddImage).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    if (ContextCompat.checkSelfPermission(
                            getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(NotasTextoActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_STORAGE_PERMISSION);
                }else{
                    selectImage();
                }}
            });

            layoutColor.findViewById(R.id.layoutAddLink).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    showAddURLDialog();
                }
            });

            if(alreadyAvailableNote != null){
                layoutColor.findViewById(R.id.layoutDeleteNotes).setVisibility(View.VISIBLE);
                layoutColor.findViewById(R.id.layoutDeleteNotes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        showDeleteNoteDialog();
                    }
                });
            }

            if(alreadyAvailableNote != null && alreadyAvailableNote.getColor() != null && alreadyAvailableNote.getColor().trim().isEmpty()){
                switch (alreadyAvailableNote.getColor()){
                    case "#83CB75":
                        layoutColor.findViewById(R.id.Color2).performClick();
                        break;
                    case "#EE5353":
                        layoutColor.findViewById(R.id.Color3).performClick();
                        break;
                    case "#92A3C5":
                        layoutColor.findViewById(R.id.Color4).performClick();
                        break;
                    case "#FF9F43":
                        layoutColor.findViewById(R.id.Color5).performClick();
                        break;
                }
            }


        }

        private void selectImage(){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }else{
                Toast.makeText(this, "Permiso Denegado!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showSuccesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NotasTextoActivity.this);
        View view = LayoutInflater.from(NotasTextoActivity.this).inflate(R.layout.layout_dialog10, (ConstraintLayout)findViewById(R.id.layoutDialogContainer10));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showSuccesDialog2();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showSuccesDialog2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NotasTextoActivity.this);
        View view = LayoutInflater.from(NotasTextoActivity.this).inflate(R.layout.layout_dialog11, (ConstraintLayout)findViewById(R.id.layoutDialogContainer11));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog11).setOnClickListener(new View.OnClickListener() {
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


    private void showDeleteNoteDialog(){

        if (dialogDeleteNote == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(NotasTextoActivity.this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_delete_note, (ViewGroup) findViewById(R.id.layoutDeleteNoteContainer));
            builder.setView(view);
            dialogDeleteNote = builder.create();
            if (dialogDeleteNote.getWindow() != null){
                dialogDeleteNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view.findViewById(R.id.txtdelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    class DeleteNoteTask extends AsyncTask<Void, Void, Void>{
                        @Override
                        protected Void doInBackground(Void... voids) {
                            NotesDatabase.getDatabase(getApplicationContext()).noteDao().deleteNote(alreadyAvailableNote);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Intent intent = new Intent();
                            intent.putExtra("isNoteDeleted", true);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    new DeleteNoteTask().execute();
                }
            });

            view.findViewById(R.id.txtCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogDeleteNote.dismiss();
                }
            });}

        dialogDeleteNote.show();
        }

        private void setviewColorcitoxd(){
        viewColorcitoxd.setBackgroundColor(Color.parseColor(selectedNoteColor));
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
             if (data != null){
                 Uri selectedImageUri = data.getData();
                 if(selectedImageUri != null){
                     try {
                         InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                         Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                         imageNote.setImageBitmap(bitmap);
                         imageNote.setVisibility(View.VISIBLE);
                         findViewById(R.id.imageRemoveImage).setVisibility(View.VISIBLE);

                         selectedImagePath = getPathFromUri(selectedImageUri);

                     }catch (Exception exception){
                         Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }
             }
        }
    }

    private String getPathFromUri(Uri contentUri){
        String filePath;
        Cursor cursor = getContentResolver()
                .query(contentUri, null, null, null, null);
        if (cursor == null){
            filePath = contentUri.getPath();
        }else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }

    private void showOutNoteDialog(){
        if (dialogOutNote == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(NotasTextoActivity.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_salir,
                    (ViewGroup) findViewById(R.id.layoutOutNoteContainer)
            );
            builder.setView(view);

            dialogOutNote = builder.create();
            if (dialogOutNote.getWindow() != null){
                dialogOutNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                view.findViewById(R.id.txtAceptar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            Toast.makeText(NotasTextoActivity.this, "Nota no guardada", Toast.LENGTH_SHORT).show();
                            dialogOutNote.dismiss();
                            startActivity(new Intent(getApplicationContext(), ListaNotasTextoActivity.class));
                            finish();
                        }
                });

                view.findViewById(R.id.txtCancel2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogOutNote.dismiss();
                        dialogOutNote = null;
                    }
                });
            }
            dialogOutNote.show();
        }
    }



    private void showAddURLDialog(){
        if (dialogAddURL == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(NotasTextoActivity.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_add_url,
                    (ViewGroup) findViewById(R.id.layoutAddUrlContainer)
            );
            builder.setView(view);

            dialogAddURL = builder.create();
            if (dialogAddURL.getWindow() != null){
                dialogAddURL.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                final EditText inputURL = view.findViewById(R.id.inputURL);
                inputURL.requestFocus();

                view.findViewById(R.id.txtAgregar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (inputURL.getText().toString().trim().isEmpty()) {
                            Toast.makeText(NotasTextoActivity.this, "Ingresa una URL", Toast.LENGTH_SHORT).show();
                        }else if(!Patterns.WEB_URL.matcher(inputURL.getText().toString()).matches()){
                            Toast.makeText(NotasTextoActivity.this, "Ingresa una URL válida", Toast.LENGTH_SHORT).show();
                        }else{
                            textWebURL.setText(inputURL.getText().toString());
                            layoutWebURL.setVisibility(View.VISIBLE);
                            dialogAddURL.dismiss();
                        }
                    }
                });

                view.findViewById(R.id.txtCancelar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAddURL.dismiss();
                        dialogAddURL = null;
                    }
                });
            }
            dialogAddURL.show();
        }
    }
}

