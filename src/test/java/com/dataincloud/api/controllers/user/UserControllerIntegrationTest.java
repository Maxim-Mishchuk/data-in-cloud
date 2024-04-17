package com.dataincloud.api.controllers.user;

import com.dataincloud.api.Application;
import com.dataincloud.services.user.dto.UserCreateDto;
import com.dataincloud.services.user.dto.UserDto;
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

import java.util.Calendar;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    static class UserCreateDtoBuilder {
        private String username;
        private Calendar birthDate;

        public UserCreateDtoBuilder() {
        }

        public UserCreateDtoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserCreateDtoBuilder birthDate(int year, int month, int day) {
            birthDate = Calendar.getInstance();
            birthDate.set(year, month, day);
            return this;
        }

        public UserCreateDtoBuilder birthDate(Calendar calendar) {
            birthDate = calendar;
            return this;
        }

        public UserCreateDto build() {
            UserCreateDto userCreateDto = new UserCreateDto();
            userCreateDto.setUsername(username);
            userCreateDto.setBirthDate(birthDate);
            return userCreateDto;
        }
    }

    @Test
    void createValidUserTest() throws Exception {
        UserCreateDto newUser = new UserCreateDtoBuilder()
                .username("new_user")
                .birthDate(2003, Calendar.NOVEMBER, 27)
                .build();

        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser))
                )
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("setInvalidUsers")
    void createInvalidUserTest(UserCreateDto newUser) throws Exception {
        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser))
                )
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("setIdsForExistingUsers")
    void getExistingUserByIdTest(Long id) throws Exception{
        mockMvc.perform(
                get("/users/{id}", id)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @ParameterizedTest
    @MethodSource("setIdsForNonExistentUsers")
    void getNonExistentUserByIdTest(Long id) throws Exception {
        mockMvc.perform(
                get("/users/{id}", id)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsersTest() throws Exception {
        mockMvc.perform(
                get("/users")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateUserTest() throws Exception {
        UserDto editedUser = new UserDto();
        editedUser.setId(1L);
        editedUser.setUsername("new_name");
        editedUser.setBirthDate(Calendar.getInstance());

        mockMvc.perform(
                        put("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(editedUser))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("new_name"));
    }

    @ParameterizedTest
    @MethodSource("setIdsForExistingUsers")
    void deleteExistingUserTest(Long id) throws Exception {
        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @ParameterizedTest
    @MethodSource("setIdsForNonExistentUsers")
    void deleteNonExistentUserTest(Long id) throws Exception {
        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNotFound());
    }

    static Stream<Arguments> setInvalidUsers() {
        return Stream.of(
                Arguments.of(new UserCreateDtoBuilder().username(null).birthDate(2003, Calendar.NOVEMBER, 27).build()),
                Arguments.of(new UserCreateDtoBuilder().username("").birthDate(2003, Calendar.NOVEMBER, 27).build()),
                Arguments.of(new UserCreateDtoBuilder().username(" ").birthDate(2003, Calendar.NOVEMBER, 27).build()),
                Arguments.of(new UserCreateDtoBuilder().username("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").birthDate(2003, Calendar.NOVEMBER, 27).build()),
                Arguments.of(new UserCreateDtoBuilder().username("test").birthDate(null).build()),
                Arguments.of(new UserCreateDtoBuilder().username("test").birthDate(2033, Calendar.NOVEMBER, 27).build())
        );
    }

    static Stream<Arguments> setIdsForExistingUsers() {
        return LongStream.range(1, 51).mapToObj(Arguments::of);
    }

    static Stream<Arguments> setIdsForNonExistentUsers() {
        return LongStream.range(60, 71).mapToObj(Arguments::of);
    }
}
