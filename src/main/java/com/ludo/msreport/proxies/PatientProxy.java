package com.ludo.msreport.proxies;

import com.ludo.msreport.Model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mspatient", url = "${ms_patient.url}:9091")
public interface PatientProxy {

    @GetMapping("/patient/{id}")
    Patient getPatient(@PathVariable int id);

}
