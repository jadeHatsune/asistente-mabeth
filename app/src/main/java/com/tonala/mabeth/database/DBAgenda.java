package com.tonala.mabeth.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tonala.mabeth.entities.Eventos;
import com.tonala.mabeth.ui.Calendario.CalendarioUtilidades;
import com.tonala.mabeth.ui.Calendario.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DBAgenda extends DbHelperCale{

    Context context;

    public DBAgenda(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarAgen(String name, String date, String time, Double milis){

        long id = 0;

        try {
            DbHelperCale dbHelperCale = new DbHelperCale(context);
            SQLiteDatabase db = dbHelperCale.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("date", date);
            values.put("time", time);
            values.put("milis", milis);

            id = db.insert(TABLE_AGENDA, null, values);
        }catch(Exception e){
            e.toString();
        }
        return id;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Event> mostrarEven(){
        DbHelperCale dbHelperCale = new DbHelperCale(context);
        SQLiteDatabase db = dbHelperCale.getWritableDatabase();

        ArrayList<Event> listaEven = new ArrayList<>();
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_AGENDA, null);
        if(cursorEventos.moveToFirst()){
            do{
                int id = cursorEventos.getInt(0);
                String name = cursorEventos.getString(1);
                String date = cursorEventos.getString(2);
                String time = cursorEventos.getString(3);
                Double milis = cursorEventos.getDouble(4);
                Event newEvent = new Event(id, name, LocalDate.parse(date), LocalTime.parse(time), milis);
                listaEven.add(newEvent);
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return listaEven;
    }

    public int contarEventos(){
        DbHelperCale dbHelperCale = new DbHelperCale(context);
        SQLiteDatabase db = dbHelperCale.getWritableDatabase();

        int contador = 0;

        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_AGENDA, null);
        if(cursorEventos.moveToFirst()){
            do{
                contador++;
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return contador;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Event verEventos(int id) {
        DbHelperCale dbHelperCale = new DbHelperCale(context);
        SQLiteDatabase db = dbHelperCale.getWritableDatabase();

        Event eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_AGENDA + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorEventos.moveToFirst()) {
            id = cursorEventos.getInt(0);
            String name = cursorEventos.getString(1);
            String date = cursorEventos.getString(2);
            String time = cursorEventos.getString(3);
            Double milis = cursorEventos.getDouble(4);
            eventos = new Event(id, name, LocalDate.parse(date), LocalTime.parse(time), milis);
        }
        cursorEventos.close();
        return eventos;
    }

    public boolean editarEventos(int id, String name, String date, String time, Double milis){

        boolean correcto = false;

        DbHelperCale dbHelperCale = new DbHelperCale(context);
        SQLiteDatabase db = dbHelperCale.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_AGENDA + " SET name = '"+ name + "', date = '" + date + "', time = '" + time + "', milis = '" + milis + "' WHERE id = '" + id + "' ");
            correcto = true;
        }catch(Exception e){
            e.toString();
            correcto = false;
        }finally {
            db.close();
        }
        return correcto;
    }

    public boolean eliminarEvento(int id){

        boolean correcto = false;

        DbHelperCale dbHelperCale = new DbHelperCale(context);
        SQLiteDatabase db = dbHelperCale.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_AGENDA + " WHERE id = '" + id +"'");
            correcto = true;
        }catch(Exception e){
            e.toString();
            correcto = false;
        }finally {
            db.close();
        }
        return correcto;
    }

}
