package com.dataincloud.core.profile;

import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
public class Profile {
    private UUID id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String photoPath;
    private LocalDate birthDate;
    private List<ProfileTags> tags = new LinkedList<>();

    public enum ProfileTags {
        EDUCATION,
        BLOG,
        SHOP
    }
}
