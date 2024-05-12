package com.dataincloud.services.profilebyuser;

import com.azure.storage.blob.BlobServiceClient;
import com.dataincloud.services.profile.ProfileService;
import com.dataincloud.services.profile.dto.ProfileDto;
import com.dataincloud.services.user.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
public class ProfileByUserRabbitOrchestrator extends ProfileByUserOrchestrator {
    private final ProfileByUserOrchestrator profileByUserOrchestrator;
    private final RabbitTemplate rabbitTemplate;

    public ProfileByUserRabbitOrchestrator(String containerName, UserService userService, ProfileService profileService, BlobServiceClient blobServiceClient, RabbitTemplate rabbitTemplate) {
        super(containerName, userService, profileService, blobServiceClient);
        this.profileByUserOrchestrator = new ProfileByUserOrchestrator(containerName, userService, profileService, blobServiceClient);
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createConnectionBetweenUserAndProfile(Long userId, UUID profileId) {
        profileByUserOrchestrator.createConnectionBetweenUserAndProfile(userId, profileId);
        log.info(String.format("Sent message to RabbitMq exchange for adding: %s_%s", userId, profileId));
        rabbitTemplate.convertAndSend("message-direct", "profile-by-user.add", String.format("%s_%s", userId, profileId));
    }

    public ProfileDto getProfileByUserAndProfileIds(Long userId, UUID profileId) {
        return profileByUserOrchestrator.getProfileByUserAndProfileIds(userId, profileId);
    }

    public List<ProfileDto> getAllProfilesByUserId(Long userId) {
        return profileByUserOrchestrator.getAllProfilesByUserId(userId);
    }

    public void deleteConnectionBetweenUserAndProfile(Long userId, UUID profileId) {
        profileByUserOrchestrator.deleteConnectionBetweenUserAndProfile(userId, profileId);
        log.info(String.format("Sent message to RabbitMq exchange for deleting: %s_%s", userId, profileId));
        rabbitTemplate.convertAndSend("message-direct", "profile-by-user.delete", String.format("%s_%s", userId, profileId));
    }
}
