/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.medicareadmission_hospitalsystem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mnqob
 */
public class MediCareAdmission_HospitalSystem {

    // =========================
    // PATIENT CATEGORY
    // =========================
    enum PatientCategory {
        INPATIENT,
        OUTPATIENT,
        EMERGENCY
    }

    // =========================
    // PATIENT CLASS
    // =========================
    static class Patient {

        private String patientId;
        private String firstName;
        private String lastName;
        private int age;
        private String gender;
        private String medicalCondition;
        private PatientCategory category;

        public Patient(String patientId, String firstName, String lastName,
                       int age, String gender, String medicalCondition,
                       PatientCategory category) {

            this.patientId = patientId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.gender = gender;
            this.medicalCondition = medicalCondition;
            this.category = category;
        }

        public String getPatientId() {
            return patientId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public int getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }

        public String getMedicalCondition() {
            return medicalCondition;
        }

        public PatientCategory getCategory() {
            return category;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setMedicalCondition(String medicalCondition) {
            this.medicalCondition = medicalCondition;
        }

        public void displayDetails() {
            System.out.println("Patient ID: " + patientId);
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Age: " + age);
            System.out.println("Gender: " + gender);
            System.out.println("Medical Condition: " + medicalCondition);
            System.out.println("Patient Category: " + category);
        }

        @Override
        public String toString() {
            return patientId + " | "
                    + firstName + " "
                    + lastName + " | Age: "
                    + age + " | Gender: "
                    + gender + " | Condition: "
                    + medicalCondition + " | Category: "
                    + category;
        }
    }

    // =========================
    // INPATIENT CLASS
    // =========================
    static class Inpatient extends Patient {

        private String wardNumber;
        private String bedNumber;

        public Inpatient(String patientId, String firstName,
                         String lastName, int age, String gender,
                         String medicalCondition, String wardNumber,
                         String bedNumber) {

            super(patientId, firstName, lastName, age, gender,
                    medicalCondition, PatientCategory.INPATIENT);

            this.wardNumber = wardNumber;
            this.bedNumber = bedNumber;
        }

        public String getWardNumber() {
            return wardNumber;
        }

        public String getBedNumber() {
            return bedNumber;
        }

        public void setBedNumber(String bedNumber) {
            this.bedNumber = bedNumber;
        }

        @Override
        public void displayDetails() {

            super.displayDetails();

            System.out.println("Ward Number: " + wardNumber);

            if (bedNumber == null) {
                System.out.println("Bed Number: Not allocated");
            } else {
                System.out.println("Bed Number: " + bedNumber);
            }
        }

        @Override
        public String toString() {

            String bed;

            if (bedNumber == null) {
                bed = "Not allocated";
            } else {
                bed = bedNumber;
            }

            return super.toString()
                    + " | Ward: " + wardNumber
                    + " | Bed: " + bed;
        }
    }

    // =========================
    // BED CLASS
    // =========================
    static class Bed {

        private String bedNumber;
        private boolean occupied;
        private String patientId;

        public Bed(String bedNumber) {
            this.bedNumber = bedNumber;
            occupied = false;
            patientId = null;
        }

        public String getBedNumber() {
            return bedNumber;
        }

        public boolean isOccupied() {
            return occupied;
        }

        public String getPatientId() {
            return patientId;
        }

        public boolean allocate(String patientId) {

            if (occupied) {
                return false;
            }

            occupied = true;
            this.patientId = patientId;

            return true;
        }

        public void release() {
            occupied = false;
            patientId = null;
        }

        @Override
        public String toString() {

            if (occupied) {
                return bedNumber
                        + " - OCCUPIED by Patient "
                        + patientId;
            }

            return bedNumber + " - AVAILABLE";
        }
    }

    // =========================
    // HOSPITAL SYSTEM CLASS
    // =========================
    static class HospitalSystem {

        public static final int TOTAL_BEDS = 20;
        public static final String WARD_NUMBER = "Ward 1";

        private List<Patient> patients;
        private Bed[] beds;

