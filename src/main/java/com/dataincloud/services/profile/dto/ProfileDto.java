package com.dataincloud.services.profile.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class ProfileDto extends ProfileCreateDto {
    @NotNull
    private UUID id;
}
