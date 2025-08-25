package com.petscheduler.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a pet appointment (vet visit, vaccination, grooming, etc.).
 * Stores type, date/time, and optional notes.
 */
public class Appointment implements Serializable {
    private AppointmentType type;
    private LocalDateTime datetime;
    private String notes = null;

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Appointment(AppointmentType type, LocalDateTime datetime) {
        this.type = type;
        this.datetime = datetime;
    }

    public Appointment(AppointmentType type, LocalDateTime datetime, String notes) {
        this.type = type;
        this.datetime = datetime;
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