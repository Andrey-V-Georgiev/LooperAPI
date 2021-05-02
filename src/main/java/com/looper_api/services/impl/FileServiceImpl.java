package com.looper_api.services.impl;

import com.looper_api.services.FileService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<String> readFileToLines(String filePath) throws IOException {
        List<String> fileLines = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.length() > 0) {
                fileLines.add(line);
            }
        }
        return fileLines;
    }

    @Override
    public void writeFile(String content, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            Files.write(
                    Paths.get(filePath),
                    Collections.singleton(content),
                    StandardCharsets.UTF_8
            );
        }
    }

    @Override
    public boolean createDir(String dirPath) {
        boolean createdDir = false;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            createdDir = dir.mkdirs();
        }
        return createdDir;
    }

    @Override
    public void addLineToFile(String rowContent, String filePath) throws IOException {
        File file = new File(filePath);
        FileWriter fr = new FileWriter(file, true);
        BufferedWriter br = new BufferedWriter(fr);
        br.write(rowContent);
        br.write(System.lineSeparator());

        br.close();
        fr.close();
    }
}
