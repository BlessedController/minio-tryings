package com.mg.minio_tryings.enums;

public enum AllowedPhotoMimeTypes {

    IMAGE_JPG("image/jpg"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png");

    private final String type;

    AllowedPhotoMimeTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static boolean isPhotoMimeTypeAllowed(String mimeType) {
        for (AllowedPhotoMimeTypes allowed : values()) {
            if (allowed.type.equalsIgnoreCase(mimeType)) {
                return true;
            }
        }
        return false;
    }
}
