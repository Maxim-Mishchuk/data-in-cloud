package com.dataincloud.dal.profile;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ProfileMongoDbRepository extends MongoRepository<ProfileDocument, UUID> {
}
