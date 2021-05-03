package com.looper_api.services.impl;

import com.looper_api.config.ConfigurationProperties;
import com.looper_api.services.FileService;
import com.looper_api.services.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LoggerServiceImpl implements LoggerService {

    private final FileService fileService;
    private final ConfigurationProperties configurationProperties;

    @Autowired
    public LoggerServiceImpl(FileService fileService, ConfigurationProperties configurationProperties) {
        this.fileService = fileService;
        this.configurationProperties = configurationProperties;
    }

    @Override
    public void logLooperIterationDetails(Thread thread, String text, Integer iteration) {

        /* Prepare log string */
        String iterationLogDetails = String.format(
                "Id: %d |Name: %s | State: %s | Message: %s_%d | Iteration: %d | Ldt: %s",
                thread.getId(),
                thread.getName(),
                thread.getState(),
                text, iteration,
                iteration,
                this.localDateTimeNow());

        /* Log details to the file system DB file and print them to console */
        this.logToDbAndConsole(iterationLogDetails);
    }

    @Override
    public void logLooperExitDetails(Thread thread) {

        /* Prepare exit log string */
        String exitLogDetails = String.format(
                "EXIT | Id: %d | State: %s | Ldt: %s",
                thread.getId(),
                thread.getState(),
                this.localDateTimeNow());

        /* Log details to the file system DB file and print them to console */
        this.logToDbAndConsole(exitLogDetails);
    }

    @Override
    public String logKillLooperDetails(Thread thread) {

        /* Prepare kill log string */
        String killLogDetails = String.format(
                "KILL | Id: %d |Name: %s | State: %s | Ldt: %s",
                thread.getId(),
                thread.getName(),
                thread.getState(),
                this.localDateTimeNow());

        /* Log details to the file system DB file and print them to console */
        this.logToDbAndConsole(killLogDetails);

        /* Return kill details as string */
        return killLogDetails;
    }

    @Override
    public String findAllLogsDetails() throws IOException {

        /* Obtain all logged records */
        String dbLogFullPath = this.configurationProperties.getDbLogFullPath();
        List<String> allLogRecords = this.fileService.readFileToLines(dbLogFullPath);

        /* Convert logs to string */
        StringBuilder stringBuilder = new StringBuilder();
        for (String record : allLogRecords) {
            stringBuilder.append(record).append(System.lineSeparator());
        }

        /* Return all log information as string representation */
        return stringBuilder.toString();
    }

    @Override
    public String logExceptionDetails(Exception e) {

        /* Prepare exception log string */
        String exceptionLogDetails = String.format(
                "EXCEPTION | Message: %s | Ldt: %s",
                e.getMessage(),
                this.localDateTimeNow());

        /* Log details to the file system DB file and print them to console */
        this.logToDbAndConsole(exceptionLogDetails);

        /* Return exception details as string */
        return exceptionLogDetails;
    }

    private void logToDbAndConsole(String logDetails) {

        /* Log details to the file system DB file */
        try {
            this.fileService.addLineToFile(
                    logDetails,
                    this.configurationProperties.getDbLogFullPath()
            );
        } catch (IOException | URISyntaxException e) {
            this.logExceptionDetails(e);
        }

        /* Print log details to console */
        System.out.println(logDetails);
    }

    private String localDateTimeNow() {

        /* Obtain time now in this current format */
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return now.format(format);
    }
}
