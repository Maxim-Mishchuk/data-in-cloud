package com.dataincloud.core.post;

import com.dataincloud.core.user.User;
import lombok.Data;

import java.util.Calendar;

@Data
public class Post {
    private Long id;
    private String header;
    private String description;
    private Calendar createdDate;
    private User user;
}
