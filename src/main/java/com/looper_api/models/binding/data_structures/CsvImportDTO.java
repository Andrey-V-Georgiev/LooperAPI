package com.looper_api.models.binding.data_structures;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CsvImportDTO {

    @Expose
    private Integer[] numbers;

    public CsvImportDTO() {
    }

    public CsvImportDTO(Integer[] numbers) {
        this.numbers = numbers;
    }

    @NotNull(message = "Field 'numbers' in input Json is required")
    @Size(min = 1, message = "CSV integer array must have at least 1 element")
    public Integer[] getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer[] numbers) {
        this.numbers = numbers;
    }
}
