package com.dataincloud.services.profilebyuser;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.TaggedBlobItem;
import com.dataincloud.core.exceptions.ResourceNotFoundException;

import com.dataincloud.services.profile.ProfileService;
import com.dataincloud.services.profile.dto.ProfileDto;
import com.dataincloud.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ProfileByUserOrchestrator {
    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String connectionString;
    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    private final UserService userService;
    private final ProfileService profileService;

    private BlobContainerClient getContainerClient() {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        return blobServiceClient.getBlobContainerClient(containerName);
    }

    public void createConnectionBetweenUserAndProfile(Long userId, UUID profileId) {
        if (!userService.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }
        if (!profileService.existsById(profileId)) {
            throw new ResourceNotFoundException("Profile not found");
        }

        String connectionName = String.format("%s_%s", userId, profileId);
        var containerClient = getContainerClient();
        BlobClient blob = containerClient.getBlobClient(connectionName);
        blob.upload(InputStream.nullInputStream());
        blob.setTags(Map.of("userId", userId.toString()));
    }

    public ProfileDto getProfileByUserAndProfileIds(Long userId, UUID profileId) {
        String connectionName = String.format("%s_%s", userId, profileId);
        var containerClient = getContainerClient();

        containerClient.getBlobClient(connectionName).exists();

        if (!containerClient.getBlobClient(connectionName).exists()) {
            throw new ResourceNotFoundException("User_Profile connection not found");
        }

        return profileService.readById(profileId);
    }

    public List<ProfileDto> getAllProfilesByUserId(Long userId) {
        var containerClient = getContainerClient();
        var list = containerClient.findBlobsByTags(String.format("\"userId\"='%d'", userId));

        List<ProfileDto> profiles = new LinkedList<>();
        for (TaggedBlobItem blob : list) {
            UUID currentProfileId = UUID.fromString(blob.getName().split("_")[1]);
            profiles.add(profileService.readById(currentProfileId));
        }

        return profiles;
    }

    public void deleteConnectionBetweenUserAndProfile(Long userId, UUID profileId) {
        String connectionName = String.format("%s_%s", userId, profileId);
        var containerClient = getContainerClient();
        BlobClient blob = containerClient.getBlobClient(connectionName);
        if (!blob.exists()) {
            throw new ResourceNotFoundException("User_Profile connection not found");
        }
        blob.delete();
    }
}
