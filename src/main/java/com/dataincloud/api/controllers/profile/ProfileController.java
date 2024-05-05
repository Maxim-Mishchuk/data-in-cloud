package com.dataincloud.api.controllers.profile;

import com.dataincloud.services.profile.ProfileService;
import com.dataincloud.services.profile.dto.ProfileCreateDto;
import com.dataincloud.services.profile.dto.ProfileDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/profiles")
    public ResponseEntity<ProfileDto> create(@RequestBody @Valid ProfileCreateDto newProfile) {
        ProfileDto createdProfile = profileService.create(newProfile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @GetMapping("/profiles")
    public List<ProfileDto> getAll() {
        return profileService.readAll();
    }

    @GetMapping("/profiles/{id}")
    public ProfileDto getById(@PathVariable UUID id) {
        return profileService.readById(id);
    }

    @PutMapping("/profiles")
    public ResponseEntity<ProfileDto> update(@RequestBody @Valid ProfileDto editedProfile) {
            ProfileDto updatedProfile = profileService.update(editedProfile);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProfile);
    }

    @DeleteMapping("/profiles/{id}")
    public ProfileDto delete(@PathVariable("id") UUID id) {
        return profileService.deleteById(id);
    }
}
