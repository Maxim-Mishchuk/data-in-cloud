package com.dataincloud.dal.user;

import com.dataincloud.dal.post.PostJpa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter @Setter
public class UserJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Calendar birthDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PostJpa> posts;
}
