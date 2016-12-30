package app.controller;

import app.model.Note;
import app.model.NoteDto;
import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;

/**
 * Created by pyvov on 15.12.2016.
 */
@RestController
@RequestMapping("/api/users")
public class MainController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Collection<User> getUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Collection<Note> getNotes(@PathVariable String username) {
        return userService.getNotes(username);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User userBody) {
        return userService.updateUser(username, userBody);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.POST)
    public ResponseEntity<?> addNote(@PathVariable String username, @RequestBody NoteDto noteDto) {
        return userService.addNote(username, noteDto);
    }

    @RequestMapping(value = "/{username}/delete-note", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteNote(@PathVariable String username, @RequestParam(value = "notes") int[] noteIds) {
        return userService.deleteNote(username, noteIds);
    }

    @RequestMapping(value = "/{username}/{noteId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateNote(@PathVariable(value = "username") String username,
                           @PathVariable(value = "noteId") int noteId,
                           @RequestBody NoteDto noteDto) {
        return userService.updateNote(username, noteId, noteDto);
    }

}
