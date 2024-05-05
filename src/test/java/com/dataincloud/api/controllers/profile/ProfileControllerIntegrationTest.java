package com.dataincloud.api.controllers.profile;

import com.dataincloud.api.Application;
import com.dataincloud.core.profile.Profile;
import com.dataincloud.dal.profile.ProfileDocument;
import com.dataincloud.services.profile.dto.ProfileCreateDto;
import com.dataincloud.services.profile.dto.ProfileDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.dataincloud.core.profile.Profile.ProfileTags.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class ProfileControllerIntegrationTest {

    @Container
    private static final MongoDBContainer mongoDb = new MongoDBContainer("mongo:latest").withExposedPorts(27017);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDb::getReplicaSetUrl);
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    private static UUID testProfileId;

    @BeforeEach
    void fillDbWithTestData() {
        ProfileDocument testProfile = new ProfileDocumentBuilder()
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
    }

    @AfterEach
    void cleanDb() {
        mongoTemplate.dropCollection("profiles");
    }

    static class ProfileCreateDtoBuilder {
        private Long userId;
        private String photo;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private List<Profile.ProfileTags> tags;

        public ProfileCreateDtoBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public ProfileCreateDtoBuilder photo(String photo) {
            this.photo = photo;
            return this;
        }

        public ProfileCreateDtoBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ProfileCreateDtoBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ProfileCreateDtoBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public ProfileCreateDtoBuilder birthDate(int year, int month, int dayOfMonth) {
            this.birthDate = LocalDate.of(year, month, dayOfMonth);
            return this;
        }

        public ProfileCreateDtoBuilder tags(List<Profile.ProfileTags> tags) {
            this.tags = tags;
            return this;
        }

        public ProfileCreateDto build() {
            ProfileCreateDto profileCreateDto = new ProfileCreateDto();
            profileCreateDto.setUserId(this.userId);
            profileCreateDto.setPhotoPath(this.photo);
            profileCreateDto.setFirstName(this.firstName);
            profileCreateDto.setLastName(this.lastName);
            profileCreateDto.setBirthDate(this.birthDate);
            profileCreateDto.setTags(this.tags);
            return profileCreateDto;
        }
    }

    static class ProfileDtoBuilder {
        private UUID id;
        private Long userId;
        private String photo;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private List<Profile.ProfileTags> tags;

        private ProfileDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ProfileDtoBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public ProfileDtoBuilder photo(String photo) {
            this.photo = photo;
            return this;
        }

        public ProfileDtoBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ProfileDtoBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ProfileDtoBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public ProfileDtoBuilder birthDate(int year, int month, int dayOfMonth) {
            this.birthDate = LocalDate.of(year, month, dayOfMonth);
            return this;
        }

        public ProfileDtoBuilder tags(List<Profile.ProfileTags> tags) {
            this.tags = tags;
            return this;
        }

        public ProfileDto build() {
            ProfileDto profileDto = new ProfileDto();
            profileDto.setId(this.id);
            profileDto.setUserId(this.userId);
            profileDto.setPhotoPath(this.photo);
            profileDto.setFirstName(this.firstName);
            profileDto.setLastName(this.lastName);
            profileDto.setBirthDate(this.birthDate);
            profileDto.setTags(this.tags);
            return profileDto;
        }
    }

    static class ProfileDocumentBuilder {
        private UUID id;
        private Long userId;
        private String firstName;
        private String lastName;
        private String photo;
        private LocalDate birthDate;
        private List<Profile.ProfileTags> tagsList;

        public ProfileDocumentBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ProfileDocumentBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public ProfileDocumentBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ProfileDocumentBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ProfileDocumentBuilder photo(String photo) {
            this.photo = photo;
            return this;
        }

        public ProfileDocumentBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public ProfileDocumentBuilder tagsList(List<Profile.ProfileTags> tagsList) {
            this.tagsList = tagsList;
            return this;
        }

        public ProfileDocument build() {
            ProfileDocument profileDocument = new ProfileDocument();
            profileDocument.setId(this.id);
            profileDocument.setUserId(this.userId);
            profileDocument.setFirstName(this.firstName);
            profileDocument.setLastName(this.lastName);
            profileDocument.setPhotoPath(this.photo);
            profileDocument.setBirthDate(this.birthDate);
            profileDocument.setTagsList(this.tagsList);
            return profileDocument;
        }
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createValidProfileTest() throws Exception{
        ProfileCreateDto newProfile = new ProfileCreateDtoBuilder()
                .userId(2L)
                .photo("/newPhoto.jpg")
                .firstName("NewFirstName")
                .lastName("NewLastName")
                .birthDate(1999, 7, 11)
                .tags(List.of(BLOG))
                .build();

        mockMvc.perform(
                post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProfile))
        ).andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("setInvalidCreateProfiles")
    void createInvalidProfileTest(ProfileCreateDto invalidProfile) throws Exception{
        mockMvc.perform(
                post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProfile))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateValidProfileTest() throws Exception{
        ProfileDto newProfile = new ProfileDtoBuilder()
                .id(testProfileId)
                .userId(1L)
                .photo("/updatedPhoto.png")
                .firstName("NewFirstName")
                .lastName("NewLastName")
                .birthDate(1999, 7, 11)
                .tags(List.of(BLOG))
                .build();

        mockMvc.perform(
                put("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProfile))
        ).andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("setInvalidUpdateProfiles")
    void updateInvalidProfileTest(ProfileDto invalidProfile) throws Exception{
        mockMvc.perform(
                put("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProfile))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void getAllProfilesTest() throws Exception {
        mockMvc.perform(get("/profiles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void getProfileByExistingIdTest() throws Exception {
        UUID id = testProfileId;

        mockMvc.perform(get("/profiles/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void getProfileByNonExistentIdTest() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(get("/profiles/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteExistingProfileTest() throws Exception {
        UUID id = testProfileId;

        mockMvc.perform(delete("/profiles/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void deleteNonExistentProfileTest() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/profiles/{id}", id))
                .andExpect(status().isNotFound());
    }

    static Stream<Arguments> setInvalidCreateProfiles() {
        return Stream.of(
                Arguments.of(
                        new ProfileCreateDtoBuilder()
                                .userId(51L)
                                .photo("/photo.jpg")
                                .firstName("firstName")
                                .lastName("lastName")
                                .birthDate(2000, 1, 27)
                                .tags(List.of(BLOG))
                                .build()
                ),
                Arguments.of(
                        new ProfileCreateDtoBuilder()
                                .userId(2L)
                                .photo("  ")
                                .firstName("firstName")
                                .lastName("lastName")
                                .birthDate(2000, 1, 27)
                                .tags(List.of(BLOG))
                                .build()
                ),
                Arguments.of(
                        new ProfileCreateDtoBuilder()
                                .userId(2L)
                                .photo("/photo.jpg")
                                .firstName("  ")
                                .lastName("lastName")
                                .birthDate(2000, 1, 27)
                                .tags(List.of(BLOG))
                                .build()
                ),
                Arguments.of(
                        new ProfileCreateDtoBuilder()
                                .userId(2L)
                                .photo("/photo.jpg")
                                .firstName("firstName")
                                .lastName(" ")
                                .birthDate(2000, 1, 27)
                                .tags(List.of(BLOG))
                                .build()
                ),
                Arguments.of(
                        new ProfileCreateDtoBuilder()
                                .userId(2L)
                                .photo("/photo.jpg")
                                .firstName("firstName")
                                .lastName(" ")
                                .birthDate(2030, 1, 27)
                                .tags(List.of(BLOG))
                                .build()
                ),
                Arguments.of(
                        new ProfileCreateDtoBuilder()
                                .userId(2L)
                                .photo("/photo.jpg")
                                .firstName("firstName")
                                .lastName(" ")
                                .birthDate(2000, 1, 27)
                                .tags(List.of())
                                .build()
                )
        );
    }

    static Stream<Arguments> setInvalidUpdateProfiles() {
        return Stream.of(
                Arguments.of(
                        new ProfileDtoBuilder()
                                .id(null)
                                .userId(2L)
                                .photo("/photo.jpg")
                                .firstName("firstName")
                                .lastName("lastName")
                                .birthDate(2000, 1, 27)
                                .tags(List.of(BLOG))
                                .build()
                ),
                Arguments.of(
                        new ProfileDtoBuilder()
                                .id(testProfileId)
                                .userId(51L)
                                .photo("/photo.jpg")
                                .firstName("firstName")
                                .lastName("lastName")
                                .birthDate(2000, 1, 27)
                                .tags(List.of(BLOG))
                                .build()
                ),
                Arguments.of(
                        new ProfileDtoBuilder()
                                .id(testProfileId)
                                .userId(2L)
                                .photo("")
                                .firstName("firstName")
                                .lastName("lastName")
                                .birthDate(2000, 1, 27)
                                .tags(List.of(BLOG))
                                .build()
                ),
                Arguments.of(
                        new ProfileDtoBuilder()
                                .id(testProfileId)
                                .userId(2L)
                                .photo("/photo.jpg")
                                .firstName("  ")
                                .lastName("lastName")
                                .birthDate(2000, 1, 27)
                                .tags(List.of(BLOG))
                                .build()
                ),
                Arguments.of(
                        new ProfileDtoBuilder()
                                .id(testProfileId)
                                .userId(2L)
                                .photo("/photo.jpg")
                                .firstName("firstName")
                                .lastName(" ")
                                .birthDate(2000, 1, 27)
                                .tags(List.of(BLOG))
                                .build()
                ),
                Arguments.of(
                        new ProfileDtoBuilder()
                                .id(testProfileId)
                                .userId(2L)
                                .photo("/photo.jpg")
                                .firstName("firstName")
                                .lastName(" ")
                                .birthDate(2030, 1, 27)
                                .tags(List.of(BLOG))
                                .build()
                ),
                Arguments.of(
                        new ProfileDtoBuilder()
                                .id(testProfileId)
                                .userId(2L)
                                .photo("/photo.jpg")
                                .firstName("firstName")
                                .lastName(" ")
                                .birthDate(2000, 1, 27)
                                .tags(List.of())
                                .build()
                )
        );
    }

}