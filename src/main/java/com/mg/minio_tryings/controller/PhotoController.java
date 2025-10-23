package com.mg.minio_tryings.controller;

import com.mg.minio_tryings.dto.AddProfilePhotoRequest;
import com.mg.minio_tryings.service.PhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/media/photo")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addVideoToCourse(
            @ModelAttribute AddProfilePhotoRequest request
    ) throws Exception {

        photoService.uploadProfilePhoto(request.file(), request.username());

        return ResponseEntity.ok("✅ Photo yüklendi");
    }

}
