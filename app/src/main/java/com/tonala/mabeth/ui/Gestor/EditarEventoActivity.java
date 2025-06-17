package com.tonala.mabeth.ui.Gestor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.WorkManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.tonala.mabeth.IniciarSes.IniciarSes;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.database.DbEvents;
import com.tonala.mabeth.entities.Eventos;
import com.tonala.mabeth.ui.Gestor.WorkManagerNoty;
import com.tonala.mabeth.ui.MenuSlideActivity;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditarEventoActivity extends AppCompatActivity {

    private Button guardar, eliminar;
    private ImageView regresar;
    private TextView txtfecha, txthora, selfecha, selhora;
    private EditText txtTitulo, txtCuerpo;
    private CheckBox estado;
    private Double milis;
    public static String selectedEventoColor2;
    boolean correcto = false;
    private View viewColorcitoxd22;
    private String color;

    private String fechabd = "0", horabd = "0";
    Calendar actual = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();

    private int minutos, hora, dia, mes, anio, conf, tag;

    Eventos evento;

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(getApplicationContext(), errorActivity.class));
            finish();
        } else {
            regresar = (ImageView) findViewById(R.id.btnGestorRegresar2);
            selfecha = (TextView) findViewById(R.id.btnCambiarFechaNoty2);
            selhora = (TextView) findViewById(R.id.btnCambiarHoraNoty2);
            guardar = (Button) findViewById(R.id.btnGuardarNoty2);
            txtfecha = (TextView) findViewById(R.id.lblFechaNoty2);
            txthora = (TextView) findViewById(R.id.lblHoraNoty2);
            txtTitulo = (EditText) findViewById(R.id.txtNombreNoty2);
            txtCuerpo = (EditText) findViewById(R.id.txtCuerpoNoty2);
            eliminar = (Button) findViewById(R.id.btnEliminarEvento);
            viewColorcitoxd22 = findViewById(R.id.viewColorcitoxd22);
            estado = (CheckBox) findViewById(R.id.chEstadoEvento);

            selectedEventoColor2 = "#5252D3";
            color = selectedEventoColor2;

            initVariado2();
            setviewColorcitoxd2();

            regresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                    finish();
                }
            });

            if(savedInstanceState == null){
                Bundle extras = getIntent().getExtras();
                if (extras == null){
                    id = Integer.parseInt(null);
                }else{
                    id = extras.getInt("ID");
                }
            }else{
                id = (int) savedInstanceState.getSerializable("ID");
            }

            DbEvents dbEvents = new DbEvents(getApplicationContext());
            evento = dbEvents.verEvento(id);

            if(evento != null){
                txtTitulo.setText(evento.getTitulo());
                txtCuerpo.setText(evento.getCuerpo());
                txtfecha.setText(evento.getFecha());
                txthora.setText(evento.getHora());
                milis = evento.getMilis();
                tag = Integer.parseInt(evento.getTag());
                if(evento.getChecked().equals("Sin completar")){
                    estado.setChecked(false);
                }else{
                    estado.setChecked(true);
                }
            }

            regresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                    finish();
                }
            });

            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!txtTitulo.getText().toString().equals("") && !txtCuerpo.getText().toString().equals("")){
                        String est;
                        if(estado.isChecked()){
                            est = "Completado";
                        }else{
                            est = "Sin completar";
                        }
                        if(fechabd.equals("0") && horabd.equals("0")){
                            Long Alerttime = milis.longValue() - System.currentTimeMillis();
                            fechabd = txtfecha.getText().toString();
                            horabd = txthora.getText().toString();
                            String t = txtTitulo.getText().toString();
                            String d = txtCuerpo.getText().toString();
                            correcto = dbEvents.editarEvento(id, t, d, fechabd, horabd, est, milis, color);
                            if(correcto){
                                EliminarNoty(v, tag);
                                Data data = EditarData(t, d, tag);
                                WorkManagerNoty.GuardarNoty(Alerttime, data, tag);
                                Toast.makeText(getApplicationContext(), "Alarma guardada", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                                finish();
                            }else{
                                Snackbar.make(v, "Ocurrió un error", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            }
                        }else if(conf == 2){
                            Long Alerttime = calendar.getTimeInMillis() - System.currentTimeMillis();
                            Double miliss = Double.valueOf(calendar.getTimeInMillis());
                            String t = txtTitulo.getText().toString();
                            String d = txtCuerpo.getText().toString();
                            correcto = dbEvents.editarEvento(id, t, d, fechabd, horabd, est, miliss, color);
                            if(correcto){
                                EliminarNoty(v, tag);
                                Data data = EditarData(t, d, tag);
                                WorkManagerNoty.GuardarNoty(Alerttime, data, tag);
                                Toast.makeText(getApplicationContext(), "Alarma guardada", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                                finish();
                            }else{
                                Snackbar.make(v, "Ocurrió un error", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Seleccione correctamente todos los datos", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Snackbar.make(v, "No deje campos en blanco", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
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
                            conf++;
                            if(conf != 2){
                                txthora.setText("Hora");
                            }
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
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onTimeSet(TimePicker view, int h, int m) {
                            calendar.set(Calendar.HOUR_OF_DAY, h);
                            calendar.set(Calendar.MINUTE, m);
                            conf++;
                            if(conf != 2){
                                txtfecha.setText("Fecha");
                            }
                            txthora.setText(String.format("%02d:%02d", h, m));
                            horabd = String.format("%02d:%02d", h, m);
                        }
                    }, hora, minutos, true);
                    timePickerDialog.show();
                }
            });

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dbEvents.eliminarEvento(id)){
                        EliminarNoty(v, tag);
                        Toast.makeText(getApplicationContext(), "Alarma eliminada", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                        finish();
                    }
                }
            });
        }
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
                selectedEventoColor2 = "#5252D3";
                color = selectedEventoColor2;
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
                selectedEventoColor2 = "#9C4DD0";
                color = selectedEventoColor2;
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
                selectedEventoColor2 = "#393A3C";
                color = selectedEventoColor2;
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
                selectedEventoColor2 = "#0D6AC6";
                color = selectedEventoColor2;
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
                selectedEventoColor2 = "#C60D4B";
                color = selectedEventoColor2;
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
        viewColorcitoxd22.setBackgroundColor(Color.parseColor(selectedEventoColor2));
    }


    private Data EditarData(String titulo, String detalle, int id_noti){
        return new Data.Builder()
                .putString("titulo", titulo)
                .putString("detalle", detalle)
                .putInt("id_noti", id_noti).build();
    }

    private void EliminarNoty(View v, int tag){
        WorkManager.getInstance(this).cancelAllWorkByTag(String.valueOf(tag));
        Snackbar.make(v, "Alarma eliminada", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}