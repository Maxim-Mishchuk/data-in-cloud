package com.dataincloud.services.user.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter @Setter
public class BasicUserDto {
    @Positive(message = "Incorrect user's id")
    private Long id;
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 32, message = "Username cannot be larger than 32 symbols")
    private String username;
    @Past(message = "Incorrect birthdate")
    private Calendar birthDate;
}
