package com.comeon.backend.image.infrastructure;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.comeon.backend.common.exception.CommonErrorCode;
import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.image.domain.FileManager;
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
    public String upload(MultipartFile multipartFile, Long userId) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            // TODO 예외 처리
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }

        String filename = getFilenameToStore(multipartFile);
        String fullPath = getFullPath(userId, filename);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(
                    new PutObjectRequest(bucket, fullPath, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            // TODO 예외 처리
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }

        return amazonS3.getUrl(bucket, fullPath).toString();
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
    public void delete(String imageUrl) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, getStoredPathFrom(imageUrl)));
    }

    private String getStoredPathFrom(String imageUrl) {
        final String prefix = "https://" + bucket + ".s3." + region + ".amazonaws.com/";
        return imageUrl.substring(prefix.length());
    }
}
