package com.looper_api.web.rest_controllers;


import com.google.gson.Gson;
import com.looper_api.constants.ExceptionsMessages;
import com.looper_api.constants.GlobalConstants;
import com.looper_api.models.binding.data_structures.CsvImportDTO;
import com.looper_api.services.CsvArrayListService;
import com.looper_api.services.PalindromeLinkedListService;
import com.looper_api.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/data-structures")
public class DataStructuresController {

    private final Gson gson;
    private final ValidationService validationService;
    private final CsvArrayListService csvArrayListService;
    private final PalindromeLinkedListService palindromeLinkedListService;

    @Autowired
    public DataStructuresController(
            Gson gson,
            ValidationService validationService,
            CsvArrayListService csvArrayListService,
            PalindromeLinkedListService palindromeLinkedListService) {

        this.gson = gson;
        this.validationService = validationService;
        this.csvArrayListService = csvArrayListService;
        this.palindromeLinkedListService = palindromeLinkedListService;
    }

    /**
     * Expect JSON object in the request body, with field: "numbers"
     * "numbers" must be array of integers
     * Return given integers from the list as comma separated values(CSV)
     *
     * @param inputJson example: "numbers": [1,2,3,4,5,6]
     **/
    @PutMapping("/csv-string")
    public ResponseEntity<Object> convertToCSV(
            @RequestBody String inputJson) throws IOException {

        CsvImportDTO csvImportDTO;
        try {
            /* Convert json to DTO */
            csvImportDTO = this.gson.fromJson(inputJson, CsvImportDTO.class);
        } catch (Exception e) {
            /* If user gives wrong type of input array */
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ExceptionsMessages.CSV_INPUT_INVALID_ARRAY_ELEMENTS_TYPE);
        }
        /* Validate input is following the validation requirements */
        if (this.validationService.isValid(csvImportDTO)) {
            /* Convert input Json to CSV output */
            String csvJSON = this.csvArrayListService.convertToCSV(csvImportDTO);
            return ResponseEntity.ok(csvJSON);
        } else {
            /* In case of validation violations, response with violations messages */
            String violationsString = this.validationService.violationsString(csvImportDTO);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(violationsString);
        }
    }

    /**
     * Expect query param: "palindrome"
     * Return boolean depends on check if input string is valid palindrome
     *
     * @param palindrome query example: ?palindrome=ABCDFDCBA
     **/
    @GetMapping("/palindrome-check")
    public ResponseEntity<Object> checkIsPalindrome(
            @RequestParam("palindrome") String palindrome) throws IOException {

        if (palindrome.length() < 1) {
            /* If user gives empty palindrome as query param */
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ExceptionsMessages.PALINDROME_STRING_CANNOT_BE_EMPTY);
        }
        /* Check does the string is valid palindrome */
        Boolean isPalindrome = this.palindromeLinkedListService.checkIsPalindrome(palindrome);
        if (isPalindrome) {
            /* In case when the string is valid palindrome */
            return ResponseEntity.ok(GlobalConstants.PALINDROME_IS_VALID);
        } else {
            /* In case when the string is invalid as palindrome */
            return ResponseEntity.ok(GlobalConstants.PALINDROME_IS_NOT_VALID);
        }
    }

    /**
     * Expect query param: "palindrome-string"
     * Return the longest palindrome contained in the input string
     *
     * @param palindromeString query example: ?palindrome-string=ZABCBAVUIG
     **/
    @GetMapping("/longest-palindrome")
    public ResponseEntity<Object> findLongestPalindrome(
            @RequestParam("palindrome-string") String palindromeString) throws IOException {

        if (palindromeString.length() < 1) {
            /* If user gives empty palindrome string as query param */
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ExceptionsMessages.PALINDROME_STRING_CANNOT_BE_EMPTY);
        }
        /* Find the longest palindrome in the string, if some */
        String longestPalindrome = this.palindromeLinkedListService.findLongestPalindrome(palindromeString);
        return ResponseEntity.ok(longestPalindrome);
    }
}
