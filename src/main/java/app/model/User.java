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
@Data
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
}
