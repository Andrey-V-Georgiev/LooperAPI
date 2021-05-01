package com.looper_api.web.rest_controllers;


import com.google.gson.Gson;
import com.looper_api.constants.ExceptionsMessages;
import com.looper_api.constants.GlobalConstants;
import com.looper_api.models.binding.CsvInputDTO;
import com.looper_api.services.CsvArrayListService;
import com.looper_api.services.PalindromeLinkedListService;
import com.looper_api.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/data-structures")
public class DataStructuresController {

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CsvArrayListService csvArrayListService;
    private final PalindromeLinkedListService palindromeLinkedListService;

    @Autowired
    public DataStructuresController(
            Gson gson,
            ValidationUtil validationUtil,
            CsvArrayListService csvArrayListService,
            PalindromeLinkedListService palindromeLinkedListService) {

        this.gson = gson;
        this.validationUtil = validationUtil;
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

        CsvInputDTO csvInputDTO;
        try {
            /* Convert json to DTO */
            csvInputDTO = this.gson.fromJson(inputJson, CsvInputDTO.class);
        } catch (Exception e) {
            /* If user gives wrong type of input array */
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ExceptionsMessages.CSV_INPUT_INVALID_ARRAY_ELEMENTS_TYPE);
        }
        /* Validate is follow the validation requirements */
        if (this.validationUtil.isValid(csvInputDTO)) {
            /* Convert input Json to CSV output */
            String csvJSON = this.csvArrayListService.convertToCSV(csvInputDTO);
            return ResponseEntity.ok(csvJSON);
        } else {
            /* In case of validation violations, response with violations messages */
            String violationsString = this.validationUtil.violationsString(csvInputDTO);
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

        System.out.println();
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
