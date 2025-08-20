package src;

import java.util.Scanner;
import java.util.Set;
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
import java.util.UUID;

/**
 * Main console menu for PetScheduler app.
 * Handles user interaction and calls PetService methods.
 */

public class PetScheduler {
    private static final String PET_DATA_FILENAME = "pets.ser";
    private static final Set<String> APPOINTMENT_TYPES = Set.of("vet visit", "vaccination", "grooming");
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Pet> pets = new HashMap<>();

    public static void main(String[] args) {
        loadPetsDataFromFile();
        boolean running = true;
        while (running) {
            System.out.println("\n============= PetCare Scheduller Menu =============");
            System.out.println("  1. Register a Pet");
            System.out.println("  2. Schedule an Appointment");
            System.out.println("  3. View All Pets");
            System.out.println("  4. View All Appointments For a Pet");
            System.out.println("  5. View Upcoming Appointments");
            System.out.println("  6. View Past Appointment History For a Pet");
            System.out.println("  7. Generate Report");
            System.out.println("  8. Save and Exit");
            System.out.println("==========================================");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    registerPet();
                    break;
                case "2":
                    scheduleAppointment();
                    break;
                case "3":
                    displayPets();
                    break;
                case "4":
                    displayPetAppointments();
                    break;
                case "5":
                    displayPetsUpcomingAppointments();
                    break;
                case "6":
                    displayPetPastAppointments();
                    break;
                case "7":
                    generateReport();
                    break;
                case "8":
                    savePetsDataToFile();
                    running = false;
                    break;
                default:
                    System.out.println("Unsuported choice. Please choose 1 and 8");
                    break;
            }

        }
    }

    public static void registerPet() {

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

    public static void scheduleAppointment() {
        System.out.print("Enter Pet ID: ");
        String id = scanner.nextLine().trim();
        Pet pet = pets.get(id);

        if (pet == null) {
            System.out.print("Error: Pet ID not found");
            return;
        }

        System.out.print("Enter appointment type: ");
        String appointmentType = scanner.nextLine().trim().toLowerCase();

        if (!APPOINTMENT_TYPES.contains(appointmentType)) {
            System.out.println("Error: Appoint type now supported: " + appointmentType);
            return;
        }

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime datetime;

        try {
            System.out.print("Enter date in the format (yyyy-MM-dd HH:mm): ");
            String datetimeInput = scanner.nextLine().trim();
            datetime = LocalDateTime.parse(datetimeInput, DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format:  Use yyyy-MM-dd HH:mm");
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
                .println("Scheduled an appointment for the pet " + pet.getName() + " On " + appointment.getDateTime());
    }

    public static void savePetsDataToFile() {
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
    public static void loadPetsDataFromFile() {
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

    public static void displayPets() {
        if (pets.isEmpty()) {
            System.out.println("There are not pets registered");
            return;
        }

        System.out.println("------Registered pets----");
        for (Pet pet : pets.values()) {
            System.out.println(pet); // Print stringified version of pet.
        }
    }

    public static void displayPetAppointments() {
        System.out.print("Enter Pet ID: ");
        String id = scanner.nextLine().trim();

        Pet pet = pets.get(id);
        if (pet == null) {
            System.out.println("Pet ID not found");
            return;
        }

        List<Appointment> appointments = pet.getAppointments();
        appointments.sort(Comparator.comparing(Appointment::getDateTime));

        if (appointments.size() == 0) {
            System.out.println("Has not appoints");
        }

        for (var appointment : appointments) {
            System.out.println(appointment);
        }
    }

    public static void displayPetsUpcomingAppointments() {
        if (pets.isEmpty()) {
            System.out.println("No registered pets.");
            return;
        }
        LocalDateTime today = LocalDateTime.now();

        for (Pet pet : pets.values()) {
            System.out.println("______Upcoming Appointments for Pet: " + pet.getName() + "______");
            for (var appointment : pet.getAppointments()) {
                if (appointment.getDateTime().isAfter(today)) {
                    System.out.println(appointment);
                }
            }
        }
    }

    public static void displayPetPastAppointments() {
        System.out.print("Enter Pet ID: ");
        String id = scanner.nextLine().trim();
        Pet pet = pets.get(id);

        if (pet == null) {
            System.out.println("Pet ID not found");
            return;
        }

        var appointments = pet.getAppointments();
        if (appointments.size() == 0) {
            System.out.println("Pet with ID " + pet.getId() + " has not scheduled appointments");
            return;
        }

        LocalDateTime today = LocalDateTime.now();
        System.out.println("______Past Appointments for Pet " + pet.getId() + "_______");
        for (Appointment appointment : appointments) {
            if (appointment.getDateTime().isBefore(today)) {
                System.out.println(appointment);
            }
        }
    }

    private static boolean isNextWeek(LocalDateTime datetime) {
        LocalDateTime today = LocalDateTime.now();

        LocalDateTime startOfNextWeek = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDateTime endOfNextWeek = startOfNextWeek.plusDays(6);

        return (datetime.isAfter(startOfNextWeek) || datetime.isEqual(startOfNextWeek))
                && datetime.isBefore(endOfNextWeek);
    }

    private static void generateReport() {
        if (pets.isEmpty()) {
            System.out.println("There are not pets registered");
            return;
        }

        System.out.println("***************** Pets with appointments in the next week ************");
        LocalDateTime today = LocalDateTime.now();

        for (Pet pet : pets.values()) {
            for (Appointment appointment : pet.getAppointments()) {
                if (appointment.getDateTime().isBefore(today)) {
                    continue;
                }
                if (isNextWeek(appointment.getDateTime())) {
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

            if (lastAppointment.getDateTime().isBefore(lastSixMonth)) {
                System.out.println(pet);
            }
        }
    }
}