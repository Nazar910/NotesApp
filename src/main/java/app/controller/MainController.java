package app.controller;

import app.model.Note;
import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 * Created by pyvov on 15.12.2016.
 */
@RestController
public class MainController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/users/new-user",method = RequestMethod.POST)
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }

    @RequestMapping(value = "/users/{id}",method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable BigInteger id){
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/users/{id}",method = RequestMethod.POST)
    public void updateUser(@PathVariable BigInteger id,@RequestBody User userBody){
        userService.updateUser(id,userBody);
    }

    @RequestMapping(value = "/users/{id}/new-note",method = RequestMethod.POST)
    public void addNote(@PathVariable BigInteger id,@RequestBody Note note){
        userService.addNote(id,note);
    }

    @RequestMapping(value = "/users/{userId}/delete-note",method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable BigInteger userId,@RequestParam(value = "notes") int[] noteIds){
        userService.deleteNote(userId,noteIds);
    }

    @RequestMapping(value = "/users/{userId}/update-note/{noteId}",method = RequestMethod.POST)
    public void updateNote(@PathVariable(value = "userId") BigInteger userId,
                           @PathVariable(value = "noteId") int noteId,
                           @RequestBody Note noteBody){
        userService.updateNote(userId,noteId,noteBody);
    }
}
