package com.looper_api.services.impl;

import com.looper_api.constants.GlobalConstants;
import com.looper_api.implementations.LooperRunnable;
import com.looper_api.implementations.ThreadDetails;
import com.looper_api.models.binding.LooperInputDTO;
import com.looper_api.implementations.LooperRunnableImpl;
import com.looper_api.services.FileService;
import com.looper_api.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ThreadServiceImpl implements ThreadService {

    private final Map<Long, ThreadDetails> threadsDetails;
    private final FileService fileService;

    @Autowired
    public ThreadServiceImpl(FileService fileService) {
        this.threadsDetails = new LinkedHashMap<>();
        this.fileService = fileService;
    }

    @PostConstruct
    private void createRootFolderOnDiskC() throws IOException {
        this.fileService.createDir(GlobalConstants.ROOT_FOLDER_PATH);
        String fullPath = String.format("%s\\%s", GlobalConstants.ROOT_FOLDER_PATH, GlobalConstants.LOOPER_API_DB_TXT);
        this.fileService.writeFile(GlobalConstants.BLANK, fullPath);
    }

    @Override
    public long startNewLooper(LooperInputDTO dto) throws InterruptedException {

        /* Obtain the details */
        Long loopsCount = dto.getLoopsCount();
        Integer sleepMilliseconds = dto.getSleepMilliseconds();
        String text = dto.getText();

        /* Create and save thread details */
        LooperRunnable runnable = new LooperRunnableImpl(loopsCount, sleepMilliseconds, text);
        Thread thread = new Thread(runnable);
        this.threadsDetails.put(thread.getId(), new ThreadDetails(runnable, thread));

        /* Start the thread */
        thread.start();

        return thread.getId();
    }

    @Override
    public String findAllThreadsDetails() {

        StringBuilder stringBuilder = new StringBuilder();
        for (ThreadDetails tmd : threadsDetails.values()) {
            stringBuilder.append(String.format(
                    "Id: %d |Name: %s | State: %s",
                    tmd.getThread().getId(),
                    tmd.getThread().getName(),
                    tmd.getThread().getState())).append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }

    @Override
    public String killThread(Long threadId) throws InterruptedException {

        ThreadDetails threadDetails = this.threadsDetails.get(threadId);
        LooperRunnable runnable = threadDetails.getRunnable();
        Thread thread = threadDetails.getThread();

        runnable.shutdown();
        thread.join();

        return String.format(
                "Killed Thread | Id: %d |Name: %s | State: %s", thread.getId(), thread.getName(), thread.getState());
    }

    @Override
    public String killAllThreads() throws InterruptedException {

        StringBuilder stringBuilder = new StringBuilder();

        for (Long threadId : threadsDetails.keySet()) {
            String killedThreadDetails = this.killThread(threadId);
            stringBuilder.append(killedThreadDetails).append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }
}
