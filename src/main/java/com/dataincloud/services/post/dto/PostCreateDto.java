package com.dataincloud.services.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostCreateDto {
    @NotBlank
    @Size(min = 4, max = 64, message = "Header cannot be larger than 64 symbols")
    private String header;
    private String description;
}
