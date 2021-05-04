package com.looper_api.web.rest_controllers;

import com.google.gson.Gson;
import com.looper_api.constants.ExceptionsMessages;
import com.looper_api.data_structures.LooperArrayList;
import com.looper_api.models.binding.loopers.LooperExportDTO;
import com.looper_api.models.binding.loopers.LooperImportDTO;
import com.looper_api.services.LoggerService;
import com.looper_api.services.LooperService;
import com.looper_api.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/looper")
public class LooperController {

    private final Gson gson;
    private final ValidationService validationService;
    private final LooperService looperService;
    private final LoggerService loggerService;

    @Autowired
    public LooperController(
            Gson gson,
            ValidationService validationService,
            LooperService looperService,
            LoggerService loggerService) {

        this.gson = gson;
        this.validationService = validationService;
        this.looperService = looperService;
        this.loggerService = loggerService;
    }

    /**
     * Return JSON object with the details of all the loopers
     **/
    @GetMapping("/all-loopers-details")
    public ResponseEntity<Object> findAllLoopersDetails() {

        /* Prepare all loopers details as JSON */
        LooperArrayList<LooperExportDTO> allLoopersDetails = this.looperService.findAllLoopersDetails();

        /* Resize tight the list to avoid null values in JSON representation */
        allLoopersDetails.resizeTight();
        String allLoopersDetailsJSON = this.gson.toJson(allLoopersDetails);

        return ResponseEntity.ok(allLoopersDetailsJSON);
    }

    /**
     * Return the details of all logs as String representation
     **/
    @GetMapping("/all-logs-details")
    public ResponseEntity<Object> findAllLogsDetails() {

        try {
            /* Prepare all logs details and return them as string representation */
            String allLogsDetails = this.loggerService.findAllLogsDetails();
            return ResponseEntity.ok(allLogsDetails);

        } catch (Exception e) {
            /* If exception appeared send user-friendly message */
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ExceptionsMessages.FIND_ALL_LOGS_INTERNAL_EXCEPTION);
        }
    }

    /**
     * Expect JSON object in the request body
     * Start new thread for looper and log his iteration details to the log file
     * Return the details of the started looper in JSON format
     *
     * @param inputJson example:
     *                  {
     *                  "loopsCount": 5, //Long
     *                  "sleepMilliseconds": 1000, //Long
     *                  "text": "Some text" //String
     *                  }
     **/
    @PostMapping("/start-new-looper")
    public ResponseEntity<Object> startNewLooper(
            @RequestBody String inputJson) throws InterruptedException {

        LooperImportDTO looperImportDTO;
        try {
            /* Convert JSON to DTO */
            looperImportDTO = this.gson.fromJson(inputJson, LooperImportDTO.class);
        } catch (Exception e) {
            /* If user gives invalid input */
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ExceptionsMessages.LOOPER_STARTING_INVALID_INPUT_SCHEMA);
        }
        /* Validate input is following the validation requirements */
        if (this.validationService.isValid(looperImportDTO)) {
            LooperExportDTO looperExportDTO = this.looperService.startNewLooper(looperImportDTO);
            String looperExportDtoJSON = this.gson.toJson(looperExportDTO);

            return ResponseEntity.ok(looperExportDtoJSON);
        } else {
            /* In case of validation violations, response with violations messages */
            String violationsString = this.validationService.violationsString(looperImportDTO);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(violationsString);
        }
    }

    /**
     * Expect thread Id (long or integer) as path variable
     * Kill looper by his Id
     * Return the details of the killed looper in JSON format
     *
     * @param threadIdStr example: 51
     **/
    @DeleteMapping("/kill-looper/{thread-id}")
    public ResponseEntity<Object> killThread(
            @PathVariable("thread-id") String threadIdStr) {

        /* Validate thread id is of type long */
        Long threadId;
        try {
            threadId = Long.parseLong(threadIdStr);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(String.format(ExceptionsMessages.LOOPER_KILL_THREAD_INVALID_INPUT_TYPE));
        }
        try {
            /* Kill looper by his thread Id */
            LooperExportDTO killedLooperDetails = this.looperService.killLooper(threadId);

            /* If thread is already terminated */
            if (killedLooperDetails == null) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(String.format(ExceptionsMessages.THREAD_IS_ALREADY_TERMINATED, threadId));
            }

            /* If successfully terminate all threads, return the details as JSON */
            String killedLooperDetailsJSON = this.gson.toJson(killedLooperDetails);
            return ResponseEntity.ok(killedLooperDetailsJSON);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(String.format(ExceptionsMessages.THREAD_WITH_ID_NOT_FOUND, threadId));
        }
    }

    /**
     * Terminate all threads and return the details of all killed loopers as JSON
     **/
    @DeleteMapping("/kill-all-loopers")
    public ResponseEntity<Object> killAllLoopers() {

        try {
            /* Kill all loopers and return a feedback with the details */
            LooperArrayList<LooperExportDTO> killedLoopersDetails = this.looperService.killAllLoopers();

            /* Resize tight the list to avoid null values in JSON representation */
            killedLoopersDetails.resizeTight();
            String killedLoopersDetailsJSON = this.gson.toJson(killedLoopersDetails);

            return ResponseEntity.ok(killedLoopersDetailsJSON);

        } catch (Exception e) {
            /* If exception appeared send user-friendly message */
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ExceptionsMessages.KILL_ALL_LOOPERS_INTERNAL_EXCEPTION);
        }
    }
}
