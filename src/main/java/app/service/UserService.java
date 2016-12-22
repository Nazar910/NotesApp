package app.service;

import app.model.Note;
import app.model.User;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.Collection;

/**
 * Created by pyvov on 17.12.2016.
 */
public interface UserService {
    ResponseEntity<?> createUser(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    ResponseEntity<?> addNote(String username, Note note);

    ResponseEntity<?> updateNote(String username, int noteId, Note noteBody);

    ResponseEntity<?> deleteNote(String username, int[] noteIds);

    ResponseEntity<?> updateUser(String username, User user);

    ResponseEntity<?> deleteUser(String username);

    Collection<User> findAll();

    Collection<Note> getNotes(String username);
}