        public HospitalSystem() {

            patients = new ArrayList<>();

            beds = new Bed[TOTAL_BEDS];

            for (int i = 0; i < TOTAL_BEDS; i++) {

                String bedNumber =
                        String.format("B%02d", i + 1);

                beds[i] = new Bed(bedNumber);
            }
        }

        // Register patient
        public boolean registerPatient(Patient patient) {

            if (patient == null) {
                return false;
            }

            if (searchPatient(patient.getPatientId()) != null) {
                return false;
            }

            patients.add(patient);

            return true;
        }

        // Search patient
        public Patient searchPatient(String patientId) {

            for (Patient patient : patients) {

                if (patient.getPatientId()
                        .equalsIgnoreCase(patientId)) {

                    return patient;
                }
            }

            return null;
        }

        // Update patient
        public boolean updatePatient(String patientId,
                                     String firstName,
                                     String lastName,
                                     int age,
                                     String gender,
                                     String medicalCondition) {

            Patient patient = searchPatient(patientId);

            if (patient == null) {
                return false;
            }

            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setAge(age);
            patient.setGender(gender);
            patient.setMedicalCondition(medicalCondition);

            return true;
        }

        // Delete patient
        public boolean deletePatient(String patientId) {

            Patient patient = searchPatient(patientId);

            if (patient == null) {
                return false;
            }

            if (patient instanceof Inpatient) {

                Inpatient inpatient =
                        (Inpatient) patient;

                if (inpatient.getBedNumber() != null) {
                    releaseBed(inpatient.getBedNumber());
                }
            }

            patients.remove(patient);

            return true;
        }

        // Allocate bed
        public boolean allocateBed(String patientId,
                                   String bedNumber) {

            Patient patient = searchPatient(patientId);

            if (!(patient instanceof Inpatient)) {
                return false;
            }

            Inpatient inpatient =
                    (Inpatient) patient;

            if (inpatient.getBedNumber() != null) {
                return false;
            }

            Bed bed = findBed(bedNumber);

            if (bed == null || bed.isOccupied()) {
                return false;
            }

            bed.allocate(patientId);

            inpatient.setBedNumber(bed.getBedNumber());

            return true;
        }

        // Release bed
        public boolean releaseBed(String bedNumber) {

            Bed bed = findBed(bedNumber);

            if (bed == null || !bed.isOccupied()) {
                return false;
            }

            String patientId = bed.getPatientId();

            Patient patient = searchPatient(patientId);

            if (patient instanceof Inpatient) {

                Inpatient inpatient =
                        (Inpatient) patient;

                inpatient.setBedNumber(null);
            }

            bed.release();

            return true;
        }

        // Find bed
        public Bed findBed(String bedNumber) {

            for (Bed bed : beds) {

                if (bed.getBedNumber()
                        .equalsIgnoreCase(bedNumber)) {

                    return bed;
                }
            }

            return null;
        }

        // Get all patients
        public List<Patient> getAllPatients() {
            return new ArrayList<>(patients);
        }

        // Available beds
        public List<Bed> getAvailableBeds() {

            List<Bed> available =
                    new ArrayList<>();

            for (Bed bed : beds) {

                if (!bed.isOccupied()) {
                    available.add(bed);
                }
            }

            return available;
        }

        // Occupied beds
        public List<Bed> getOccupiedBeds() {

            List<Bed> occupied =
                    new ArrayList<>();

            for (Bed bed : beds) {

                if (bed.isOccupied()) {
                    occupied.add(bed);
                }
            }

            return occupied;
        }

        // Sort by surname
        public List<Patient> sortBySurname() {

            List<Patient> sorted =
                    getAllPatients();

            sorted.sort(
                    Comparator.comparing(
                            Patient::getLastName,
                            String.CASE_INSENSITIVE_ORDER
                    )
            );

            return sorted;
        }

        // Sort by Patient ID
        public List<Patient> sortById() {

            List<Patient> sorted =
                    getAllPatients();

            sorted.sort(
                    Comparator.comparing(
                            Patient::getPatientId
                    )
            );

            return sorted;
        }

        public int getRegisteredPatientCount() {
            return patients.size();
        }

