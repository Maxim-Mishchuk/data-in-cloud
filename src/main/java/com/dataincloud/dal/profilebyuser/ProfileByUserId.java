package com.dataincloud.dal.profilebyuser;

import lombok.*;

import java.util.UUID;

@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProfileByUserId {
    private Long userId;
    private UUID profileId;
}
