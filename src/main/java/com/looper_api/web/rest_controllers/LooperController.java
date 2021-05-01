package com.looper_api.web.rest_controllers;


import com.google.gson.Gson;
import com.looper_api.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/looper")
public class LooperController {

    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public LooperController(Gson gson, ValidationUtil validationUtil) {
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @GetMapping("/all-loops-statuses")
    public ResponseEntity<Object> findAllLoopsStatuses(
            @PathVariable("agreement-folder-name") String agreementFolderName) throws IOException {

        return null;
    }

    @PostMapping("/write-to-file")
    public ResponseEntity<Object> writeToFile(
            @PathVariable("agreement-folder-name") String agreementFolderName) throws IOException {

        return null;
    }

    @DeleteMapping("/kill-writing-loop")
    public ResponseEntity<Object> killWritingLoop(
            @PathVariable("agreement-folder-name") String agreementFolderName) throws IOException {

        return null;
    }

    @DeleteMapping("/kill-all-loops")
    public ResponseEntity<Object> killAllLoops(
            @PathVariable("agreement-folder-name") String agreementFolderName) throws IOException {

        return null;
    }

}
