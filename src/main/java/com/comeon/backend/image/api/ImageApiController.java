package com.comeon.backend.image.api;

import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.image.application.FileManager;
import com.comeon.backend.image.application.ImageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageApiController {

    private final FileManager fileManager;

    @PostMapping
    public ImageDto.UploadResponse uploadImage(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                               @RequestParam("image") MultipartFile imageFile) {
        return fileManager.upload(imageFile, jwtPrincipal.getUserId());
    }

    @DeleteMapping
    public ImageRemoveResponse removeImage(@RequestBody ImageDto.RemoveRequest request) {
        fileManager.delete(request);
        return new ImageRemoveResponse();
    }
}
