package com.looper_api.models.binding;

import com.google.gson.annotations.Expose;

public class LooperInputDTO {

    @Expose
    private Long loopsCount;
    @Expose
    private Integer sleepMilliseconds;
    @Expose
    private String text;

    public LooperInputDTO() {
    }

    public LooperInputDTO(Long loopsCount, Integer sleepMilliseconds, String text) {
        this.loopsCount = loopsCount;
        this.sleepMilliseconds = sleepMilliseconds;
        this.text = text;
    }

    public Long getLoopsCount() {
        return loopsCount;
    }

    public void setLoopsCount(Long loopsCount) {
        this.loopsCount = loopsCount;
    }

    public Integer getSleepMilliseconds() {
        return sleepMilliseconds;
    }

    public void setSleepMilliseconds(Integer sleepMilliseconds) {
        this.sleepMilliseconds = sleepMilliseconds;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
