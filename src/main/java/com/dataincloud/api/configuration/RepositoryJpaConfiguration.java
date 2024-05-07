package com.dataincloud.api.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({"com.dataincloud.dal.user", "com.dataincloud.dal.post"})
@EntityScan("com.dataincloud.dal")
@EnableJpaRepositories("com.dataincloud.dal")
public class RepositoryJpaConfiguration {
}
