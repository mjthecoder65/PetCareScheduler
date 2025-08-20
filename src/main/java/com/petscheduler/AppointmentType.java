package com.petscheduler;

public enum AppointmentType {
    VET_VISIT,
    VACCINATION,
    GROOMING,
    OTHER;

    @Override
    public String toString() {
        return switch (this) {
            case VET_VISIT -> "vet visit";
            case VACCINATION -> "vaccination";
            case GROOMING -> "grooming";
            default -> super.toString();
        };
    }

    public static AppointmentType fromString(String type) {
        return switch (type) {
            case "vet visit" -> VET_VISIT; 
            case "vaccination" -> VACCINATION;
            case "grooming" -> GROOMING;
            default -> throw new IllegalArgumentException("Invalid Appointment Type: " + type);
        };
    }
}
