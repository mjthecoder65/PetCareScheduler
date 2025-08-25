package com.petscheduler;

import java.util.Scanner;

import com.petscheduler.service.PetSchedulerService;

public class Main {
   private static final Scanner scanner = new Scanner(System.in);
   public static void main(String[] args) {
        PetSchedulerService service = new PetSchedulerService();
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
                case "1" -> service.registerPet();
                case "2" -> service.scheduleAppointment();
                case "3" -> service.displayPets();
                case "4" -> service.displayPetAppointments();
                case "5" -> service.displayPetsUpcomingAppointments();
                case "6" -> service.displayPetPastAppointments();
                case "7" -> service.generateReport();
                case "8" -> {
                    service.savePetsDataToFile();
                    running = false;
                }
                default -> System.out.println("Unsuported choice. Please choose 1 and 8");

            }

        }
    } 
}
