package com.dataincloud.services.profile;

import com.dataincloud.core.profile.IProfileRepository;
import com.dataincloud.core.profile.Profile;
import com.dataincloud.services.profile.dto.ProfileCreateDto;
import com.dataincloud.services.profile.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProfileService {
    private final IProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    public ProfileDto create(ProfileCreateDto newProfile) {
        return modelMapper.map(
                profileRepository.create(modelMapper.map(newProfile, Profile.class)),
                ProfileDto.class
        );
    }

    public ProfileDto readById(UUID id) {
        return modelMapper.map(
                profileRepository.readById(id),
                ProfileDto.class
        );
    }

    public List<ProfileDto> readAll() {
        return profileRepository.readAll().stream()
                .map(profile -> modelMapper.map(profile, ProfileDto.class))
                .toList();
    }

    public ProfileDto update(ProfileCreateDto editedProfile) {
        return modelMapper.map(
                profileRepository.update(modelMapper.map(editedProfile, Profile.class)),
                ProfileDto.class
        );
    }

    public ProfileDto deleteById(UUID id) {
        return modelMapper.map(
                profileRepository.delete(id),
                ProfileDto.class
        );
    }
}
