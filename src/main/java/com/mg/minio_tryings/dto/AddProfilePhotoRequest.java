package com.mg.minio_tryings.dto;

import org.springframework.web.multipart.MultipartFile;

public record AddProfilePhotoRequest(
        MultipartFile file,
        String username
) {
}
