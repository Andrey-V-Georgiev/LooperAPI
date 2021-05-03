package com.looper_api.loopers.impl;

import com.looper_api.loopers.LooperRunnable;
import com.looper_api.services.LoggerService;

import java.util.concurrent.atomic.AtomicBoolean;

public class LooperRunnableImpl implements LooperRunnable {

    private final LoggerService loggerService;
    private final Long loopsCount;
    private final Integer sleepMilliseconds;
    private final String text;

    private AtomicBoolean running = new AtomicBoolean(true);

    public LooperRunnableImpl(LoggerService loggerService, Long loopsCount, Integer sleepMilliseconds, String text) {
        this.loggerService = loggerService;
        this.loopsCount = loopsCount;
        this.sleepMilliseconds = sleepMilliseconds;
        this.text = text;
    }

    public Long getLoopsCount() {
        return loopsCount;
    }

    public Integer getSleepMilliseconds() {
        return sleepMilliseconds;
    }

    public String getText() {
        return text;
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();

        for (int iteration = 0; iteration < this.loopsCount && running.get(); iteration++) {

            /* Log iteration details to file system DB file and print to console */
            this.loggerService.logLooperIterationDetails(currentThread, this.text, iteration);

            /* Provide iteration functionality by sleeping intervals on the thread */
            try {
                Thread.sleep(this.sleepMilliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /* Log exit details and print to console*/
        this.loggerService.logLooperExitDetails(currentThread);
    }

    public void shutdown() {
        running.set(false);
    }
}
