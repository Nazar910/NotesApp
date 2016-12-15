package app.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by pyvov on 15.12.2016.
 */
@Data
public class Note {
    @Id
    private BigInteger id;
    private String title;
    private String text;
}
