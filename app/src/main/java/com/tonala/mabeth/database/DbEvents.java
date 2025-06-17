package com.tonala.mabeth.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.tonala.mabeth.entities.Eventos;

import java.util.ArrayList;
import java.util.List;

public class DbEvents extends DbHelperEvents{

    Context context;

    public DbEvents(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarEvento(String titulo, String cuerpo, String fecha, String hora, String checked, Double milis, String tag, String color){

        long id = 0;

        try {
            DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
            SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("titulo", titulo);
            values.put("cuerpo", cuerpo);
            values.put("fecha", fecha);
            values.put("hora", hora);
            values.put("checked", checked);
            values.put("milis", milis);
            values.put("tag", tag);
            values.put("color", color);

            id = db.insert(TABLE_EVENTS, null, values);
        }catch(Exception e){
            e.toString();
        }
        return id;
    }


    public List<Eventos> mostrarEventosMorado(){
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        List<Eventos> listaEventos = new ArrayList<>();
        Eventos eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE color = '#5252D3'", null);
        if(cursorEventos.moveToFirst()){
            do{
                eventos = new Eventos();
                eventos.setId(cursorEventos.getInt(0));
                eventos.setTitulo(cursorEventos.getString(1));
                eventos.setCuerpo(cursorEventos.getString(2));
                eventos.setFecha(cursorEventos.getString(3));
                eventos.setHora(cursorEventos.getString(4));
                eventos.setChecked(cursorEventos.getString(5));
                eventos.setColor(cursorEventos.getString(8));
                listaEventos.add(eventos);
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return listaEventos;
    }

    public List<Eventos> mostrarEventosPurpura(){
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        List<Eventos> listaEventos = new ArrayList<>();
        Eventos eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE color = '#9C4DD0'", null);
        if(cursorEventos.moveToFirst()){
            do{
                eventos = new Eventos();
                eventos.setId(cursorEventos.getInt(0));
                eventos.setTitulo(cursorEventos.getString(1));
                eventos.setCuerpo(cursorEventos.getString(2));
                eventos.setFecha(cursorEventos.getString(3));
                eventos.setHora(cursorEventos.getString(4));
                eventos.setChecked(cursorEventos.getString(5));
                eventos.setColor(cursorEventos.getString(8));
                listaEventos.add(eventos);
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return listaEventos;
    }

    public List<Eventos> mostrarEventosSalmon(){
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        List<Eventos> listaEventos = new ArrayList<>();
        Eventos eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE color = '#393A3C'", null);
        if(cursorEventos.moveToFirst()){
            do{
                eventos = new Eventos();
                eventos.setId(cursorEventos.getInt(0));
                eventos.setTitulo(cursorEventos.getString(1));
                eventos.setCuerpo(cursorEventos.getString(2));
                eventos.setFecha(cursorEventos.getString(3));
                eventos.setHora(cursorEventos.getString(4));
                eventos.setChecked(cursorEventos.getString(5));
                eventos.setColor(cursorEventos.getString(8));
                listaEventos.add(eventos);
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return listaEventos;
    }

    public List<Eventos> mostrarEventosAzul(){
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        List<Eventos> listaEventos = new ArrayList<>();
        Eventos eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE color = '#0D6AC6'", null);
        if(cursorEventos.moveToFirst()){
            do{
                eventos = new Eventos();
                eventos.setId(cursorEventos.getInt(0));
                eventos.setTitulo(cursorEventos.getString(1));
                eventos.setCuerpo(cursorEventos.getString(2));
                eventos.setFecha(cursorEventos.getString(3));
                eventos.setHora(cursorEventos.getString(4));
                eventos.setChecked(cursorEventos.getString(5));
                eventos.setColor(cursorEventos.getString(8));
                listaEventos.add(eventos);
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return listaEventos;
    }

    public List<Eventos> mostrarEventosRojo(){
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        List<Eventos> listaEventos = new ArrayList<>();
        Eventos eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE color = '#C60D4B'", null);
        if(cursorEventos.moveToFirst()){
            do{
                eventos = new Eventos();
                eventos.setId(cursorEventos.getInt(0));
                eventos.setTitulo(cursorEventos.getString(1));
                eventos.setCuerpo(cursorEventos.getString(2));
                eventos.setFecha(cursorEventos.getString(3));
                eventos.setHora(cursorEventos.getString(4));
                eventos.setChecked(cursorEventos.getString(5));
                eventos.setColor(cursorEventos.getString(8));
                listaEventos.add(eventos);
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return listaEventos;
    }

    public List<Eventos> mostrarEventos(){
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        List<Eventos> listaEventos = new ArrayList<>();
        Eventos eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);
        if(cursorEventos.moveToFirst()){
            do{
                eventos = new Eventos();
                eventos.setId(cursorEventos.getInt(0));
                eventos.setTitulo(cursorEventos.getString(1));
                eventos.setCuerpo(cursorEventos.getString(2));
                eventos.setFecha(cursorEventos.getString(3));
                eventos.setHora(cursorEventos.getString(4));
                eventos.setChecked(cursorEventos.getString(5));
                eventos.setColor(cursorEventos.getString(8));
                listaEventos.add(eventos);
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return listaEventos;
    }

    public List<Eventos> mostrarEventosCompletados(){
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        List<Eventos> listaEventos = new ArrayList<>();
        Eventos eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE checked = 'Completado'", null);
        if(cursorEventos.moveToFirst()){
            do{
                eventos = new Eventos();
                eventos.setId(cursorEventos.getInt(0));
                eventos.setTitulo(cursorEventos.getString(1));
                eventos.setCuerpo(cursorEventos.getString(2));
                eventos.setFecha(cursorEventos.getString(3));
                eventos.setHora(cursorEventos.getString(4));
                eventos.setChecked(cursorEventos.getString(5));
                eventos.setColor(cursorEventos.getString(8));
                listaEventos.add(eventos);
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return listaEventos;
    }

    public List<Eventos> mostrarEventosSinCompletar(){
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        List<Eventos> listaEventos = new ArrayList<>();
        Eventos eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE checked = 'Sin completar'", null);
        if(cursorEventos.moveToFirst()){
            do{
                eventos = new Eventos();
                eventos.setId(cursorEventos.getInt(0));
                eventos.setTitulo(cursorEventos.getString(1));
                eventos.setCuerpo(cursorEventos.getString(2));
                eventos.setFecha(cursorEventos.getString(3));
                eventos.setHora(cursorEventos.getString(4));
                eventos.setChecked(cursorEventos.getString(5));
                eventos.setColor(cursorEventos.getString(8));
                listaEventos.add(eventos);
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return listaEventos;
    }

    public List<Eventos> mostrarEventosExpirados(Long miliscurrent){
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        List<Eventos> listaEventos = new ArrayList<>();
        Eventos eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);
        if(cursorEventos.moveToFirst()){
            do{
                Double milis = cursorEventos.getDouble(6);
                Long res = milis.longValue() - miliscurrent;
                if(res < 0){
                    eventos = new Eventos();
                    eventos.setId(cursorEventos.getInt(0));
                    eventos.setTitulo(cursorEventos.getString(1));
                    eventos.setCuerpo(cursorEventos.getString(2));
                    eventos.setFecha(cursorEventos.getString(3));
                    eventos.setHora(cursorEventos.getString(4));
                    eventos.setChecked(cursorEventos.getString(5));
                    eventos.setColor(cursorEventos.getString(8));
                    listaEventos.add(eventos);
                }
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return listaEventos;
    }

    public List<Eventos> mostrarEventosNoExpirados(Long miliscurrent){
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        List<Eventos> listaEventos = new ArrayList<>();
        Eventos eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);
        if(cursorEventos.moveToFirst()){
            do{
                Double milis = cursorEventos.getDouble(6);
                Long res = milis.longValue() - miliscurrent;
                if(res > 0){
                    eventos = new Eventos();
                    eventos.setId(cursorEventos.getInt(0));
                    eventos.setTitulo(cursorEventos.getString(1));
                    eventos.setCuerpo(cursorEventos.getString(2));
                    eventos.setFecha(cursorEventos.getString(3));
                    eventos.setHora(cursorEventos.getString(4));
                    eventos.setChecked(cursorEventos.getString(5));
                    eventos.setColor(cursorEventos.getString(8));
                    listaEventos.add(eventos);
                }
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return listaEventos;
    }

    public int contarEventos(){
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        int contador = 0;

        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);
        if(cursorEventos.moveToFirst()){
            do{
                contador++;
            }while (cursorEventos.moveToNext());
        }
        cursorEventos.close();
        return contador;
    }

    public Eventos verEvento(int id) {
        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        Eventos eventos = null;
        Cursor cursorEventos = null;

        cursorEventos = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorEventos.moveToFirst()) {
            eventos = new Eventos();
            eventos.setId(cursorEventos.getInt(0));
            eventos.setTitulo(cursorEventos.getString(1));
            eventos.setCuerpo(cursorEventos.getString(2));
            eventos.setFecha(cursorEventos.getString(3));
            eventos.setHora(cursorEventos.getString(4));
            eventos.setChecked(cursorEventos.getString(5));
            eventos.setMilis(cursorEventos.getDouble(6));
            eventos.setTag(cursorEventos.getString(7));
            eventos.setColor(cursorEventos.getString(8));
        }
        cursorEventos.close();
        return eventos;
    }

    public boolean editarEvento(int id, String titulo, String cuerpo, String fecha, String hora, String checked, Double milis, String color){

        boolean correcto = false;

        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_EVENTS + " SET titulo = '"+ titulo + "', cuerpo = '" + cuerpo + "', fecha = '" + fecha + "', hora = '" + hora + "', checked = '" + checked + "', milis = '" + milis +  "', color = '" + color + "' WHERE id = '" + id + "' ");
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

        DbHelperEvents dbHelperEvents = new DbHelperEvents(context);
        SQLiteDatabase db = dbHelperEvents.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_EVENTS + " WHERE id = '" + id +"'");
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
