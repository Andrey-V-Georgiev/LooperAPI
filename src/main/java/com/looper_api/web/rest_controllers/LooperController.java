package com.looper_api.web.rest_controllers;

import com.google.gson.Gson;
import com.looper_api.constants.ExceptionsMessages;
import com.looper_api.models.binding.LooperInputDTO;
import com.looper_api.services.ThreadService;
import com.looper_api.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/looper")
public class LooperController {

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ThreadService threadService;

    @Autowired
    public LooperController(Gson gson, ValidationUtil validationUtil, ThreadService threadService) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.threadService = threadService;
    }

    @GetMapping("/all-threads-details")
    public ResponseEntity<Object> findAllThreadsDetails() throws IOException {

        String allThreadsDetails = this.threadService.findAllThreadsDetails();
        return ResponseEntity.ok(allThreadsDetails);
    }

    @PostMapping("/write-to-file")
    public ResponseEntity<Object> writeToFile(
            @RequestBody String inputJson) throws IOException, InterruptedException {

        //TODO first validate the DTO
        LooperInputDTO looperInputDTO = this.gson.fromJson(inputJson, LooperInputDTO.class);
        long looperId = this.threadService.startNewLooper(looperInputDTO);
        return ResponseEntity.ok("Thread Id: " + looperId);
    }

    @DeleteMapping("/kill-thread/{thread-id}")
    public ResponseEntity<Object> killThread(
            @PathVariable("thread-id") Long threadId) throws InterruptedException {

        try {
            String killedThreadDetails = this.threadService.killThread(threadId);
            return ResponseEntity.ok(killedThreadDetails);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(String.format(ExceptionsMessages.THREAD_WITH_ID_NOT_FOUND, threadId));
        }
    }

    @DeleteMapping("/kill-all-threads")
    public ResponseEntity<Object> killAllThreads() throws IOException {

        try {
            String killedAllThreadsDetails = this.threadService.killAllThreads();
            return ResponseEntity.ok(killedAllThreadsDetails);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ExceptionsMessages.KILL_ALL_THREADS_INTERRUPTED_EXCEPTION);
        }
    }

}
