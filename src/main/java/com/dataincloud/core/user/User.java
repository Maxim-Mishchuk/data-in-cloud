package com.dataincloud.core.user;

import com.dataincloud.core.post.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Calendar;
import java.util.List;

@Data
public class User {
    private Long id;
    private String username;
    private Calendar birthDate;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Post> posts;
}
