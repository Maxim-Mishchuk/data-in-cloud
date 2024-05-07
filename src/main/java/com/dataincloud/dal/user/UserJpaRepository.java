package com.dataincloud.dal.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpa, Long> {
    @Query("SELECT u FROM UserJpa u LEFT JOIN FETCH u.posts WHERE u.id = :id")
    Optional<UserJpa> findByIdFetch(@Param("id") Long id);
}
