package app.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by pyvov on 17.12.2016.
 */
@Document
@CompoundIndexes({
        @CompoundIndex(name = "username", def = "{ 'username': 1 }", unique = true)
})
public class User {
    @Id
    private BigInteger id;
    @Indexed(unique = true)
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String email;
    private List<Note> notes = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, String email, List<Note> notes) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.notes = notes;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
