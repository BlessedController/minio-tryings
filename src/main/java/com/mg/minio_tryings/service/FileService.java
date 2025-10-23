package com.mg.minio_tryings.service;

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

import static com.mg.minio_tryings.enums.AllowedPhotoMimeTypes.isPhotoMimeTypeAllowed;


@Service
public class FileService {

    private final MinioClient minioClient;
    private final Logger log = LoggerFactory.getLogger(FileService.class);

    public FileService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }


    public void uploadProfilePhoto(MultipartFile file)
            throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try (InputStream inputStream = file.getInputStream()) {

            String bucketName = "user-profile-photos";

            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
            }

            minioClient.putObject(

                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getOriginalFilename())
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()

            );

            System.out.println(file.getContentType());
            log.info("✅ Dosya yüklendi: {}", file.getOriginalFilename());

        }

    }

    public void uploadVideo(MultipartFile file, String authorName, String courseId) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try (InputStream inputStream = file.getInputStream()) {

            String bucketName = "course-videos";
            String objectName = String.format("%s/%s/%s", authorName, courseId, file.getOriginalFilename());

            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
            }

            String mimeType = file.getContentType();

            if (mimeType == null || !isPhotoMimeTypeAllowed(mimeType)) {
                throw new InvalidMimeTypeException(mimeType == null ? "Unkonwn mime type" : mimeType,
                        "Invalid mime type");
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            System.out.println(file.getContentType());
            System.out.println("✅ Dosya yüklendi: " + file.getOriginalFilename());

        }
    }
}
