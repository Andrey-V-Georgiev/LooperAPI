package com.looper_api.models.binding.loopers;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LooperImportDTO {

    @Expose
    private Long loopsCount;
    @Expose
    private Integer sleepMilliseconds;
    @Expose
    private String text;

    public LooperImportDTO() {
    }

    public LooperImportDTO(Long loopsCount, Integer sleepMilliseconds, String text) {
        this.loopsCount = loopsCount;
        this.sleepMilliseconds = sleepMilliseconds;
        this.text = text;
    }

    @NotNull(message = "Loops count field is required")
    @Min(value = 1, message = "Loops count must contains at least one iteration")
    public Long getLoopsCount() {
        return loopsCount;
    }

    public void setLoopsCount(Long loopsCount) {
        this.loopsCount = loopsCount;
    }

    @NotNull(message = "Sleep milliseconds field is required")
    @Min(value = 100, message = "Timeout between loops iterations must be at least 100 milliseconds")
    public Integer getSleepMilliseconds() {
        return sleepMilliseconds;
    }

    public void setSleepMilliseconds(Integer sleepMilliseconds) {
        this.sleepMilliseconds = sleepMilliseconds;
    }

    @NotEmpty(message = "Text field is required")
    @Size(min = 1, message = "Import text must be at least one character long")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
