package com.dataincloud.dal.profilebyuser;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.UUID;

@Entity
@Table(name = "users_profiles")
@IdClass(ProfileByUserId.class)
@Getter @Setter
public class ProfileByUserJpa {
    @Id
    private Long userId;
    @Id
    private UUID profileId;
    @Column(insertable = false, updatable = false)
    private Calendar creationDate;
}
