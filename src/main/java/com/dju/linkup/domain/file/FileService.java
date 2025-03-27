package com.dju.linkup.domain.file;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
public class FileService {

    private final String uploadDir = "uploads/";

    public String saveFile(MultipartFile file) {
        File fileDir = new File(uploadDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        String fileName = UUID.randomUUID().toString();
        File destination = new File(uploadDir + fileName);

        try {
            file.transferTo(destination);
            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
