package com.ludo.msreport.Controller;

import com.ludo.msreport.Model.Note;
import com.ludo.msreport.Model.Patient;
import com.ludo.msreport.Service.ReportService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    ReportService reportService;

    @GetMapping("/")
    public String index() {
        return "Greeting to report Microservice";
    }

    @ApiOperation(value = "get patient")
    @GetMapping("/patient/{patientId}")
    public Patient GetPatient(@PathVariable int patientId) {
        LOGGER.info("GET /patient/{patientID} for patientId = " + patientId );
        return reportService.getPatient(patientId);
    }

    @ApiOperation(value = "get patient notes")
    @GetMapping("/note/patient/{patientId}")
    public List<Note> GetNotes(@PathVariable int patientId) {
        LOGGER.info("GET /note/patient/{patientID} for patientId = " + patientId );
        return reportService.getNotes(patientId);
    }

    @ApiOperation(value = "get patient report")
    @GetMapping("/report/patient/{patientId}")
    public String getReportForPatientId(@PathVariable int patientId){
        LOGGER.info("GET /report/patient/{patientID} for patientId = " + patientId );
        return reportService.getReportForPatientId(patientId);
    }

}
