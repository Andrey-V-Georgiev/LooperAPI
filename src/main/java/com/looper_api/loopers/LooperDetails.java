package com.looper_api.loopers;

public interface LooperDetails {

    LooperRunnable getRunnable();

    void setRunnable(LooperRunnable runnable);

    Thread getThread();

    void setThread(Thread thread);
}
