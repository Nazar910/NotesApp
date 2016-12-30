package app.model;

import java.time.LocalDateTime;

/**
 * Created by pyvov on 30.12.2016.
 */
public class NoteDto extends NoteWithExpDate {
    public NoteDto(String title, String text, LocalDateTime remindDate) {
        super(title, text, remindDate);
    }

    public NoteDto(String title, String text) {
        super(title, text);
    }

    public NoteDto() {
    }
}