        public int getOccupiedBedCount() {
            return getOccupiedBeds().size();
        }

        public double getOccupancyPercentage() {

            return (getOccupiedBedCount()
                    * 100.0) / TOTAL_BEDS;
        }

        // Display ward layout
        public void displayWardLayout() {

            System.out.println();
            System.out.println(
                    "========== WARD 1 LAYOUT =========="
            );

            for (int i = 0; i < beds.length; i++) {

                System.out.printf(
                        "%-25s",
                        beds[i]
                );

                if ((i + 1) % 5 == 0) {
                    System.out.println();
                }
            }
        }

        // Display patients
        public void displayPatients() {

            System.out.println();
            System.out.println(
                    "========== REGISTERED PATIENTS =========="
            );

            if (patients.isEmpty()) {

                System.out.println(
                        "No patients registered."
                );

                return;
            }

            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }

        // Display available beds
        public void displayAvailableBeds() {

            System.out.println();
            System.out.println(
                    "========== AVAILABLE BEDS =========="
            );

            for (Bed bed : getAvailableBeds()) {
                System.out.println(bed);
            }
        }

        // Display occupied beds
        public void displayOccupiedBeds() {

            System.out.println();
            System.out.println(
                    "========== OCCUPIED BEDS =========="
            );

            if (getOccupiedBeds().isEmpty()) {

                System.out.println(
                        "No beds are occupied."
                );

                return;
            }

            for (Bed bed : getOccupiedBeds()) {
                System.out.println(bed);
            }
        }

        // Reports
        public void displayReport() {

            System.out.println();
            System.out.println(
                    "========== WARD REPORT =========="
            );

            System.out.println(
                    "Registered Patients: "
                    + getRegisteredPatientCount()
            );

            System.out.println(
                    "Total Beds: "
                    + TOTAL_BEDS
            );

            System.out.println(
                    "Occupied Beds: "
                    + getOccupiedBedCount()
            );

            System.out.println(
                    "Available Beds: "
                    + getAvailableBeds().size()
            );

            System.out.printf(
                    "Ward Occupancy: %.2f%%%n",
                    getOccupancyPercentage()
            );
        }
    }

    // =========================
    // MAIN PROGRAM
    // =========================

    private static Scanner scanner =
            new Scanner(System.in);

    private static HospitalSystem hospital =
            new HospitalSystem();

    public static void main(String[] args) {

        boolean running = true;

        System.out.println(
                "========================================"
        );

        System.out.println(
                "        MEDICARE HOSPITAL"
        );

        System.out.println(
                "      PATIENT ADMISSION SYSTEM"
        );

        System.out.println(
                "========================================"
        );

        while (running) {

            displayMenu();

            int choice =
                    readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    registerPatient();
                    break;

                case 2:
                    searchPatient();
                    break;

                case 3:
                    updatePatient();
                    break;

                case 4:
                    deletePatient();
                    break;

                case 5:
                    hospital.displayPatients();
                    break;

                case 6:
                    allocateBed();
                    break;

                case 7:
                    releaseBed();
                    break;

                case 8:
                    hospital.displayWardLayout();
                    break;

                case 9:
                    hospital.displayAvailableBeds();
                    break;

                case 10:
                    hospital.displayOccupiedBeds();
                    break;

                case 11:
                    hospital.displayReport();
                    break;

                case 12:
                    sortPatients();
                    break;

                case 0:
                    running = false;

                    System.out.println(
                            "Thank you for using MediCare Hospital."
                    );

                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }

        scanner.close();
    }

    private static void displayMenu() {

        System.out.println();
        System.out.println(
                "============== MAIN MENU =============="
        );

        System.out.println("1. Register Patient");
        System.out.println("2. Search Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");
        System.out.println("5. Display All Patients");
        System.out.println("6. Allocate Bed");
        System.out.println("7. Release Bed");
        System.out.println("8. Display Ward Layout");
        System.out.println("9. Display Available Beds");
        System.out.println("10. Display Occupied Beds");
        System.out.println("11. Generate Reports");
        System.out.println("12. Sort Patients");
        System.out.println("0. Exit");

        System.out.println(
                "======================================="
        );
    }

