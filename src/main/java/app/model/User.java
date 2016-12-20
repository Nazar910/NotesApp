package app.model;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by pyvov on 17.12.2016.
 */
@Data
public class User {
    @Id
    private BigInteger id;
    private String username;
    private String password;
    private String email;
    private List<Note> notes=new ArrayList<>();
}
