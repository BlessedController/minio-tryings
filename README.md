📦 MinIO Tryings

A Spring Boot application designed to experiment with MinIO integration for uploading and managing media files (photos and videos).
It provides REST APIs for file uploads and handles storage via MinIO’s object storage service.

🚀 Features

📁 Photo upload (PhotoService)

🎞️ Video upload (VideoService)

⚙️ MinIO integration via MinioClient

🔒 MIME type validation through AllowedPhotoMimeTypes and AllowedVideoMimeTypes

❗ Centralized exception handling (GlobalExceptionHandler, InvalidFileFormatException)

🧱 Layered architecture: Controller → Service → MinIO

🧰 Tech Stack

Java 17+

Spring Boot 3

MinIO SDK

Gradle

Docker Compose

⚙️ Setup
1️⃣ Create .env file
MINIO_ROOT_USER=root_user
MINIO_ROOT_PASSWORD=root_pass

2️⃣ Start MinIO with Docker
docker compose up -d

3️⃣ Run the application
./gradlew bootRun

📤 Example API Requests
Upload Photo
curl -X POST http://localhost:8080/v1/media/photo/upload \
  -F "file=@D:/path/to/photo.jpg" \
  -F "username=mahabbat"

Upload Video
curl -X POST http://localhost:8080/v1/media/video/upload \
  -F "file=@D:/path/to/video.mp4" \
  -F "author=mahabbat" \
  -F "courseId=1"

📂 Project Structure
'''
src/
└── main/
    ├── java/
    │   └── com/mg/minio_tryings/
    │       ├── config/
    │       │   └── MinioConfig.java
    │       ├── controller/
    │       │   ├── PhotoController.java
    │       │   └── VideoController.java
    │       ├── dto/
    │       │   ├── AddProfilePhotoRequest.java
    │       │   └── AddVideoToACourseRequest.java
    │       ├── enums/
    │       │   ├── AllowedPhotoMimeTypes.java
    │       │   └── AllowedVideoMimeTypes.java
    │       ├── exception/
    │       │   ├── GlobalExceptionHandler.java
    │       │   └── InvalidFileFormatException.java
    │       ├── service/
    │       │   ├── FileService.java
    │       │   ├── PhotoService.java
    │       │   └── VideoService.java
    │       └── FileTryingsApplication.java
    └── resources/
        └── application.properties
  '''

👨‍💻 Author

Məhəbbət Gözəlov
Backend Java Developer ☕
