package src;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Pet with personal details and appointment history.
 * Stores owner info and allows scheduling appointments.
 */
public class Pet implements Serializable {
    private String id;
    private String name;
    private String breed;
    private int age;
    private String ownerName;
    private String contactInfo;
    private LocalDate registrationDate;
    private List<Appointment> appointments = new ArrayList<>();

    public Pet(String id, String name, String breed, int age, String ownerName,
            String contactInfo) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.ownerName = ownerName;
        this.contactInfo = contactInfo;
        this.registrationDate = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public List<Appointment> getAppointments() {
        // Return a copy of appoints list
        return new ArrayList<>(appointments);
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Pet))
            return false;

        Pet pet = (Pet) other;
        return id.equals(pet.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nName: " + name +
                "\nBreed: " + breed +
                "\nAge: " + age +
                "\nOwner Name: " + ownerName +
                "\nContact Info: " + contactInfo +
                "\nRegistration Date: " + registrationDate;
    }
}