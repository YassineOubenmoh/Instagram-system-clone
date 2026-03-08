package com.yassine.insta_clone_backend.services;

import com.yassine.insta_clone_backend.exception.UnauthorizedExtensionException;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    private final MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    public String uploadImage(MultipartFile imageFile) throws Exception{
        String fileName = imageFile.getOriginalFilename();
        assert fileName != null;
        String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
        List<String> allowedExtensions = Arrays.asList(".png", ".jpeg", ".jpg");
        if (!allowedExtensions.contains(fileExtension)){
            throw new UnauthorizedExtensionException("Extension not allowed !");
        }
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uniqueFileName = fileName.replace(fileExtension, "_" + timestamp );

        InputStream fileStream = imageFile.getInputStream();

        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(uniqueFileName)
                .stream(fileStream, imageFile.getSize(), -1)
                .contentType(imageFile.getContentType())
                .build();

        ObjectWriteResponse response = minioClient.putObject(putObjectArgs);

        return uniqueFileName;
    }

    public InputStream getFile(String fileName) throws Exception {
        try {
            GetObjectResponse in = minioClient
                    .getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            return in;
        } catch (Exception e) {
            logger.error("Error occurred while downloading the file: " + e.getMessage());
            throw e;
        }
    }

}
