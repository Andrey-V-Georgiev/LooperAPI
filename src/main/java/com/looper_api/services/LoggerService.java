package com.looper_api.services;


import java.io.IOException;

public interface LoggerService {

    void logLooperIterationDetails(Thread thread, String text, Integer iteration);

    void logLooperExitDetails(Thread thread);

    String logKillLooperDetails(Thread thread);

    String logExceptionDetails(Exception e);

    String findAllLogsDetails() throws IOException;

}
