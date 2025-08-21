# PetCareScheduler - Pet Management & Appointment Scheduling Console App

![Java CI](https://github.com/mjthecoder65/PetCareScheduler/actions/workflows/maven.yml/badge.svg)

## Overview

PetCareScheduler is a Java console application designed to help pet owners and veterinarians manage pets and their appointments. It allows you to register pets, schedule appointments (vet visits, vaccinations, grooming), and generate useful reports for upcoming and overdue appointments.

---

## Features

- **Register Pets**
  Add pets with details including ID, name, breed, age, owner name, and contact information.

- **Schedule Appointments**
  Schedule appointments for pets with type, date/time, and optional notes.

- **View Pets and Appointments**

  - View all registered pets.
  - View all appointments for a specific pet.
  - View upcoming appointments.
  - View past appointment history.

- **Reports**

  - Generate reports for pets with appointments in the next week.
  - Generate reports for pets with overdue vet visits (older than 6 months).

- **Data Persistence**

  - All pet and appointment data is automatically saved to `pets.ser` on exit.
  - Data is loaded on application start if the file exists.

---

---

## Technologies

- **Programming Language:** Java 17
- **Build Tool:** Maven
- **Testing Framework:** JUnit 5
- **IDE/Editor:** VSCode (with Java Extension Pack)
- **Version Control:** Git

---

## Installation

1. **Clone the repository**

```bash
git clone https://github.com/mjthecoder65/PetCareScheduler.git
cd PetCareScheduler
```

2. **Compile the source code**

```bash
mvn compile
```

3. **Run Tests**

```bash
mvn clean test
```

4. **Run the application**

```bash
mvn exec:java
```

---

## Usage

1. Run the program to see the main menu.
2. Choose an option (1-8) from the menu to perform the action you want.

**Example Interaction:**

```
============= PetCare Scheduler =============
  1. Register a Pet
  2. Schedule an Appointment
  3. View All Pets
  4. View All Appointments For a Pet
  5. View Upcoming Appointments
  6. View Past Appointment History For a Pet
  7. Generate Report
  8. Save and Exit
==========================================
Choose an option: 1
Enter Pet Name: Fluffy
Enter Pet Breed: Persian
Enter Pet's Age: 3
Enter Pet Owner Name: John Doe
Enter Pet Owner Contact Info: 123-456-7890
Pet is successfully registered, date: 2025-08-20
```

---

## Data Persistence

- Pet and appointment data are saved to `pets.ser` when you exit the application.
- On starting the application, existing data is automatically loaded.
- If `pets.ser` does not exist, the program will start with an empty database.

---

## Future Enhancements

- Add functionality to **edit or delete** pets and appointments.
- Implement **automated reminders** for upcoming appointments.
- Support **search pets** by name, breed, or owner.
- Migrate data persistence from **Java serialization** to **JSON or a database**.
- Implement **unit and integration tests** for better maintainability.

---

## License

This project is licensed under the **MIT License**.
