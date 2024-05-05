package com.dataincloud.services.user.dto;

import com.dataincloud.services.post.dto.BasicPostDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserDto extends BasicUserDto{
    private List<BasicPostDto> posts;
}
