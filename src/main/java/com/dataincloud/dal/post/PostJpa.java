package com.dataincloud.dal.post;

import com.dataincloud.dal.user.UserJpa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Entity
@Table(name = "posts")
@Getter @Setter
public class PostJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String description;
    private Calendar createdDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserJpa user;
}
