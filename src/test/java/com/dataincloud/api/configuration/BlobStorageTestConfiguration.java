package com.dataincloud.api.configuration;

import com.azure.storage.blob.BlobServiceClient;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BlobStorageTestConfiguration {

    @Bean
    public BlobServiceClient blobServiceClient() {
        return Mockito.mock(BlobServiceClient.class);
    }
}
