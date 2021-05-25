package com.looper_api.services.impl;

import com.looper_api.config.ConfigurationProperties;
import com.looper_api.constants.GlobalConstants;
import com.looper_api.data_structures.LooperArrayList;
import com.looper_api.data_structures.impl.LooperArrayListImpl;
import com.looper_api.loopers.LooperDetails;
import com.looper_api.loopers.LooperRunnable;
import com.looper_api.loopers.impl.LooperDetailsImpl;
import com.looper_api.models.binding.loopers.LooperExportDTO;
import com.looper_api.models.binding.loopers.LooperImportDTO;
import com.looper_api.loopers.impl.LooperRunnableImpl;
import com.looper_api.services.FileService;
import com.looper_api.services.LoggerService;
import com.looper_api.services.LooperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LooperServiceImpl implements LooperService {

    private final Map<Long, LooperDetails> loopersDetails;
    private final FileService fileService;
    private final ConfigurationProperties configurationProperties;
    private final LoggerService loggerService;

    @Autowired
    public LooperServiceImpl(
            FileService fileService,
            ConfigurationProperties configurationProperties,
            LoggerService loggerService) {

        this.configurationProperties = configurationProperties;
        this.loggerService = loggerService;
        this.loopersDetails = new LinkedHashMap<>();
        this.fileService = fileService;
    }

    @PostConstruct
    private void createRootFolderOnDiskC() throws IOException {

        /* Check does root folder exists and if not create it */
        this.fileService.createDirIfNotExists(configurationProperties.getDbRootFolderPath());

        /* Override the DB text file (empty the previous records) */
        this.fileService.overrideFile(GlobalConstants.BLANK, configurationProperties.getDbLogFullPath());
    }

    @Override
    public LooperArrayList<LooperExportDTO> findAllLoopersDetails() {

        LooperArrayList<LooperExportDTO> looperExportDtoList = new LooperArrayListImpl<>();

        /* Prepare all loopers details */
        for (LooperDetails ld : loopersDetails.values()) {

            /* Obtain current looper details */
            LooperRunnable ldRunnable = ld.getRunnable();
            Thread ldThread = ld.getThread();

            /* Set export DTO details */
            LooperExportDTO looperExportDTO = this.generateLooperExportDTO(ldRunnable, ldThread);

            /* Add current DTO to the export list */
            looperExportDtoList.add(looperExportDTO);
        }
        return looperExportDtoList;
    }

    @Override
    public synchronized LooperExportDTO startNewLooper(LooperImportDTO dto) {

        /* Obtain the details */
        Long loopsCount = dto.getLoopsCount();
        Integer sleepMilliseconds = dto.getSleepMilliseconds();
        String text = dto.getText();

        /* Create and save thread details */
        LooperRunnable runnable = new LooperRunnableImpl(this.loggerService, loopsCount, sleepMilliseconds, text);
        Thread thread = new Thread(runnable);
        this.loopersDetails.put(thread.getId(), new LooperDetailsImpl(runnable, thread));

        /* Start the thread */
        thread.start();

        return this.generateLooperExportDTO(runnable, thread);
    }

    @Override
    public LooperExportDTO killLooper(Long threadId) throws InterruptedException {

        /* Find the details in loopers details map */
        LooperDetails looperDetails = this.loopersDetails.get(threadId);
        LooperRunnable runnable = looperDetails.getRunnable();
        Thread thread = looperDetails.getThread();

        /* Kill the thread if it is not terminated already */
        if (!thread.getState().toString().equals(GlobalConstants.TERMINATED)) {

            /* Shutdown the thread and wait to terminate */
            runnable.shutdown();
            thread.join();

            /* Log the kill details */
            this.loggerService.logKillLooperDetails(thread);

            /* Return looper export DTO with the details */
            return this.generateLooperExportDTO(runnable, thread);
        } else {

            /* If the thread is already terminated return null */
            return null;
        }
    }

    @Override
    public LooperArrayList<LooperExportDTO> killAllLoopers() throws InterruptedException {

        LooperArrayList<LooperExportDTO> killedLoopersExportDtoList = new LooperArrayListImpl<>();

        /* Kill all threads */
        for (Long threadId : loopersDetails.keySet()) {

            /* Kill current thread */
            LooperExportDTO killedLooperDetails = this.killLooper(threadId);

            if (killedLooperDetails != null) {
                /* Add current DTO to the export list in case it is not already terminated */
                killedLoopersExportDtoList.add(killedLooperDetails);
            }
        }

        /* Prepare details for all killed threads and return them */
        return killedLoopersExportDtoList;
    }

    private LooperExportDTO generateLooperExportDTO(LooperRunnable ldRunnable, Thread ldThread) {

        /* Create new looper export DTO with all needed details */
        LooperExportDTO looperExportDTO = new LooperExportDTO(
                ldThread.getId(),
                ldThread.getName(),
                ldThread.getState().toString(),
                ldRunnable.getLoopsCount(),
                ldRunnable.getSleepMilliseconds(),
                ldRunnable.getText()
        );

        /* Return the fully equipped DTO */
        return looperExportDTO;
    }
}
