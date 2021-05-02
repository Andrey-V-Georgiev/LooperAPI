package com.looper_api.services;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface FileService {

    List<String> readFileToLines(String filePath) throws IOException;

    void writeFile(String content, String filePath) throws IOException;

    boolean createDir(String dirPath);

    void addLineToFile(String rowContent, String filePath) throws IOException, URISyntaxException;
}
