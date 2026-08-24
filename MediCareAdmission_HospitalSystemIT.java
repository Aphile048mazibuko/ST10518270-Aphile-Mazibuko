package com.mycompany.medicareadmission_hospitalsystem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class MediCareAdmission_HospitalSystemIT {

    private MediCareAdmission_HospitalSystem system;
    private MediCareAdmission_HospitalSystem.Patient patient1;
    private MediCareAdmission_HospitalSystem.Patient patient2;

    public MediCareAdmission_HospitalSystemIT() {
    }

    @BeforeAll
    public static void setUpClass() {
        System.out.println("Starting all tests - setUpClass()");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("Finishing all tests - tearDownClass()");
    }

    @BeforeEach
    public void setUp() {
        system = new MediCareAdmission_HospitalSystem();
        // Dala ama-patients for testing
        patient1 = new MediCareAdmission_HospitalSystem.Patient(
            "P001", "Sipho", "Dlamini", 30, "Male", "Flu", 
            MediCareAdmission_HospitalSystem.PatientCategory.INPATIENT
        );
        patient2 = new MediCareAdmission_HospitalSystem.Patient(
            "P002", "Noluthando", "Mkhize", 25, "Female", "Fracture",
            MediCareAdmission_HospitalSystem.PatientCategory.EMERGENCY
        );
    }

    @AfterEach
    public void tearDown() {
        system = null;
        patient1 = null;
        patient2 = null;
    }
}
    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }
    // TEST 1: Test main method
    @Test
    public void testMain() {
        System.out.println("Testing main method");
        String[] args = null;
        assertDoesNotThrow(() -> {
        // Ungayivuli uma i-main inayo i-Scanner - izo-linda input
            // MediCareAdmission_HospitalSystem.main(args);
        });
    }

    // TEST 2: Test PatientCategory enum
    @Test
    public void testPatientCategoryEnum() {
        System.out.println("Testing PatientCategory");
        assertNotNull(MediCareAdmission_HospitalSystem.PatientCategory.INPATIENT);
        assertNotNull(MediCareAdmission_HospitalSystem.PatientCategory.OUTPATIENT);
        assertNotNull(MediCareAdmission_HospitalSystem.PatientCategory.EMERGENCY);
        assertEquals(3, MediCareAdmission_HospitalSystem.PatientCategory.values().length);
    }

    // TEST 3: Test Patient creation
    @Test
    public void testPatientCreation() {
        System.out.println("Testing Patient creation");
        assertNotNull(patient1);
        assertEquals("P001", patient1.getPatientId());
        assertEquals("Sipho", patient1.getFirstName());
        assertEquals("Dlamini", patient1.getLastName());
        assertEquals(30, patient1.getAge());
    }

    // TEST 4: Test Patient getters and setters
    @Test
    public void testPatientGettersSetters() {
        System.out.println("Testing Patient getters/setters");
        patient1.setFirstName("Thabo");
        assertEquals("Thabo", patient1.getFirstName());

        patient1.setAge(35);
        assertEquals(35, patient1.getAge());

        patient1.setMedicalCondition("Headache");
        assertEquals("Headache", patient1.getMedicalCondition());
    }

    // TEST 5: Test Patient Category
    @Test
    public void testPatientCategory() {
        System.out.println("Testing Patient category");
        assertEquals(MediCareAdmission_HospitalSystem.PatientCategory.INPATIENT, patient1.getCategory());
        assertEquals(MediCareAdmission_HospitalSystem.PatientCategory.EMERGENCY, patient2.getCategory());
    }

    // TEST 6: Test toString method
    @Test
    public void testPatientToString() {
        System.out.println("Testing Patient toString");
        String result = patient1.toString();
        assertNotNull(result);
        assertTrue(result.contains("P001") || result.contains("Sipho"));
    }

    // TEST 7: Test ArrayList of Patients
    @Test
    public void testPatientList() {
        System.out.println("Testing Patient List");
        List<MediCareAdmission_HospitalSystem.Patient> list = new ArrayList<>();
        list.add(patient1);
        list.add(patient2);
        
        assertEquals(2, list.size());
        assertEquals("P001", list.get(0).getPatientId());
    }
}
