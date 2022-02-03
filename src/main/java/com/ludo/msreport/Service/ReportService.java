package com.ludo.msreport.Service;

import com.ludo.msreport.Model.Note;
import com.ludo.msreport.Model.Patient;

import java.util.List;

public interface ReportService {

    Patient getPatient(int patientId);

    List<Note> getNotes(int patientId);

    String getReportForPatientId(int patientId);
}
