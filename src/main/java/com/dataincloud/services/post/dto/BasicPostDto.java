package com.dataincloud.services.post.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter @Setter
public class BasicPostDto {
    @Positive(message = "Incorrect post's id")
    private Long id;
    @NotBlank
    @Size(min = 4, max = 64, message = "Header cannot be larger than 64 symbols")
    private String header;
    private String description;
    @NotNull(message = "Date of creation cannot be null")
    private Calendar createdDate;
}
