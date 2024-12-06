package com.dataincloud.api.configuration;

import com.azure.storage.blob.BlobServiceClient;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BlobStorageTestConfiguration {

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Bean
    public String containerName() {
        return containerName;
    }

    @Bean
    public BlobServiceClient blobServiceClient() {
        return Mockito.mock(BlobServiceClient.class);
    }
}
