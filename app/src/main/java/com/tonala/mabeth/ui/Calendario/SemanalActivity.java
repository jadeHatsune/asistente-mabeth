package com.tonala.mabeth.ui.Calendario;

import static com.tonala.mabeth.ui.Calendario.CalendarioUtilidades.daysInMonthArray;
import static com.tonala.mabeth.ui.Calendario.CalendarioUtilidades.daysInWeekArray;
import static com.tonala.mabeth.ui.Calendario.CalendarioUtilidades.monthYearFromDate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tonala.mabeth.R;
import com.tonala.mabeth.ui.MenuSlideActivity;
import com.tonala.mabeth.ui.Notas.ListaNotasTextoActivity;

import java.time.LocalDate;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)


public class SemanalActivity extends AppCompatActivity implements CalendarioAdapta.OnItemListener
{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private int xd9 = MenuSlideActivity.variableinex5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semanal);
        initWidgets();
        setWeekView();

        findViewById(R.id.casaxd2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                finish();
            }
        });

        switch(xd9){
            case 0:
                showSuccesDialog();
                MenuSlideActivity.variableinex5 = 1;
                break;
            default:
                MenuSlideActivity.variableinex5 = 1;
                break;
        }
    }

    private void showSuccesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SemanalActivity.this);
        View view = LayoutInflater.from(SemanalActivity.this).inflate(R.layout.layout_dialog20, (ConstraintLayout)findViewById(R.id.layoutDialogContainer20));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog20).setOnClickListener(new View.OnClickListener() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SemanalActivity.this);
        View view = LayoutInflater.from(SemanalActivity.this).inflate(R.layout.layout_dialog21, (ConstraintLayout)findViewById(R.id.layoutDialogContainer21));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog21).setOnClickListener(new View.OnClickListener() {
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



    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView2);
        monthYearText = findViewById(R.id.mesAnio2);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText((CalendarioUtilidades.monthYearFromDate(CalendarioUtilidades.selectedDate)).toUpperCase());
        ArrayList<LocalDate> days = CalendarioUtilidades.daysInWeekArray(CalendarioUtilidades.selectedDate);

        CalendarioAdapta calendarioAdapta = new CalendarioAdapta(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarioAdapta);
        setEventAdapter();
    }


    public void semanaPrevia(View view) {

        CalendarioUtilidades.selectedDate = CalendarioUtilidades.selectedDate.minusWeeks(1);
        setWeekView();

    }

    public void proximaSemana(View view) {

        CalendarioUtilidades.selectedDate = CalendarioUtilidades.selectedDate.plusWeeks(1);
        setWeekView();

    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarioUtilidades.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarioUtilidades.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void nuevoEventoAccion(View view) {
        startActivity(new Intent(this, EventoEditActivity.class));
    }

    public void accionDiaria(View view) {
        startActivity(new Intent(this, DiariamenteActivity.class));
    }
}