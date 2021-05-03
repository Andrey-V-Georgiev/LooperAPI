package com.looper_api.config;

import org.springframework.context.annotation.Configuration;


@Configuration
public class ConfigurationProperties {

    private final String dbRootFolderPath = "C:\\LooperAPI_DB";
    private final String dbFileNameTxt = "LooperAPI_DB.txt";
    private final String dbLogFullPath = String.format("%s\\%s", dbRootFolderPath, dbFileNameTxt);

    public ConfigurationProperties() {
    }

    public String getDbRootFolderPath() {
        return dbRootFolderPath;
    }

    public String getDbFileNameTxt() {
        return dbFileNameTxt;
    }

    public String getDbLogFullPath() {
        return dbLogFullPath;
    }
}
