package com.looper_api.models.binding.data_structures;

import com.google.gson.annotations.Expose;

public class PalindromeLongestExportDTO {

    @Expose
    private String longestPalindrome;

    public PalindromeLongestExportDTO() {
    }

    public PalindromeLongestExportDTO(String longestPalindrome) {
        this.longestPalindrome = longestPalindrome;
    }

    public String getLongestPalindrome() {
        return longestPalindrome;
    }

    public void setLongestPalindrome(String longestPalindrome) {
        this.longestPalindrome = longestPalindrome;
    }
}
