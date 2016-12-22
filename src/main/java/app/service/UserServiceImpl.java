package app.service;

import app.exception.UserAlreadyExistsException;
import app.exception.UserNotFoundException;
import app.model.Note;
import app.model.User;
import app.model.UserRepository;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
        if (userIsNotNull(user) &&
                (!user.getUsername().equals("") ||
                        !user.getPassword().equals("") ||
                        !user.getEmail().equals(""))) {
            try {
                userRepository.save(user);
            } catch (Exception e) {
                e.printStackTrace();
                throw new UserAlreadyExistsException(user.getUsername());
            }
            User result = userRepository.findByUsername(user.getUsername()).get();
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/users/{username}")
                    .buildAndExpand(result.getUsername()).toUri();
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
    public ResponseEntity<?> deleteUser(String username) {
        this.validateUser(username);
        BigInteger userId = userRepository.findByUsername(username).get().getId();
        userRepository.delete(userId);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<?> addNote(String username, Note note) {
        User user = this.validateUser(username);
        if (noteIsNotNull(note) &&
                (!note.getText().equals("") ||
                        !note.getTitle().equals(""))) {
            user.getNotes().add(note);
            userRepository.save(user);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/users/{username}")
                    .buildAndExpand(user.getUsername()).toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> updateNote(String username, int noteId, Note noteBody) {
        User user = this.validateUser(username);
        Note note = user.getNotes().get(noteId);
        note.setText(noteBody.getText());
        note.setTitle(noteBody.getTitle());
        note.setRemindDate(noteBody.getRemindDate());
        userRepository.save(user);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<?> deleteNote(String username, int[] noteIds) {
        User user = this.validateUser(username);
        for (int i = noteIds.length - 1; i >= 0; i--) {
            int noteId = noteIds[i];
            user.getNotes().remove(noteId);
        }
        userRepository.save(user);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<?> updateUser(String username, User userBody) {
        User user = this.validateUser(username);
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
    public Collection<Note> getNotes(String username) {
        return this.validateUser(username).getNotes();
    }

    private User validateUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
    }

    private boolean userIsNotNull(User user) {
        return (user != null && user.getUsername() != null && user.getPassword() != null && user.getEmail() != null);
    }


    private boolean noteIsNotNull(Note note) {
        return (note != null && note.getText() != null && note.getText() != null);
    }

    private boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }

}
