package src;

public enum AppointmentType {
    VET_VISIT,
    VACCINATION,
    GROOMING,
    OTHER;

    @Override
    public String toString() {
        switch (this) {
            case VET_VISIT:
                return "vet visit";
            case VACCINATION:
                return "vaccination";
            case GROOMING:
                return "grooming";
            default:
                return super.toString();
        }
    }

    public static AppointmentType fromString(String type) {
        switch (type) {
            case "vet visit":
                return VET_VISIT;
            case "vaccination":
                return VACCINATION;
            case "grooming":
                return GROOMING;
            default:
                throw new IllegalArgumentException("Invalid Appointment Type: " + type);
        }
    }
}
