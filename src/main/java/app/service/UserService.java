package app.service;

import app.model.Note;
import app.model.User;

import java.math.BigInteger;

/**
 * Created by pyvov on 17.12.2016.
 */
public interface UserService {
    void createUser(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    void addNote(BigInteger userId,Note note);
    void updateNote(BigInteger userId,int noteId,Note noteBody);
    void deleteNote(BigInteger userId,int[] noteIds);
    void updateUser(BigInteger userId,User user);
    void deleteUser(BigInteger id);
}
