package com.petscheduler.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import com.petscheduler.entity.Appointment;
import com.petscheduler.entity.AppointmentType;
import com.petscheduler.entity.Pet;


public class PetSchedulerService {
    private static final String PET_DATA_FILENAME = "pets.ser";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    private final Scanner scanner;
    private static Map<String, Pet> pets;


    public PetSchedulerService() {
        pets = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void registerPet() {

        System.out.print("Enter Pet Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Pet Breed: ");
        String breed = scanner.nextLine().trim();

        int age;

        try {
            System.out.print("Enter Pet's Age: ");
            age = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Age must a positive integer: " + e.getMessage());
            return;
        }

        System.out.print("Enter Pet Owner Name: ");
        String ownerName = scanner.nextLine().trim();

        System.out.print("Enter Pet Owner Contact Info: ");
        String contactInfo = scanner.nextLine().trim();

        String id = UUID.randomUUID().toString();
        Pet pet = new Pet(id, name, breed, age, ownerName, contactInfo);
        pets.put(id, pet);
        System.out.println("Pet is successfully registered, date: " + pet.getRegistrationDate());
    }

    public void scheduleAppointment() {
        System.out.print("Enter Pet ID: ");
        String id = scanner.nextLine().trim();
        Pet pet = pets.get(id);

        if (pet == null) {
            System.out.print("Error: Pet ID not found");
            return;
        }

        System.out.print("Enter appointment type: ");
        String appointmentTypeInput = scanner.nextLine().trim().toLowerCase();

        AppointmentType appointmentType;

        try {
            appointmentType = AppointmentType.fromString(appointmentTypeInput);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime datetime;

        try {
            System.out.printf("Enter date in the format (%s): ", DATETIME_FORMAT);
            String datetimeInput = scanner.nextLine().trim();
            datetime = LocalDateTime.parse(datetimeInput, DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format:  Use " + DATETIME_FORMAT);
            return;
        }

        if (datetime.isBefore(today)) {
            System.out.println("Error: " + datetime + " is before " + today);
            return;
        }

        System.out.print("Do you want to add some notes (yes/no): ");
        String inputChoice = scanner.nextLine().trim();

        String notes = null;

        if (inputChoice.equalsIgnoreCase("yes")) {
            System.out.print("Enter appointment notes: ");
            notes = scanner.nextLine();
        }

        Appointment appointment = new Appointment(appointmentType, datetime);

        if (notes != null) {
            appointment.setNotes(notes);
        }

        pet.addAppointment(appointment);
        System.out
                .println("Scheduled an appointment for the pet " + pet.getName() + " On " + appointment.getDatetime());
    }

    public void savePetsDataToFile() {
        if (!pets.isEmpty()) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(PET_DATA_FILENAME))) {
                out.writeObject(pets);
                System.out
                        .println("Saved " + pets.size() + " pet record(s). Thank you for using PetScheduler service");
            } catch (IOException e) {
                System.out.println("Failed to save pets data to the file: " + e.getMessage());
            }
        } else {
            System.out.println("There are current not pet records");
        }
    }

    @SuppressWarnings("unchecked")
    public void loadPetsDataFromFile() {
        try (var in = new ObjectInputStream(new FileInputStream(PET_DATA_FILENAME))) {
            pets = (Map<String, Pet>) in.readObject();
            if (!pets.isEmpty())
                System.out.println("Loaded " + pets.size() + " pet record(s) from file");
            else
                System.out.println("No Pet records saved.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Not saved pets data: " + e.getMessage());
        }
    }

    public void displayPets() {
        if (pets.isEmpty()) {
            System.out.println("There are not pets registered");
            return;
        }

        System.out.println("------Registered pets----");
        for (Pet pet : pets.values()) {
            System.out.println(pet); // Print stringified version of pet.
        }
    }

    public void displayPetAppointments() {
        System.out.print("Enter Pet ID: ");
        String id = scanner.nextLine().trim();

        Pet pet = pets.get(id);
        if (pet == null) {
            System.out.println("Pet ID not found");
            return;
        }

        List<Appointment> appointments = pet.getAppointments();
        appointments.sort(Comparator.comparing(Appointment::getDatetime));

        if (appointments.isEmpty()) {
            System.out.println("Has not appoints");
        }

        for (var appointment : appointments) {
            System.out.println(appointment);
        }
    }

    public void displayPetsUpcomingAppointments() {
        if (pets.isEmpty()) {
            System.out.println("No registered pets.");
            return;
        }
        LocalDateTime today = LocalDateTime.now();

        for (Pet pet : pets.values()) {
            System.out.println("______Upcoming Appointments for Pet: " + pet.getName() + "______");
            for (var appointment : pet.getAppointments()) {
                if (appointment.getDatetime().isAfter(today)) {
                    System.out.println(appointment);
                }
            }
        }
    }

    public void displayPetPastAppointments() {
        System.out.print("Enter Pet ID: ");
        String id = scanner.nextLine().trim();
        Pet pet = pets.get(id);

        if (pet == null) {
            System.out.println("Pet ID not found");
            return;
        }

        var appointments = pet.getAppointments();
        if (appointments.isEmpty()) {
            System.out.println("Pet with ID " + pet.getId() + " has not scheduled appointments");
            return;
        }

        LocalDateTime today = LocalDateTime.now();
        System.out.println("______Past Appointments for Pet " + pet.getId() + "_______");
        for (Appointment appointment : appointments) {
            if (appointment.getDatetime().isBefore(today)) {
                System.out.println(appointment);
            }
        }
    }

    private boolean isNextWeek(LocalDateTime datetime) {
        LocalDateTime today = LocalDateTime.now();

        LocalDateTime startOfNextWeek = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDateTime endOfNextWeek = startOfNextWeek.plusDays(6);

        return (datetime.isAfter(startOfNextWeek) || datetime.isEqual(startOfNextWeek))
                && datetime.isBefore(endOfNextWeek);
    }

    public void generateReport() {
        if (pets.isEmpty()) {
            System.out.println("There are not pets registered");
            return;
        }

        System.out.println("***************** Pets with appointments in the next week ************");
        LocalDateTime today = LocalDateTime.now();

        for (Pet pet : pets.values()) {
            for (Appointment appointment : pet.getAppointments()) {
                if (appointment.getDatetime().isBefore(today)) {
                    continue;
                }
                if (isNextWeek(appointment.getDatetime())) {
                    System.out.println(pet);
                    break;
                }
            }
        }
        
        System.out.println("****************** Pets with Overdue Vet Visit **************");
        for (Pet pet : pets.values()) {
            var appointments = pet.getAppointments();
            int n = appointments.size();
            if (n == 0)
                continue;
            var lastAppointment = appointments.get(n - 1);
            var lastSixMonth = LocalDateTime.now().minusMonths(6);

            if (lastAppointment.getDatetime().isBefore(lastSixMonth)) {
                System.out.println(pet);
            }
        }
    }
}