package com.dataincloud.api.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan("com.dataincloud.dal.profile")
@EnableMongoRepositories("com.dataincloud.dal.profile")
public class RepositoryMongoConfiguration {
}
