package com.myblog;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class MinioConfiguration {
    @Value("${FILE_STORAGE_ENDPOINT}")
    private String endpoint;

    @Value("${FILE_STORAGE_USER}")
    private String user;

    @Value("${FILE_STORAGE_PASSWORD}")
    private String password;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(user, password)
                .build();
    }
}
