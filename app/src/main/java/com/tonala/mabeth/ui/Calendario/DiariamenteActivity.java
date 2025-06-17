package com.tonala.mabeth.ui.Calendario;

import static com.tonala.mabeth.ui.Calendario.CalendarioUtilidades.selectedDate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.MenuSlideActivity;
import com.tonala.mabeth.ui.Notas.ListaNotasTextoActivity;
import com.tonala.mabeth.ui.Notas.NotasTextoActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiariamenteActivity extends AppCompatActivity
{
    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;
    private int xd8 = MenuSlideActivity.variableinex4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diariamente);
        iniWidgets();

        findViewById(R.id.layoutsema).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SemanalActivity.class));
                finish();
            }
        });

        switch(xd8){
            case 0:
                showSuccesDialog();
                MenuSlideActivity.variableinex4 = 1;
                break;
            default:
                MenuSlideActivity.variableinex4 = 1;
                break;
        }

    }

    private void iniWidgets() {
        monthDayText = findViewById(R.id.monthDayText);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        hourListView = findViewById(R.id.hourListView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume()
    {
        super.onResume();
        setDayView();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDayView() {
        monthDayText.setText((CalendarioUtilidades.monthDayFromDate(CalendarioUtilidades.selectedDate)).toUpperCase());
        String dayOfWeek = CalendarioUtilidades.selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText((dayOfWeek).toUpperCase());
        setHourAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setHourAdapter() {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<HourEvent> hourEventList()
    {
        ArrayList<HourEvent> list = new ArrayList<>();
    for (int hour = 0; hour < 24; hour++)
    {
        LocalTime time = LocalTime.of(hour, 0);
        ArrayList<Event> events = Event.eventsForDateAndTime(CalendarioUtilidades.selectedDate, time);
        HourEvent hourEvent = new HourEvent(time, events);
        list.add(hourEvent);
    }
        return list;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void diaPrevio(View view) {
        CalendarioUtilidades.selectedDate = CalendarioUtilidades.selectedDate.minusDays(1);
        setDayView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void proximoDia(View view) {
        CalendarioUtilidades.selectedDate = CalendarioUtilidades.selectedDate.plusDays(1);
        setDayView();
    }

    public void nuevoEventoAccion(View view) {
        startActivity(new Intent(this, EventoEditActivity.class));
    }

    private void showSuccesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DiariamenteActivity.this);
        View view = LayoutInflater.from(DiariamenteActivity.this).inflate(R.layout.layout_dialog19, (ConstraintLayout)findViewById(R.id.layoutDialogContainer19));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog19).setOnClickListener(new View.OnClickListener() {
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
}