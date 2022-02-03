package com.ludo.msreport.service;

import com.ludo.msreport.Model.Note;
import com.ludo.msreport.Model.Patient;
import com.ludo.msreport.Service.ReportServiceImpl;
import com.ludo.msreport.proxies.NoteProxy;
import com.ludo.msreport.proxies.PatientProxy;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ReportServiceTest {
    @Mock
    PatientProxy patientProxy;

    @Mock
    NoteProxy noteProxy;

    @InjectMocks
    ReportServiceImpl reportService;

    private Patient patient;

    private Note note;

    @BeforeEach
    public void setUpPerTest(){
        patient = new Patient();
        patient.setId(1);
        patient.setFirstName("test");
        patient.setLastName("test");
        patient.setBirthdate(LocalDate.of(2000, 01, 01));
        patient.setSex("M");
        patient.setAddress("address");
        patient.setPhone("phone");

        note = new Note();
        note.setId("noteId");
        note.setPatientId(1);
        note.setNote("note content");

    }

    @Test
    public void getPatientTest(){
        Mockito.when(patientProxy.getPatient(1)).thenReturn(patient);

        Assertions.assertTrue(reportService.getPatient(1).getLastName().equalsIgnoreCase(patient.getLastName()));
    }

    @Test
    public void getPatientKOTest(){
        Mockito.when(patientProxy.getPatient(1)).thenReturn(null);

        Assertions.assertNull(reportService.getPatient(1));
    }

    @Test
    public void getNoteListForPatientTest(){
        Mockito.when(noteProxy.getNotesForPatientId(1)).thenReturn(Collections.singletonList(note));

        List<Note> result = reportService.getNotes(1);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(note.getId(), result.get(0).getId());
    }

    @Test
    public void RiskNoneTest() {
        Mockito.when(patientProxy.getPatient(1)).thenReturn(patient);
        Mockito.when(noteProxy.getNotesForPatientId(1)).thenReturn(Collections.singletonList(note));

        Assertions.assertEquals(reportService.getReportForPatientId(1), "none");

    }

    @Test
    public void RiskEarlyOnsetMoreThirtyYearsTest() {
        note.setNote("Hémoglobine A1C Microalbumine Taille poids fumeur Anormal Cholestérol Vertige");
        patient.setBirthdate(LocalDate.of(1972, 01, 01));

        Mockito.when(patientProxy.getPatient(1)).thenReturn(patient);
        Mockito.when(noteProxy.getNotesForPatientId(1)).thenReturn(Collections.singletonList(note));

        Assertions.assertEquals(reportService.getReportForPatientId(1), "Early onset");

    }

    @Test
    public void RiskEarlyOnsetLessThirtyYearsFemaleTest() {
        note.setNote("Hémoglobine A1C Microalbumine Taille poids fumeur Anormal Cholestérol");
        patient.setBirthdate(LocalDate.of(2010, 01, 01));
        patient.setSex("F");

        Mockito.when(patientProxy.getPatient(1)).thenReturn(patient);
        Mockito.when(noteProxy.getNotesForPatientId(1)).thenReturn(Collections.singletonList(note));

        Assertions.assertEquals(reportService.getReportForPatientId(1), "Early onset");

    }

    @Test
    public void RiskEarlyOnsetLessThirtyYearsMaleTest() {
        note.setNote("Hémoglobine A1C Microalbumine Taille poids fumeur");
        patient.setBirthdate(LocalDate.of(2010, 01, 01));
        patient.setSex("M");

        Mockito.when(patientProxy.getPatient(1)).thenReturn(patient);
        Mockito.when(noteProxy.getNotesForPatientId(1)).thenReturn(Collections.singletonList(note));

        Assertions.assertEquals(reportService.getReportForPatientId(1), "Early onset");

    }

    @Test
    public void RiskDangerMoreThirtyYearsSixTriggersTest() {
        note.setNote("Hémoglobine A1C Microalbumine Taille poids fumeur Anormal");
        patient.setBirthdate(LocalDate.of(1972, 01, 01));

        Mockito.when(patientProxy.getPatient(1)).thenReturn(patient);
        Mockito.when(noteProxy.getNotesForPatientId(1)).thenReturn(Collections.singletonList(note));

        Assertions.assertEquals(reportService.getReportForPatientId(1), "Danger");

    }

    @Test
    public void RiskDangerLessThirtyYearsFemaleFourTriggersTest() {
        note.setNote("Hémoglobine A1C Microalbumine Taille poids");
        patient.setBirthdate(LocalDate.of(2000, 01, 01));
        patient.setSex("F");

        Mockito.when(patientProxy.getPatient(1)).thenReturn(patient);
        Mockito.when(noteProxy.getNotesForPatientId(1)).thenReturn(Collections.singletonList(note));

        Assertions.assertEquals(reportService.getReportForPatientId(1), "Danger");

    }

    @Test
    public void RiskDangerLessThirtyYearsMaleThreeTriggersTest() {
        note.setNote("Hémoglobine A1C Microalbumine Taille");
        patient.setBirthdate(LocalDate.of(2000, 01, 01));
        patient.setSex("M");

        Mockito.when(patientProxy.getPatient(1)).thenReturn(patient);
        Mockito.when(noteProxy.getNotesForPatientId(1)).thenReturn(Collections.singletonList(note));

        Assertions.assertEquals(reportService.getReportForPatientId(1), "Danger");

    }

    @Test
    public void RiskBorderlineMoreThirtyYearsTwoTriggersTest() {
        note.setNote("Hémoglobine A1C Microalbumine");
        patient.setBirthdate(LocalDate.of(1972, 01, 01));

        Mockito.when(patientProxy.getPatient(1)).thenReturn(patient);
        Mockito.when(noteProxy.getNotesForPatientId(1)).thenReturn(Collections.singletonList(note));

        Assertions.assertEquals(reportService.getReportForPatientId(1), "Borderline");

    }

    @Test
    public void RiskNotDefinedTest() {
        note.setNote("Hémoglobine A1C taille");
        patient.setBirthdate(LocalDate.of(2000, 01, 01));
        patient.setSex("F");

        Mockito.when(patientProxy.getPatient(1)).thenReturn(patient);
        Mockito.when(noteProxy.getNotesForPatientId(1)).thenReturn(Collections.singletonList(note));

        Assertions.assertEquals(reportService.getReportForPatientId(1), "not defined");

    }


}
