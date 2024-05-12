package com.dataincloud.api.configuration;

import com.azure.storage.blob.BlobServiceClient;
import com.dataincloud.core.post.IPostRepository;
import com.dataincloud.core.profile.IProfileRepository;
import com.dataincloud.core.user.IUserRepository;
import com.dataincloud.services.post.PostService;
import com.dataincloud.services.profile.ProfileService;
import com.dataincloud.services.profilebyuser.ProfileByUserOrchestrator;
import com.dataincloud.services.profilebyuser.ProfileByUserRabbitOrchestrator;
import com.dataincloud.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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

    @Bean
    public ProfileByUserOrchestrator profileByUserService(String containerName, UserService userService, ProfileService profileService, BlobServiceClient blobServiceClient) {
        return new ProfileByUserOrchestrator(containerName, userService, profileService, blobServiceClient);
    }

    @Bean
    @Primary
    public ProfileByUserOrchestrator profileByUserRabbitService(String containerName, UserService userService, ProfileService profileService, BlobServiceClient blobServiceClient, RabbitTemplate rabbitTemplate) {
        return new ProfileByUserRabbitOrchestrator(containerName, userService, profileService, blobServiceClient, rabbitTemplate);
    }
}
