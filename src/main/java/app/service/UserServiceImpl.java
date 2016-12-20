package app.service;

import app.exception.UserNotFoundException;
import app.model.Note;
import app.model.User;
import app.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;
import java.util.Collection;

/**
 * Created by pyvov on 17.12.2016.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> createUser(User user) {
        if (user != null) {
            userRepository.save(user);
            User result = userRepository.findByUsername(user.getUsername()).get();
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/users/{userId}")
                    .buildAndExpand(result.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email)
        );
    }

    @Override
    public ResponseEntity<?> deleteUser(BigInteger userId) {
        this.validateUser(userId);
        userRepository.delete(userId);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<?> addNote(BigInteger userId, Note note) {
        User user = this.validateUser(userId);
        if (note != null) {
            user.getNotes().add(note);
            userRepository.save(user);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/users/{userId}")
                    .buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> updateNote(BigInteger userId, int noteId, Note noteBody) {
        User user = this.validateUser(userId);
        Note note = user.getNotes().get(noteId);
        note.setText(noteBody.getText());
        note.setTitle(noteBody.getTitle());
        note.setRemindDate(noteBody.getRemindDate());
        userRepository.save(user);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<?> deleteNote(BigInteger userId, int[] noteIds) {
        User user = this.validateUser(userId);
        for (int i = noteIds.length - 1; i >= 0; i--) {
            int noteId = noteIds[i];
            user.getNotes().remove(noteId);
        }
        userRepository.save(user);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<?> updateUser(BigInteger userId, User userBody) {
        User user = this.validateUser(userId);
        if (!this.isEmptyString(userBody.getEmail())) {
            user.setEmail(userBody.getEmail());
        }
        if (!this.isEmptyString(userBody.getUsername())) {
            user.setUsername(userBody.getUsername());
        }
        if (!this.isEmptyString(userBody.getPassword())) {
            user.setPassword(userBody.getPassword());
        }
        if (userBody.getNotes() != null) {
            user.setNotes(userBody.getNotes());
        }
        userRepository.save(user);
        return ResponseEntity.accepted().build();
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Collection<Note> getNotes(BigInteger userId) {
        return this.validateUser(userId).getNotes();
    }

    @Override
    public User validateUser(BigInteger userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId.toString())
        );
    }

    private boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }
}
