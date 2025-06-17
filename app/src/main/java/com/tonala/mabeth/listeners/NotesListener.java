package com.tonala.mabeth.listeners;

import com.tonala.mabeth.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
