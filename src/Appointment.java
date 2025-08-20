package src;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a pet appointment (vet visit, vaccination, grooming, etc.).
 * Stores type, date/time, and optional notes.
 */
public class Appointment implements Serializable {
    private String type;
    private LocalDateTime datetime;
    private String notes = null;

    public Appointment(String type, LocalDateTime datetime) {
        this.type = type;
        this.datetime = datetime;
    }

    public Appointment(String type, LocalDateTime datetime, String notes) {
        this.type = type;
        this.datetime = datetime;
        this.notes = notes;
    }

    public String getAppointmentType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return datetime;
    }

    public String getNotes() {
        return notes;
    }

    public void setAppointmentType(String type) {
        this.type = type;
    }

    public void setDateTime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        var formatedDatetime = datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        String appointmentInfo = "type: " + type + "\ndatetime: " + formatedDatetime;

        if (notes != null) {
            appointmentInfo += "\nnotes: " + notes;
        }

        return appointmentInfo;
    }
}