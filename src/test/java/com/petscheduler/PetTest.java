package com.petscheduler;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PetTest {
    @Test
    public void testAddAppointment() {
        Pet pet = new Pet(
                UUID.randomUUID().toString(),
                "Fuffy",
                "Persion",
                10,
                "Michael",
                "michael@gmail.com");

        Appointment appointment = new Appointment(
                AppointmentType.VET_VISIT,
                LocalDateTime.now().plusDays(1));

        pet.addAppointment(appointment);
        assertEquals(1, pet.getAppointments().size());
        assertEquals(appointment, pet.getAppointments().get(0));
    }
}
