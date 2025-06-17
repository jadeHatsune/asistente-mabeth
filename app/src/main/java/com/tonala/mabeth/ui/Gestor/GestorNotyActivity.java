package com.tonala.mabeth.ui.Gestor;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Data;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.database.DbEvents;
import com.tonala.mabeth.database.DbHelperEvents;
import com.tonala.mabeth.entities.Eventos;
import com.tonala.mabeth.entities.Note;
import com.tonala.mabeth.ui.MenuSlideActivity;
import com.tonala.mabeth.ui.Notas.NotasTextoActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GestorNotyActivity extends AppCompatActivity {

    private Button guardar;
    private ImageView regresar;
    private TextView txtfecha, txthora, selfecha, selhora;
    private EditText txtTitulo, txtCuerpo;
    public static String selectedEventoColor;
    private View viewColorcitoxd2;
    private int xd7 = MenuSlideActivity.variableinex3;

    Calendar actual = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();

        private int minutos, hora, dia, mes, anio, tag;
    private String fechabd, horabd, color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor_noty);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        } else {
            regresar = (ImageView) findViewById(R.id.btnGestorRegresar);
            selfecha = (TextView) findViewById(R.id.btnCambiarFechaNoty);
            selhora = (TextView) findViewById(R.id.btnCambiarHoraNoty);
            guardar = (Button) findViewById(R.id.btnGuardarNoty);
            txtfecha = (TextView) findViewById(R.id.lblFechaNoty);
            txthora = (TextView) findViewById(R.id.lblHoraNoty);
            txtTitulo = (EditText) findViewById(R.id.txtNombreNoty);
            viewColorcitoxd2 = findViewById(R.id.viewColorcitoxd2);
            txtCuerpo = (EditText) findViewById(R.id.txtCuerpoNoty);


            selectedEventoColor = "#5252D3";
            color = selectedEventoColor;

            initVariado2();
            setviewColorcitoxd2();

            switch(xd7){
                case 0:
                    showSuccesDialog();
                    MenuSlideActivity.variableinex3 = 1;
                    break;
                default:
                    MenuSlideActivity.variableinex3 = 1;
                    break;
            }

            regresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                    finish();
                }
            });

            selfecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anio = actual.get(Calendar.YEAR);
                    mes = actual.get(Calendar.MONTH);
                    dia = actual.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int y, int m, int d) {
                            calendar.set(Calendar.DAY_OF_MONTH, d);
                            calendar.set(Calendar.MONTH, m);
                            calendar.set(Calendar.YEAR, y);

                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            String strDate = format.format(calendar.getTime());
                            txtfecha.setText(strDate);
                            fechabd = strDate;
                        }
                    }, anio, mes, dia);
                    datePickerDialog.show();
                }
            });

            selhora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hora = actual.get(Calendar.HOUR_OF_DAY);
                    minutos = actual.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int h, int m) {
                            calendar.set(Calendar.HOUR_OF_DAY, h);
                            calendar.set(Calendar.MINUTE, m);

                            txthora.setText(String.format("%02d:%02d", h, m));
                            horabd = String.format("%02d:%02d", h, m);
                        }
                    }, hora, minutos, true);
                    timePickerDialog.show();
                }
            });

            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long Alerttime = calendar.getTimeInMillis() - System.currentTimeMillis();
                    Double milis = Double.valueOf(calendar.getTimeInMillis());

                    String t = txtTitulo.getText().toString();
                    String d = txtCuerpo.getText().toString();
                    String checked = "Sin completar";

                    DbHelperEvents dbHelper = new DbHelperEvents(getApplicationContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    if(db != null){
                        DbEvents dbEvents = new DbEvents(getApplicationContext());
                        int contador = dbEvents.contarEventos();
                        int tag = contador + 1;
                        long id = dbEvents.insertarEvento(t, d, fechabd, horabd, checked, milis, String.valueOf(tag), color);
                        if (id>0){
                            Data data = GuardarData(t, d, tag);
                            WorkManagerNoty.GuardarNoty(Alerttime, data, tag);
                            Toast.makeText(getApplicationContext(), "Alarma guardada", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Ocurrió un error", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                            finish();
                        }
                    }
                }
            });

        }
    }


    private void showSuccesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GestorNotyActivity.this);
        View view = LayoutInflater.from(GestorNotyActivity.this).inflate(R.layout.layout_dialog16, (ConstraintLayout)findViewById(R.id.layoutDialogContainer16));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog16).setOnClickListener(new View.OnClickListener() {
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

    private Data GuardarData(String titulo, String detalle, int id_noti){
        return new Data.Builder()
                .putString("titulo", titulo)
                .putString("detalle", detalle)
                .putInt("id_noti", id_noti).build();
    }

    private void initVariado2(){
        final LinearLayout layoutColor = findViewById(R.id.layoutcolores2);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutColor);
        layoutColor.findViewById(R.id.txtcolores2).setOnClickListener(new View.OnClickListener() {
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
                selectedEventoColor = "#5252D3";
                color = selectedEventoColor;
                ImageColor1.setImageResource(R.drawable.ic_check);
                ImageColor2.setImageResource(0);
                ImageColor3.setImageResource(0);
                ImageColor4.setImageResource(0);
                ImageColor5.setImageResource(0);
                setviewColorcitoxd2();
            }
        });

        layoutColor.findViewById(R.id.Color2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedEventoColor = "#9C4DD0";
                color = selectedEventoColor;
                ImageColor1.setImageResource(0);
                ImageColor2.setImageResource(R.drawable.ic_check);
                ImageColor3.setImageResource(0);
                ImageColor4.setImageResource(0);
                ImageColor5.setImageResource(0);
                setviewColorcitoxd2();
            }
        });

        layoutColor.findViewById(R.id.Color3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedEventoColor = "#393A3C";
                color = selectedEventoColor;
                ImageColor1.setImageResource(0);
                ImageColor2.setImageResource(0);
                ImageColor3.setImageResource(R.drawable.ic_check);
                ImageColor4.setImageResource(0);
                ImageColor5.setImageResource(0);
                setviewColorcitoxd2();
            }
        });

        layoutColor.findViewById(R.id.Color4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedEventoColor = "#0D6AC6";
                color = selectedEventoColor;
                ImageColor1.setImageResource(0);
                ImageColor2.setImageResource(0);
                ImageColor3.setImageResource(0);
                ImageColor4.setImageResource(R.drawable.ic_check);
                ImageColor5.setImageResource(0);
                setviewColorcitoxd2();
            }
        });

        layoutColor.findViewById(R.id.Color5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedEventoColor = "#C60D4B";
                color = selectedEventoColor;
                ImageColor1.setImageResource(0);
                ImageColor2.setImageResource(0);
                ImageColor3.setImageResource(0);
                ImageColor4.setImageResource(0);
                ImageColor5.setImageResource(R.drawable.ic_check);
                setviewColorcitoxd2();
            }
        });

    }

    private void setviewColorcitoxd2(){
        viewColorcitoxd2.setBackgroundColor(Color.parseColor(selectedEventoColor));
    }



}