    private static void registerPatient() {

        System.out.println();
        System.out.println(
                "========== REGISTER PATIENT =========="
        );

        String id =
                readText("Patient ID: ");

        if (hospital.searchPatient(id) != null) {

            System.out.println(
                    "Patient ID already exists."
            );

            return;
        }

        String firstName =
                readText("First Name: ");

        String lastName =
                readText("Last Name: ");

        int age =
                readInt("Age: ");

        String gender =
                readText("Gender: ");

        String condition =
                readText("Medical Condition: ");

        System.out.println();
        System.out.println("1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");

        int category =
                readInt("Select category: ");

        Patient patient;

        if (category == 1) {

            patient = new Inpatient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    HospitalSystem.WARD_NUMBER,
                    null
            );

        } else if (category == 2) {

            patient = new Patient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    PatientCategory.OUTPATIENT
            );

        } else if (category == 3) {

            patient = new Patient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    PatientCategory.EMERGENCY
            );

        } else {

            System.out.println(
                    "Invalid category."
            );

            return;
        }

        if (hospital.registerPatient(patient)) {

            System.out.println(
                    "Patient registered successfully."
            );

        } else {

            System.out.println(
                    "Patient registration failed."
            );
        }
    }

    private static void searchPatient() {

        String id =
                readText("Enter Patient ID: ");

        Patient patient =
                hospital.searchPatient(id);

        if (patient == null) {

            System.out.println(
                    "Patient not found."
            );

        } else {

            System.out.println();

            patient.displayDetails();
        }
    }

    private static void updatePatient() {

        String id =
                readText("Enter Patient ID: ");

        if (hospital.searchPatient(id) == null) {

            System.out.println(
                    "Patient not found."
            );

            return;
        }

        String firstName =
                readText("New First Name: ");

        String lastName =
                readText("New Last Name: ");

        int age =
                readInt("New Age: ");

        String gender =
                readText("New Gender: ");

        String condition =
                readText("New Medical Condition: ");

        if (hospital.updatePatient(
                id,
                firstName,
                lastName,
                age,
                gender,
                condition)) {

            System.out.println(
                    "Patient updated successfully."
            );

        } else {

            System.out.println(
                    "Update failed."
            );
        }
    }

    private static void deletePatient() {

        String id =
                readText("Enter Patient ID: ");

        if (hospital.deletePatient(id)) {

            System.out.println(
                    "Patient deleted successfully."
            );

        } else {

            System.out.println(
                    "Patient not found."
            );
        }
    }

    private static void allocateBed() {

        String patientId =
                readText("Inpatient ID: ");

        Patient patient =
                hospital.searchPatient(patientId);

        if (!(patient instanceof Inpatient)) {

            System.out.println(
                    "Only inpatients can receive a bed."
            );

            return;
        }

        String bedNumber =
                readText("Bed Number (B01-B20): ");

        if (hospital.allocateBed(
                patientId,
                bedNumber)) {

            System.out.println(
                    "Bed allocated successfully."
            );

        } else {

            System.out.println(
                    "Bed allocation failed."
            );
        }
    }

    private static void releaseBed() {

        String bedNumber =
                readText("Bed Number: ");

        if (hospital.releaseBed(bedNumber)) {

            System.out.println(
                    "Bed released successfully."
            );

        } else {

            System.out.println(
                    "Bed could not be released."
            );
        }
    }

    private static void sortPatients() {

        System.out.println();
        System.out.println("1. Sort by Surname");
        System.out.println("2. Sort by Patient ID");

        int choice =
                readInt("Enter choice: ");

        List<Patient> sorted;

        if (choice == 1) {

            sorted =
                    hospital.sortBySurname();

        } else if (choice == 2) {

            sorted =
                    hospital.sortById();

        } else {

            System.out.println(
                    "Invalid choice."
            );

            return;
        }

        System.out.println();

        for (Patient patient : sorted) {
            System.out.println(patient);
        }
    }

    private static String readText(String message) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }

    private static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}
            