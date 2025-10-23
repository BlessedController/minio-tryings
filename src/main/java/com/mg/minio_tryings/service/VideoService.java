package com.mg.minio_tryings.service;


import com.mg.minio_tryings.exception.InvalidFileFormatException;
import io.minio.*;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static com.mg.minio_tryings.enums.AllowedVideoMimeTypes.isVideoMimeTypeAllowed;

@Service
public class VideoService {

    private final MinioClient minioClient;
    private final Logger log = LoggerFactory.getLogger(VideoService.class);

    public VideoService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void uploadVideoFile(MultipartFile file,
                                String username,
                                String courseId
    ) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {


        if (file == null || file.isEmpty()) {
            throw new InvalidFileFormatException("File is empty. Please select a valid file to upload");
        }

        String mimeType = file.getContentType();

        if (mimeType == null || !isVideoMimeTypeAllowed(mimeType)) {
            throw new InvalidMimeTypeException(mimeType == null ? "Unkonwn mime type" : mimeType,
                    "Invalid mime type");
        }

        String bucketName = "course-videos";


        try (InputStream inputStream = file.getInputStream()) {

            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );

            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
            }

            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(username + "/" + courseId + "/" + UUID.randomUUID())
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();

            minioClient.putObject(args);

            System.out.println(file.getContentType());
            log.info("✅ Dosya yüklendi: {}", file.getOriginalFilename());

        }
    }

}

