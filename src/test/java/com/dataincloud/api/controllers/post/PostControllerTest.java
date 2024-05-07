package com.dataincloud.api.controllers.post;

import com.dataincloud.api.Application;
import com.dataincloud.services.post.dto.PostCreateDto;
import com.dataincloud.services.post.dto.PostDto;
import com.dataincloud.services.user.dto.BasicUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Calendar;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    static class PostCreateDtoBuilder {
        private String header;
        private String description;

        public PostCreateDtoBuilder() {
        }

        public PostCreateDtoBuilder header(String header) {
            this.header = header;
            return this;
        }

        public PostCreateDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PostCreateDto build() {
            PostCreateDto postCreateDto = new PostCreateDto();
            postCreateDto.setHeader(header);
            postCreateDto.setDescription(description);
            return postCreateDto;
        }
    }

    @Test
    void createValidPostTest() throws Exception {
        PostCreateDto newPost = new PostCreateDtoBuilder()
                .header("header")
                .description("description")
                .build();

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPost))
                )
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("setInvalidPosts")
    void createInvalidPostTest(PostCreateDto newPost) throws Exception {
        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPost))
                )
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("setIdsForExistingUsers")
    void getExistingPostByIdTest(Long id) throws Exception {
        mockMvc.perform(get("/posts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @ParameterizedTest
    @MethodSource("setIdsForNonExistentPosts")
    void getNonExistentPostByIdTest(Long id) throws Exception {
        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllPostsTest() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void updatePostTest() throws Exception {
        PostCreateDto newPost = new PostCreateDtoBuilder()
                .header("new_header")
                .description("new_description")
                .build();

        MvcResult result = mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPost)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        PostDto postForEditing = objectMapper.readValue(result.getResponse().getContentAsString(), PostDto.class);
        postForEditing.setHeader("edited_header");
        postForEditing.setCreatedDate(Calendar.getInstance());
        postForEditing.setUser(new BasicUserDto());

        mockMvc.perform(
                        put("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postForEditing))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @ParameterizedTest
    @MethodSource("setIdsForExistingUsers")
    void deleteExistingPostsTest(Long id) throws Exception {
        mockMvc.perform(delete("/posts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @ParameterizedTest
    @MethodSource("setIdsForNonExistentPosts")
    void deleteNonExistentPostsTest(Long id) throws Exception {
        mockMvc.perform(delete("/posts/{id}", id))
                .andExpect(status().isNotFound());
    }

    static Stream<Arguments> setInvalidPosts() {
        return Stream.of(
                Arguments.of(new PostCreateDtoBuilder().header(null).description("description").build()),
                Arguments.of(new PostCreateDtoBuilder().header("").description("description").build()),
                Arguments.of(new PostCreateDtoBuilder().header("     ").description("description").build()),
                Arguments.of(new PostCreateDtoBuilder().header("abc").description("description").build()),
                Arguments.of(new PostCreateDtoBuilder().header(
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                ).description("description").build())
        );
    }

    static Stream<Arguments> setIdsForExistingUsers() {
        return LongStream.range(1, 101).mapToObj(Arguments::of);
    }

    static Stream<Arguments> setIdsForNonExistentPosts() {
        return LongStream.range(120, 131).mapToObj(Arguments::of);
    }

}