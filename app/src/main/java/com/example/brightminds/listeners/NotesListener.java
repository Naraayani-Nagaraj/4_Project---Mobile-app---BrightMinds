package com.example.brightminds.listeners;

import com.example.brightminds.entities.Note;

public interface NotesListener
{
    void onNoteClicked(Note note, int position);
}
