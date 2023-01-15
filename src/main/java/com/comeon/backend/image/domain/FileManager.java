package com.comeon.backend.image.domain;

import org.springframework.web.multipart.MultipartFile;

public interface FileManager {

    String upload(MultipartFile multipartFile, Long userId);

    void delete(String imageUrl);
}
