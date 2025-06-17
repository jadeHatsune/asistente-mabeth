package com.tonala.mabeth.ui.Calendario;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.work.Data;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
public class EventoEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private TextView eventDateTV, evenTimeTV;
    private LocalTime time;
    private ImageView regresar;
    private int minutos, hora, dia, mes, anio, tag;
    private int xd10 = MenuSlideActivity.variableinex6;

    Calendar actual = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_edit);
        iniWidgets();
        time = LocalTime.now();
        regresar = (ImageView) findViewById(R.id.casaxd);
        eventDateTV.setText("" + CalendarioUtilidades.formattedDate(CalendarioUtilidades.selectedDate));

        switch(xd10){
            case 0:
                showSuccesDialog();
                MenuSlideActivity.variableinex6 = 1;
                break;
            default:
                break;
        }

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                finish();
            }
        });

        findViewById(R.id.txtEventoHora).setOnClickListener(new View.OnClickListener() {
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

                                time = localTime;

                                evenTimeTV.setText(CalendarioUtilidades.formattedTime(time));


                            }
                        },
                        hora,
                        minutos,
                        true);
                timePickerDialog.show();
            }
        });

    }

    private void iniWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        evenTimeTV = findViewById(R.id.eventTimeTV);
    }

    private void showSuccesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EventoEditActivity.this);
        View view = LayoutInflater.from(EventoEditActivity.this).inflate(R.layout.layout_dialog22, (ConstraintLayout) findViewById(R.id.layoutDialogContainer22));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog22).setOnClickListener(new View.OnClickListener() {
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

    public void guardarEventoAccion(View view) {

        Long Alerttime = calendar.getTimeInMillis() - System.currentTimeMillis();
        Double milis = Double.valueOf(calendar.getTimeInMillis());

        String t = eventNameET.getText().toString();

        String name = eventNameET.getText().toString();
        String date = CalendarioUtilidades.selectedDate.toString();

        DbHelperCale dbHelperCale = new DbHelperCale(EventoEditActivity.this);
        SQLiteDatabase db = dbHelperCale.getWritableDatabase();
        if (db != null){
            DBAgenda dbAgenda = new DBAgenda(getApplicationContext());
            long id = dbAgenda.insertarAgen(name, date, String.valueOf(time), milis);
            if (id>0){
                Data data = GuardarData(t, tag);
                WorkManagerNotyCale.GuardarNoty(Alerttime, data, tag);
                Toast.makeText(EventoEditActivity.this, "Evento guardado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                finish();
            }else{
                Toast.makeText(EventoEditActivity.this, "Error al crear el evento", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                finish();
            }}

    }

    private Data GuardarData(String titulo, int id_noti){
        return new Data.Builder()
                .putString("titulo", titulo)
                .putInt("id_noti", id_noti).build();
    }
}