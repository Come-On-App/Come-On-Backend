package com.comeon.backend.image.application;

import org.springframework.web.multipart.MultipartFile;

public interface FileManager {

    ImageDto.UploadResponse upload(MultipartFile multipartFile, Long userId);

    void delete(ImageDto.RemoveRequest imageUrl);
}
