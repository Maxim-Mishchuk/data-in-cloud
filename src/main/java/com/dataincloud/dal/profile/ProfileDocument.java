package com.dataincloud.dal.profile;

import com.dataincloud.core.profile.Profile;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document("profiles")
@Data
public class ProfileDocument {
    @Id
    private UUID id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String photoPath;
    private LocalDate birthDate;
    private List<Profile.ProfileTags> tagsList;
}
