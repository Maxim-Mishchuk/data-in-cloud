package com.dataincloud.api.configuration;

import com.dataincloud.core.post.IPostRepository;
import com.dataincloud.core.profile.IProfileRepository;
import com.dataincloud.core.user.IUserRepository;
import com.dataincloud.services.post.PostService;
import com.dataincloud.services.profile.ProfileService;
import com.dataincloud.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean
    public UserService userService(IUserRepository userRepository, ModelMapper modelMapper) {
        return new UserService(userRepository, modelMapper);
    }

    @Bean
    public PostService postService(IPostRepository postRepository, ModelMapper modelMapper) {
        return new PostService(postRepository, modelMapper);
    }

    @Bean
    public ProfileService profileService(IProfileRepository profileRepository, ModelMapper modelMapper) {
        return new ProfileService(profileRepository, modelMapper);
    }
}
