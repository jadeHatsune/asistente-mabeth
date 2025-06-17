package com.tonala.mabeth.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "notes")

public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "notetext")
    private String notetext;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "nombredevariablepromedio")
    private String nombredevariablepromedio;

    @ColumnInfo(name = "weblink")
    private String weblink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotetext() { return notetext; }

    public void setNotetext(String notetext) { this.notetext = notetext;}

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color;}

    public String getNombredevariablepromedio() {
        return nombredevariablepromedio;
    }

    public void setNombredevariablepromedio(String nombredevariablepromedio) {
        this.nombredevariablepromedio = nombredevariablepromedio;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    @NonNull
    @Override
    public String toString(){
        return title + " : " + date;
    }
}
