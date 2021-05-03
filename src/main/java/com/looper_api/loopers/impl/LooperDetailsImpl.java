package com.looper_api.loopers.impl;

import com.looper_api.loopers.LooperDetails;
import com.looper_api.loopers.LooperRunnable;

public class LooperDetailsImpl implements LooperDetails {

    private LooperRunnable runnable;
    private Thread thread;

    public LooperDetailsImpl(LooperRunnable runnable, Thread thread) {
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
