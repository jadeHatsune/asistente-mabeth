package com.tonala.mabeth.adapters;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.tonala.mabeth.R;
import com.tonala.mabeth.entities.Note;
import com.tonala.mabeth.listeners.NotesListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder>{

    private List<Note> notes;
    private NotesListener notesListener;
    private Timer timer;
    private List<Note> notesSource;

    public NotesAdapter(List<Note> notes, NotesListener notesListener) {
        this.notes = notes; this.notesListener = notesListener; notesSource = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setNote(notes.get(position));
        holder.layoutnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesListener.onNoteClicked(notes.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textDate;
        LinearLayout layoutnote;
        RoundedImageView imageNote;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.textDate);
            textTitle = itemView.findViewById(R.id.textTitle);
            layoutnote = itemView.findViewById(R.id.layoutnote);
            imageNote = itemView.findViewById(R.id.imageNote);
        }

        void setNote(Note note){
            textTitle.setText(note.getTitle());
            textDate.setText(note.getDate());

            if (note.getColor() != null){
                Drawable background = layoutnote.getBackground();
                if (background instanceof ShapeDrawable) {
                    ShapeDrawable shapeDrawable = (ShapeDrawable) background;
                    shapeDrawable.getPaint().setColor(Color.parseColor(note.getColor()));
                } else if (background instanceof GradientDrawable) {
                    GradientDrawable gradientDrawable = (GradientDrawable) background;
                    gradientDrawable.setColor(Color.parseColor(note.getColor()));
                } else if (background instanceof ColorDrawable) {
                    ColorDrawable colorDrawable = (ColorDrawable) background;
                    colorDrawable.setColor(Color.parseColor(note.getColor()));
                }
                if (note.getNombredevariablepromedio() != null) {
                    imageNote.setImageBitmap(BitmapFactory.decodeFile(note.getNombredevariablepromedio()));
                    imageNote.setVisibility(View.VISIBLE);
                } else{
                    imageNote.setVisibility(View.GONE);
                }
            }else {
                layoutnote.setBackgroundColor(Color.parseColor("#6E727A"));
            }

        }
    }
    public void searchNotes(final String searchKeyboard){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyboard.trim().isEmpty()){
                    notes = notesSource;
                }else{
                    ArrayList<Note> temp = new ArrayList<>();
                    for (Note note : notesSource){
                        if(note.getTitle().toLowerCase().contains(searchKeyboard.toLowerCase()) || note.getNotetext().toLowerCase().contains(searchKeyboard.toLowerCase())){
                            temp.add(note);
                        }
                    }
                    notes = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run(){
                        notifyDataSetChanged();
                    }
        });
    }
    },500);
    }

    public void cancelTimer(){
        if(timer != null){
            timer.cancel();
        }
    }
}
