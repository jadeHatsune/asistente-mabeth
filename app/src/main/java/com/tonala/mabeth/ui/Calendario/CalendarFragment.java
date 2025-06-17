package com.tonala.mabeth.ui.Calendario;

import static com.tonala.mabeth.ui.Calendario.CalendarioUtilidades.daysInMonthArray;
import static com.tonala.mabeth.ui.Calendario.CalendarioUtilidades.monthYearFromDate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tonala.mabeth.BuildConfig;
import com.tonala.mabeth.Inicio.MainActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.database.DBAgenda;
import com.tonala.mabeth.database.DbEvents;
import com.tonala.mabeth.database.DbHelperCale;
import com.tonala.mabeth.database.DbHelperEvents;
import com.tonala.mabeth.ui.MenuSlideActivity;
import com.tonala.mabeth.ui.Recursos.Computacion.CompActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarFragment extends Fragment implements CalendarioAdapta.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private int dx3  = MenuSlideActivity.variablec;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DBAgenda dbAgenda = new DBAgenda(getActivity());
        DbHelperCale dbHelperCale = new DbHelperCale(getActivity());
        SQLiteDatabase db = dbHelperCale.getWritableDatabase();
        ArrayList<Event> listaxdcale = dbAgenda.mostrarEven();

        if (db!=null){
            if (!listaxdcale.isEmpty()){
                Event.eventsLists = listaxdcale;
        }else{
            }
        }

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarRecyclerView = (RecyclerView) view.findViewById(R.id.calendarRecyclerView);
        monthYearText = (TextView) view.findViewById(R.id.mesAnio);

        CalendarioUtilidades.selectedDate = LocalDate.now();
        setMonthView();

        switch(dx3){
            case 0:
                showSuccesDialog(view);
                MenuSlideActivity.variablec = 1;
                break;
            default:
                break;
        }

        view.findViewById(R.id.btnMPrev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesPrevio(view);
            }
        });

        view.findViewById(R.id.btnMSig).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proximoMes(view);
            }
        });

        return view;
    }

    private int getFirstTimeRun() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int result, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0; else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        return result;
    }

    private void showSuccesDialog(View xd){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog4, (ConstraintLayout)xd.findViewById(R.id.layoutDialogContainer4));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog4).setOnClickListener(new View.OnClickListener() {
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog18, (ConstraintLayout)xd.findViewById(R.id.layoutDialogContainer18));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog18).setOnClickListener(new View.OnClickListener() {
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

    private void setMonthView() {
        monthYearText.setText((monthYearFromDate(CalendarioUtilidades.selectedDate)).toUpperCase());
        ArrayList<LocalDate> daysInMonth = daysInMonthArray ();

        CalendarioAdapta calendarioAdapta = new CalendarioAdapta(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarioAdapta);

    }

    public void mesPrevio(View view) {
        CalendarioUtilidades.selectedDate = CalendarioUtilidades.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void proximoMes(View view) {
        CalendarioUtilidades.selectedDate = CalendarioUtilidades.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarioUtilidades.selectedDate = date;
            setMonthView();
            Intent intent = new Intent(getActivity(), DiariamenteActivity.class);
            startActivity(intent);
        }
    }


}