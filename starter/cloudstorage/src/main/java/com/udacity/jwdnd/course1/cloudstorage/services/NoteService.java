package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(String title, String description, int userId){
        return noteMapper.insertNote(new Note(null, title, description, userId));
    }

    public Note getNote(int noteId){
        return noteMapper.selectNote(noteId);
    }

    public void editNote(int noteId, String title, String description){
        noteMapper.updateNote(noteId, title, description);
    }

    public void deleteNote(int noteId){
        noteMapper.deleteNote(noteId);
    }

    public ArrayList<Note> getAllUserNotes(int userid){
        return noteMapper.getAllUserNotes(userid);
    }


}
