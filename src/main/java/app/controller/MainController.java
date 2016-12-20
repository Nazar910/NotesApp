package app.controller;

import app.model.Note;
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
@RequestMapping("/users")
public class MainController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Collection<User> getUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Collection<Note> getNotes(@PathVariable BigInteger userId) {
        return userService.getNotes(userId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable BigInteger userId) {
        return userService.deleteUser(userId);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable BigInteger userId, @RequestBody User userBody) {
        return userService.updateUser(userId, userBody);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity<?> addNote(@PathVariable BigInteger userId, @RequestBody Note note) {
        return userService.addNote(userId, note);
    }

    @RequestMapping(value = "/{userId}/delete-note", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteNote(@PathVariable BigInteger userId, @RequestParam(value = "notes") int[] noteIds) {
        return userService.deleteNote(userId, noteIds);
    }

    @RequestMapping(value = "/{userId}/{noteId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateNote(@PathVariable(value = "userId") BigInteger userId,
                           @PathVariable(value = "noteId") int noteId,
                           @RequestBody Note noteBody) {
        return userService.updateNote(userId, noteId, noteBody);
    }

}
