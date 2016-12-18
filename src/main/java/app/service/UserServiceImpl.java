package app.service;

import app.model.Note;
import app.model.User;
import app.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by pyvov on 17.12.2016.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUser(BigInteger id) {
        userRepository.delete(id);
    }

    @Override
    public void addNote(BigInteger userId,Note note) {
        User user = userRepository.findById(userId);
        user.addNote(note);
        userRepository.save(user);
    }

    @Override
    public void updateNote(BigInteger userId, int noteId, Note noteBody) {
        User user = userRepository.findById(userId);
        Note note = user.getNotes().get(noteId);
        note.setText(noteBody.getText());
        note.setTitle(noteBody.getTitle());
        note.setRemindDate(noteBody.getRemindDate());
        userRepository.save(user);
    }

    @Override
    public void deleteNote(BigInteger userId, int[] noteIds) {
        User user = userRepository.findById(userId);
        for(int i=noteIds.length-1;i>=0;i--){
            int noteId=noteIds[i];
            user.getNotes().remove(noteId);
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(BigInteger userId, User userBody) {
        User user = userRepository.findById(userId);
        user.setEmail(userBody.getEmail());
        user.setUsername(userBody.getUsername());
        user.setNotes(userBody.getNotes());
        user.setPassword(userBody.getPassword());
        userRepository.save(user);
    }
}
