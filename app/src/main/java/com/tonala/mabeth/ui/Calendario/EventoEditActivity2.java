package com.tonala.mabeth.ui.Calendario;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.work.Data;
import androidx.work.WorkManager;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.tonala.mabeth.R;
import com.tonala.mabeth.database.DBAgenda;
import com.tonala.mabeth.database.DbEvents;
import com.tonala.mabeth.database.DbHelperCale;
import com.tonala.mabeth.ui.Gestor.WorkManagerNoty;
import com.tonala.mabeth.ui.MenuSlideActivity;
import com.tonala.mabeth.ui.Notas.ListaNotasTextoActivity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EventoEditActivity2 extends AppCompatActivity {

    private EditText eventNameET2;
    private TextView eventDateTV2, evenTimeTV2;
    private LocalTime time;
    private String fechabd = "0", horabd = "0";
    private Double milis;
    boolean correcto = false;
    private Button guardar, eliminar;
    private ImageView regresar2;
    private int minutos, hora, dia, mes, anio, tag, conf;
    private int xd10 = MenuSlideActivity.variableinex6;

    Event event;

    int id = 0;

    Calendar actual = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_edit2);
        iniWidgets();
        time = LocalTime.now();
        eliminar = (Button) findViewById(R.id.btnEliminar);
        regresar2 = (ImageView) findViewById(R.id.casaxd2);
        eventDateTV2.setText("" + CalendarioUtilidades.formattedDate(CalendarioUtilidades.selectedDate));

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

        DBAgenda dbAgenda = new DBAgenda(getApplicationContext());
        event = dbAgenda.verEventos(id);

        if (event!=null){
            eventNameET2.setText(event.getName());
            eventDateTV2.setText(String.valueOf(event.getDate()));
            evenTimeTV2.setText(String.valueOf(event.getTime()));
            milis = event.getMilis();
        }

        regresar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                finish();
            }
        });

        findViewById(R.id.btnCrearEv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!eventNameET2.getText().toString().equals("")){
                    if(fechabd.equals("0") && horabd.equals("0")){
                        Long Alerttime = milis.longValue() - System.currentTimeMillis();
                        fechabd = eventDateTV2.getText().toString();
                        horabd = evenTimeTV2.getText().toString();
                        String t = eventNameET2.getText().toString();
                        correcto = dbAgenda.editarEventos(id, t, fechabd, horabd, milis);
                        if(correcto){
                            EliminarNoty(view, tag);
                            Data data = EditarData(t, tag);
                            WorkManagerNotyCale.GuardarNoty(Alerttime, data, tag);
                            Toast.makeText(getApplicationContext(), "Evento modificado", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                            finish();
                        }else{
                            Snackbar.make(view, "Ocurrió un error", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }else if(conf == 2){
                        Long Alerttime = calendar.getTimeInMillis() - System.currentTimeMillis();
                        Double miliss = Double.valueOf(calendar.getTimeInMillis());
                        String t = eventNameET2.getText().toString();
                        correcto = dbAgenda.editarEventos(id, t, fechabd, horabd, miliss);
                        if(correcto){
                            EliminarNoty(view, tag);
                            Data data = EditarData(t, tag);
                            WorkManagerNotyCale.GuardarNoty(Alerttime, data, tag);
                            Toast.makeText(getApplicationContext(), "Evento modificado", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                            finish();
                        }else{
                            Snackbar.make(view, "Ocurrió un error", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Seleccione correctamente todos los datos", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Snackbar.make(view, "No deje campos en blanco", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbAgenda.eliminarEvento(id)){
                    EliminarNoty(v, tag);
                    Toast.makeText(getApplicationContext(), "Evento eliminado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                    finish();
                }
            }
        });

        findViewById(R.id.txtEventoHora2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hora = actual.get(Calendar.HOUR_OF_DAY);
                minutos = actual.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int h, int m) {
                                int day = CalendarioUtilidades.selectedDate.getDayOfMonth();

                                // Get year from date
                                int year = CalendarioUtilidades.selectedDate.getYear();

                                ZoneId defaultZoneId = ZoneId.systemDefault();
                                Date date = Date.from(CalendarioUtilidades.selectedDate.atStartOfDay(defaultZoneId).toInstant());
                                Calendar calendarxd = Calendar.getInstance();
                                calendarxd.setTime(date);
                                int month = calendarxd.get(Calendar.MONTH);

                                calendar.set(Calendar.HOUR_OF_DAY, h);
                                calendar.set(Calendar.MINUTE, m);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.YEAR, year);

                                Date xd = calendar.getTime();
                                Instant instant = xd.toInstant();
                                ZoneId zone = ZoneId.systemDefault();
                                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
                                LocalTime localTime = localDateTime.toLocalTime();

                                conf++;
                                if(conf != 2){
                                    eventDateTV2.setText("Hora");
                                }

                                time = localTime;

                                evenTimeTV2.setText(CalendarioUtilidades.formattedTime(time));


                            }
                        },
                        hora,
                        minutos,
                        true);
                timePickerDialog.show();
            }
        });

    }

    private void EliminarNoty(View v, int tag){
        WorkManager.getInstance(this).cancelAllWorkByTag(String.valueOf(tag));
        Snackbar.make(v, "Alarma eliminada", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    private Data EditarData(String titulo, int id_noti){
        return new Data.Builder()
                .putString("titulo", titulo)
                .putInt("id_noti", id_noti).build();
    }

    private void iniWidgets() {
        eventNameET2 = findViewById(R.id.eventNameET2);
        eventDateTV2 = findViewById(R.id.eventDateTV2);
        evenTimeTV2 = findViewById(R.id.eventTimeTV2);
    }


    private Data GuardarData(String titulo, int id_noti){
        return new Data.Builder()
                .putString("titulo", titulo)
                .putInt("id_noti", id_noti).build();
    }
}