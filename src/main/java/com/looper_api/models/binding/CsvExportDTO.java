package com.looper_api.models.binding;

import com.google.gson.annotations.Expose;

public class CsvExportDTO {

    @Expose
    private String csvString;

    public CsvExportDTO() {
    }

    public CsvExportDTO(String csvString) {
        this.csvString = csvString;
    }

    public String getCsvString() {
        return csvString;
    }

    public void setCsvString(String csvString) {
        this.csvString = csvString;
    }
}
