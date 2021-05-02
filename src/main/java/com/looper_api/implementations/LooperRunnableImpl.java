package com.looper_api.implementations;

import java.util.concurrent.atomic.AtomicBoolean;

public class LooperRunnableImpl implements LooperRunnable {

    private Long loopsCount;
    private Integer sleepMilliseconds;
    private String text;

    private AtomicBoolean running = new AtomicBoolean(true);

    public LooperRunnableImpl(Long loopsCount, Integer sleepMilliseconds, String text) {
        this.loopsCount = loopsCount;
        this.sleepMilliseconds = sleepMilliseconds;
        this.text = text;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.loopsCount && running.get(); i++) {

            Thread currentThread = Thread.currentThread();
            System.out.printf("Id: %d |Name: %s | State: %s | Iteration: %d | Message: %s_%d\n",
                    currentThread.getId(),
                    currentThread.getName(),
                    currentThread.getState(),
                    i,
                    this.text, i);
            try {
                Thread.sleep(this.sleepMilliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        running.set(false);
    }
}
