package com.dataincloud.api.controllers.profilebyuser;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.dataincloud.api.Application;
import com.dataincloud.api.configuration.BlobStorageTestConfiguration;
import com.dataincloud.api.configuration.MongoDbTestConfiguration;
import com.dataincloud.api.configuration.PostgresTestConfiguration;
import com.dataincloud.api.configuration.RabbitTestConfiguration;
import com.dataincloud.api.controllers.profile.ProfileControllerIntegrationTest;
import com.dataincloud.dal.profile.ProfileDocument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.dataincloud.core.profile.Profile.ProfileTags.EDUCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@Import({PostgresTestConfiguration.class, MongoDbTestConfiguration.class, RabbitTestConfiguration.class, BlobStorageTestConfiguration.class})
@AutoConfigureMockMvc
class ProfileByUserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BlobServiceClient blobServiceClient;

    @Mock
    BlobContainerClient blobContainerClient;

    @Mock
    BlobClient blobClient;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static UUID testProfileId;

    @BeforeEach
    void init() {
        ProfileDocument testProfile = new ProfileControllerIntegrationTest.ProfileDocumentBuilder()
                .id(UUID.randomUUID())
                .userId(1L)
                .photo("/photo.jpg")
                .firstName("FirstName1")
                .lastName("LastName1")
                .birthDate(LocalDate.of(2002, 3, 18))
                .tagsList(List.of(EDUCATION))
                .build();
        mongoTemplate.save(testProfile);
        testProfileId = testProfile.getId();

        Mockito.when(blobServiceClient.getBlobContainerClient(containerName)).thenReturn(blobContainerClient);
        Mockito.when(blobContainerClient.getBlobClient(Mockito.anyString())).thenReturn(blobClient);
    }

    @AfterEach
    void cleanDb() {
        mongoTemplate.dropCollection("profiles");
    }

    @Test
    void createNewConnectionWithExistingIds() throws Exception{
        Long userId = 1L;
        UUID profileId = testProfileId;

        mockMvc.perform(
                post("/users/{userId}/profiles/{profileId}", userId, profileId)
        ).andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("setNonExistentPairs")
    void createNewConnectionWithNonExistentIds(Long userId, UUID profileId) throws Exception {
        mockMvc.perform(
                post("/users/{userId}/profiles/{profileId}", userId, profileId)
        ).andExpect(status().isNotFound());
    }

    @Test
    void getConnectionWithExistingIds() throws Exception {
        Mockito.when(blobClient.exists()).thenReturn(true);

        Long userId = 1L;
        UUID profileId = testProfileId;

        mockMvc.perform(
                get("/users/{userId}/profiles/{profileId}", userId, profileId)
        ).andExpect(status().isOk());
    }

    @Test
    void getConnectionWithNonExistentIds() throws Exception {
        Mockito.when(blobClient.exists()).thenReturn(false);

        Long userId = 0L;
        UUID profileId = UUID.randomUUID();

        mockMvc.perform(
                get("/users/{userId}/profiles/{profileId}", userId, profileId)
        ).andExpect(status().isNotFound());
    }

    @Test
    void getConnections() throws Exception {
        Long userId = 1L;

        PagedIterable pagedIterable = Mockito.mock(PagedIterable.class);
        Mockito.when(blobContainerClient.findBlobsByTags(String.format("\"userId\"='%d'", userId)))
                        .thenReturn(pagedIterable);
        Mockito.when(pagedIterable.iterator()).thenReturn(Collections.emptyIterator());

        mockMvc.perform(
                get("/users/{userId}/profiles", userId)
        ).andExpect(status().isOk());
    }

    @Test
    void deleteExistingConnection() throws Exception {
        Mockito.when(blobClient.exists()).thenReturn(true);

        Long userId = 1L;
        UUID profileId = testProfileId;

        mockMvc.perform(
                delete("/users/{userId}/profiles/{profileId}", userId, profileId)
        ).andExpect(status().isNoContent());
    }

    @Test
    void deleteNonExistentConnection() throws Exception {
        Mockito.when(blobClient.exists()).thenReturn(false);

        Long userId = 0L;
        UUID profileId = UUID.randomUUID();

        mockMvc.perform(
                delete("/users/{userId}/profiles/{profileId}", userId, profileId)
        ).andExpect(status().isNotFound());
    }

    static Stream<Arguments> setNonExistentPairs() {
        return Stream.of(
                Arguments.of(1L, UUID.randomUUID()),
                Arguments.of(0L, testProfileId),
                Arguments.of(-5L, UUID.randomUUID())
        );
    }
}