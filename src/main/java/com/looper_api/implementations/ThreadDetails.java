package com.looper_api.implementations;

public class ThreadDetails {

    private LooperRunnable runnable;
    private Thread thread;

    public ThreadDetails(LooperRunnable runnable, Thread thread) {
        this.runnable = runnable;
        this.thread = thread;
    }

    public LooperRunnable getRunnable() {
        return runnable;
    }

    public void setRunnable(LooperRunnable runnable) {
        this.runnable = runnable;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
