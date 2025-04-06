package service;

import com.myblog.service.MinioService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MinioServiceTest {

    @Mock
    private MinioClient minioClient;

    @InjectMocks
    private MinioService minioService;

    private String fileName;
    private InputStream fileStream;
    private long size;
    private String contentType;

    @BeforeEach
    void setUp() {
        fileName = "test-file.jpg";
        fileStream = mock(InputStream.class);
        size = 1024L;
        contentType = "image/jpeg";
    }

    @Test
    void uploadFile_ShouldCallMinioClientPutObject() throws Exception {
        minioService.uploadFile(fileName, fileStream, size, contentType);

        verify(minioClient, times(1)).putObject(any(PutObjectArgs.class));
    }

    @Test
    void deleteFile_ShouldCallMinioClientRemoveObject() throws Exception {
        minioService.deleteFile(fileName);

        verify(minioClient, times(1)).removeObject(any(RemoveObjectArgs.class));
    }
}

