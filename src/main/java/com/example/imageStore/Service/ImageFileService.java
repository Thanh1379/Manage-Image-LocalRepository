package com.example.imageStore.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageFileService {
    @Value("${app.static.path}")
    private String staticFilePath;
    public void addFileToStore(String fileFullName, InputStream fileInputStream) throws Exception {
        try (fileInputStream) {
            String fullPath = staticFilePath + fileFullName;
            URI uri = new URI(fullPath);
            File file = new File(uri);
            if(file.exists()) {
                throw new Exception("File already exists.");
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                int MAX_BUFFER_SIZE = 1024;
                byte[] buffer = new byte[MAX_BUFFER_SIZE];
                boolean endOfFile = false;
                while (!endOfFile) {
                    int bytesRead = fileInputStream.read(buffer, 0, MAX_BUFFER_SIZE);
                    if (bytesRead == -1) {
                        endOfFile = true;
                    } else {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }
    public void removeFileInStore(String fileFullName) throws Exception {
        String fullPath = staticFilePath + fileFullName;
        URI uri = new URI(fullPath);
        File file = new File(uri);
        if (file.exists()) {
            file.delete();
        } else {
            throw new Exception("File does not exist.");
        }
    }
}
