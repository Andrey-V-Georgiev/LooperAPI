package com.looper_api.services;

import com.looper_api.models.binding.LooperInputDTO;

public interface ThreadService {

    long startNewLooper(LooperInputDTO looperInputDTO) throws InterruptedException;

    String findAllThreadsDetails();

    String killThread(Long threadId) throws InterruptedException;

    String killAllThreads() throws InterruptedException;

}
