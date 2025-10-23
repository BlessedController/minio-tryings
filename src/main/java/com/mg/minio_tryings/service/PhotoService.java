package com.mg.minio_tryings.service;

import com.mg.minio_tryings.exception.InvalidFileFormatException;
import io.minio.*;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.mg.minio_tryings.enums.AllowedPhotoMimeTypes.isPhotoMimeTypeAllowed;

@Service
public class PhotoService {

    private final MinioClient minioClient;

    @Value("${minio.profile-photo-bucket:user-profile-photos}")
    private String bucketName;

    private final Logger log = LoggerFactory.getLogger(PhotoService.class);


    public PhotoService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }


    public void uploadProfilePhoto(MultipartFile file, String username) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        if (file == null || file.isEmpty()) {
            throw new InvalidFileFormatException("File is empty. Please select a valid file to upload");
        }

        String mimeType = file.getContentType();

        if (mimeType == null || !isPhotoMimeTypeAllowed(mimeType)) {
            throw new InvalidMimeTypeException(mimeType == null ? "Unkonwn mime type" : mimeType,
                    "Invalid mime type");
        }

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
                    .object(username)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();

            minioClient.putObject(args);

            System.out.println(file.getContentType());
            log.info("✅ Dosya yüklendi: {}", file.getOriginalFilename());

        }
    }
}
