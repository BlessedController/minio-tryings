package com.mg.minio_tryings.dto;

import org.springframework.web.multipart.MultipartFile;

public record AddVideoToACourseRequest(
        MultipartFile file,
        String username,
        String courseId
) {
}
