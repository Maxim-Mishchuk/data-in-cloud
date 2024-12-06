package com.dataincloud.api.configuration.converters;

import com.dataincloud.core.profile.Profile;
import org.springframework.core.convert.converter.Converter;

public class StringToProfileTagConverter implements Converter<String, Profile.ProfileTags> {
    @Override
    public Profile.ProfileTags convert(String source) {
        return Profile.ProfileTags.valueOf(source.toUpperCase());
    }
}
