package com.mg.minio_tryings.controller;


import com.mg.minio_tryings.dto.AddVideoToACourseRequest;
import com.mg.minio_tryings.service.VideoService;
import io.minio.errors.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/v1/media/video")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadVideoFile(@ModelAttribute AddVideoToACourseRequest request) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        videoService.uploadVideoFile(request.file(), request.username(), request.courseId());

        return ResponseEntity.ok("✅ Video yüklendi");
    }


}