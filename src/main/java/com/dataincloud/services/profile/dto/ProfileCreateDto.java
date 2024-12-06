package com.dataincloud.services.profile.dto;

import com.dataincloud.core.profile.Profile;
import com.dataincloud.services.profile.dto.validator.ExistingUser;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class ProfileCreateDto {
    @NotNull(message = "User id cannot be null")
    @Positive
    @ExistingUser
    private Long userId;
    @NotBlank
    private String photoPath;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    @PastOrPresent(message = "Birthdate cannot be greater than today")
    private LocalDate birthDate;
    @NotEmpty
    private List<Profile.ProfileTags> tags;
}
