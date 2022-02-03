package com.ludo.msreport.Controller;

import com.ludo.msreport.Model.Note;
import com.ludo.msreport.Model.Patient;
import com.ludo.msreport.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/")
    public String index() {
        return "Greeting to report Microservice";
    }

    @GetMapping("/patient/{patientId}")
    public Patient GetPatient(@PathVariable int patientId) {
        return reportService.getPatient(patientId);
    }

    @GetMapping("/note/patient/{patientId}")
    public List<Note> GetNotes(@PathVariable int patientId) {
        return reportService.getNotes(patientId);
    }

    @GetMapping("/report/patient/{patientId}")
    public String getReportForPatientId(@PathVariable int patientId){
        return reportService.getReportForPatientId(patientId);
    }

}
