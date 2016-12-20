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

    ResponseEntity<?> addNote(BigInteger userId, Note note);

    ResponseEntity<?> updateNote(BigInteger userId, int noteId, Note noteBody);

    ResponseEntity<?> deleteNote(BigInteger userId, int[] noteIds);

    ResponseEntity<?> updateUser(BigInteger userId, User user);

    ResponseEntity<?> deleteUser(BigInteger userId);

    Collection<User> findAll();

    Collection<Note> getNotes(BigInteger userId);

    User validateUser(BigInteger userId);
}
