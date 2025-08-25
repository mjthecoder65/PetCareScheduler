package com.petscheduler.entity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class AppointmentTest {

    @Test
    void testToStringWithNotes() {
        Appointment appointment = new Appointment(
                AppointmentType.VET_VISIT,
                LocalDateTime.of(2025, 8, 21, 14, 30),
                "Bring medical reports"
        );

        String expected = """
                          type: vet visit
                          datetime: 2025-08-21 14:30
                          notes: Bring medical reports""";

        assertEquals(expected, appointment.toString());
    }

    @Test
    void testToStringWithoutNotes() {
        Appointment appointment = new Appointment(
                AppointmentType.GROOMING,
                LocalDateTime.of(2025, 8, 21, 9, 0)
        );

        String expected = """
                          type: grooming
                          datetime: 2025-08-21 09:00""";

        assertEquals(expected, appointment.toString());
    }

    @Test
    void testSettersAndGetters() {
        Appointment appointment = new Appointment(
                AppointmentType.OTHER,
                LocalDateTime.of(2025, 8, 21, 11, 0)
        );

        appointment.setNotes("Fasting required");
        assertEquals("Fasting required", appointment.getNotes());

        appointment.setType(AppointmentType.VACCINATION);
        assertEquals(AppointmentType.VACCINATION, appointment.getType());

        LocalDateTime newTime = LocalDateTime.of(2025, 8, 22, 16, 0);
        appointment.setDatetime(newTime);
        assertEquals(newTime, appointment.getDatetime());
    }
}
