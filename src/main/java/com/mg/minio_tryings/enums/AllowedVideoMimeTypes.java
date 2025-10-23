package com.mg.minio_tryings.enums;

public enum AllowedVideoMimeTypes {

    VIDEO_MP4("video/mp4"),
    VIDEO_MPEG("video/mpeg");

    private final String type;

    AllowedVideoMimeTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public static boolean isVideoMimeTypeAllowed(String mimeType) {
        for (AllowedVideoMimeTypes allowed : values()) {
            if (allowed.type.equalsIgnoreCase(mimeType)) {
                return true;
            }
        }
        return false;
    }


}
