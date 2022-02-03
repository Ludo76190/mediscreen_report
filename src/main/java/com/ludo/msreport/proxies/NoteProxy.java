package com.ludo.msreport.proxies;

import com.ludo.msreport.Model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msnote", url = "${ms_note.url}:9092")
public interface NoteProxy {

    @GetMapping("/note/patient/{patientId}")
    List<Note> getNotesForPatientId(@PathVariable int patientId);


}
