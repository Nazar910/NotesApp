package app.controller;

import app.model.Note;
import app.model.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 * Created by pyvov on 15.12.2016.
 */
@RestController
public class MainController {

    @Autowired
    NoteRepository noteRepository;

    @RequestMapping(value = "/notes/new-note",method = RequestMethod.POST)
    public void createNote(@RequestBody Note note){
        noteRepository.save(note);
    }

    @RequestMapping(value = "/notes/{id}",method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable BigInteger id){
        Note note = noteRepository.findById(id);
        noteRepository.delete(note);
    }

    @RequestMapping(value = "/notes/{id}",method = RequestMethod.POST)
    public void updateNote(@PathVariable BigInteger id,@RequestBody Note noteBody){
        Note note = noteRepository.findById(id);
        note.setText(noteBody.getText());
        note.setTitle(noteBody.getTitle());
        noteRepository.save(note);
    }

}
