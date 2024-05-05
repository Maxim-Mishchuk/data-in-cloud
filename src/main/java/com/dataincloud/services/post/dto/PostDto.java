package com.dataincloud.services.post.dto;

import com.dataincloud.services.user.dto.BasicUserDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostDto extends BasicPostDto{
    @NotNull
    private BasicUserDto user;
}
