package com.dataincloud.dal.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<PostJpa, Long> {
    @Query("SELECT p FROM PostJpa p JOIN FETCH p.user")
    List<PostJpa> findAllFetch();
}
