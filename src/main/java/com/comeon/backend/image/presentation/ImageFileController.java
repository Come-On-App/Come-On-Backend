package com.comeon.backend.image.presentation;

import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.image.domain.FileManager;
import com.comeon.backend.image.presentation.request.ImageRemoveRequest;
import com.comeon.backend.image.presentation.response.ImageRemoveResponse;
import com.comeon.backend.image.presentation.response.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageFileController {

    private final FileManager fileManager;

    @PostMapping
    public ImageUploadResponse uploadImage(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                           @RequestParam("image") MultipartFile imageFile) {
        return new ImageUploadResponse(fileManager.upload(imageFile, jwtPrincipal.getUserId()));
    }

    @DeleteMapping
    public ImageRemoveResponse removeImage(@RequestBody ImageRemoveRequest request) {
        fileManager.delete(request.getImageUrl());
        return new ImageRemoveResponse();
    }
}
