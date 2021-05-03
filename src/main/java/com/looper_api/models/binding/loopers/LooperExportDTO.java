package com.looper_api.models.binding.loopers;

import com.google.gson.annotations.Expose;

public class LooperExportDTO {

    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private String state;
    @Expose
    private Long loopsCount;
    @Expose
    private Integer sleepMilliseconds;
    @Expose
    private String text;

    public LooperExportDTO() {
    }

    public LooperExportDTO(Long id, String name, String state, Long loopsCount, Integer sleepMilliseconds, String text) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.loopsCount = loopsCount;
        this.sleepMilliseconds = sleepMilliseconds;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
