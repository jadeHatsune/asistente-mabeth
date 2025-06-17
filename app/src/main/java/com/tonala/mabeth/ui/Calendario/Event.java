package com.tonala.mabeth.ui.Calendario;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
@RequiresApi(api = Build.VERSION_CODES.O)
public class Event
{
    public static ArrayList<Event> eventsLists = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsLists){
            if (event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time)
    {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsLists)
        {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if (event.getDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }

        return events;
    }


    private int id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private Double milis;

    public Event(int id, String name, LocalDate date, LocalTime time, Double milis)
    {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.milis = milis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getMilis() { return milis; }

    public void setMilis(Double milis) { this.milis = milis; }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
