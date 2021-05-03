package com.looper_api.services.impl;

import com.google.gson.Gson;
import com.looper_api.data_structures.LooperArrayList;
import com.looper_api.data_structures.LooperLinkedList;
import com.looper_api.data_structures.impl.LooperArrayListImpl;
import com.looper_api.data_structures.impl.LooperDoublyLinkedListImpl;
import com.looper_api.models.binding.data_structures.PalindromeLongestExportDTO;
import com.looper_api.services.PalindromeLinkedListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PalindromeLinkedListServiceImpl implements PalindromeLinkedListService {

    private final Gson gson;

    @Autowired
    public PalindromeLinkedListServiceImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Boolean checkIsPalindrome(String palindrome) {

        /* Initialization new doubly linked list */
        LooperLinkedList looperLinkedList = new LooperDoublyLinkedListImpl(palindrome);

        /* Check does the palindrome string is valid palindrome */
        return looperLinkedList.isPalindrome();
    }

    @Override
    public String findLongestPalindrome(String palindromeString) {

        /* Initialization new doubly linked list */
        LooperArrayList<Character> looperLinkedList = new LooperArrayListImpl<>(palindromeString);

        /* Export longest palindrome as JSON */
        String longestPalindrome = looperLinkedList.findLongestPalindrome();
        PalindromeLongestExportDTO palindromeExportDTO = new PalindromeLongestExportDTO(longestPalindrome);
        return this.gson.toJson(palindromeExportDTO);
    }
}
