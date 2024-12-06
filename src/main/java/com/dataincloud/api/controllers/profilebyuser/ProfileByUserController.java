package com.dataincloud.api.controllers.profilebyuser;

import com.dataincloud.services.profile.dto.ProfileDto;
import com.dataincloud.services.profilebyuser.ProfileByUserOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProfileByUserController {
    private final ProfileByUserOrchestrator profileByUserOrchestrator;

    @PostMapping("/users/{userId}/profiles/{profileId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewConnection(@PathVariable Long userId, @PathVariable UUID profileId) {
        profileByUserOrchestrator.createConnectionBetweenUserAndProfile(userId, profileId);
    }

    @GetMapping("/users/{userId}/profiles/{profileId}")
    public ProfileDto getConnection(@PathVariable Long userId, @PathVariable UUID profileId) {
        return profileByUserOrchestrator.getProfileByUserAndProfileIds(userId, profileId);
    }

    @GetMapping("/users/{userId}/profiles")
    public List<ProfileDto> getConnections(@PathVariable Long userId) {
        return profileByUserOrchestrator.getAllProfilesByUserId(userId);
    }

    @DeleteMapping("/users/{userId}/profiles/{profileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConnection(@PathVariable Long userId, @PathVariable UUID profileId) {
        profileByUserOrchestrator.deleteConnectionBetweenUserAndProfile(userId, profileId);
    }
}
