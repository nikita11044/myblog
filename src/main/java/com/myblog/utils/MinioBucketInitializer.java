package com.myblog.utils;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class MinioBucketInitializer {
    private final MinioClient minioClient;

    public MinioBucketInitializer(MinioClient minioClient) {
        this.minioClient = minioClient;
        createBucketIfNotExists();
    }

    private void createBucketIfNotExists() {
        try {
            boolean bucketExists = minioClient.bucketExists(io.minio.BucketExistsArgs.builder().bucket("myblog-bucket").build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("myblog-bucket").build());
            }
        } catch (MinioException | IllegalArgumentException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException e) {
            throw new RuntimeException("Error creating bucket: " + e.getMessage(), e);
        }
    }
}
