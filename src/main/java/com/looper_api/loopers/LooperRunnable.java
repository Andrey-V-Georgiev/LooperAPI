package com.looper_api.loopers;

public interface LooperRunnable extends Runnable {

    void shutdown();

    Long getLoopsCount();

    Integer getSleepMilliseconds();

    String getText();
}
