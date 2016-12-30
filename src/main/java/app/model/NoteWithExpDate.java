package app.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by pyvov on 30.12.2016.
 */
public class NoteWithExpDate extends Note {
    private LocalDateTime remindDate;

    public NoteWithExpDate(String title, String text, LocalDateTime remindDate) {
        super(title, text);
        this.remindDate = remindDate;
    }

    public NoteWithExpDate(String title, String text) {
        super(title, text);
    }

    public NoteWithExpDate() {
    }

    public LocalDateTime getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(LocalDateTime remindDate) {
        this.remindDate = remindDate;
    }
}
