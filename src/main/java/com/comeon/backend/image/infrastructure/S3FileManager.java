package com.comeon.backend.image.infrastructure;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.comeon.backend.common.error.CommonErrorCode;
import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.common.error.ImageErrorCode;
import com.comeon.backend.image.application.FileManager;
import com.comeon.backend.image.application.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3FileManager implements FileManager {

    private final AmazonS3 amazonS3;

    @Value("${s3.dir-name}")
    private String dirName;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Override
    public ImageDto.UploadResponse upload(MultipartFile multipartFile, Long userId) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new RestApiException("이미지 파일이 비어있습니다.", ImageErrorCode.NO_IMAGE_FILE);
        }

        String filename = getFilenameToStore(multipartFile);
        String fullPath = getFullPath(userId, filename);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fullPath, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RestApiException(e, CommonErrorCode.INTERNAL_SERVER_ERROR);
        }

        return new ImageDto.UploadResponse(amazonS3.getUrl(bucket, fullPath).toString());
    }

    private String getFilenameToStore(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        return generateRandomFilename() + "." + getExt(originalFilename);
    }

    private String generateRandomFilename() {
        return UUID.randomUUID().toString();
    }

    private String getExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

    private String getFullPath(Long userId, String filename) {
        return dirName + "/" + userId + "/" + filename;
    }

    @Override
    public void delete(ImageDto.RemoveRequest request) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, getStoredPathFrom(request.getImageUrl())));
    }

    private String getStoredPathFrom(String imageUrl) {
        final String prefix = "https://" + bucket + ".s3." + region + ".amazonaws.com/";
        return imageUrl.substring(prefix.length());
    }
}
