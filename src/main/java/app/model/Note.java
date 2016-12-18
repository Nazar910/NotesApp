package app.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * Created by pyvov on 15.12.2016.
 */
@Data
public class Note {
    private String title;
    private String text;
    private LocalDateTime remindDate;
}
