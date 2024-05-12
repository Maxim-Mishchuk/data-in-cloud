package com.dataincloud.api.platform.messagebroker.profilebyuser;

import com.dataincloud.dal.profilebyuser.ProfileByUserId;
import com.dataincloud.dal.profilebyuser.ProfileByUserJpa;
import com.dataincloud.dal.profilebyuser.ProfileByUserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class ProfileByUserRabbitListener {
    private final ProfileByUserJpaRepository profileByUserJpaRepository;

    @RabbitListener(queues = "profile-by-user-adding-queue")
    @Transactional
    public void processAddingMessage(String message) {
        log.info("Received the message from RabbitMQ profile-by-user-queue for adding: {}", message);
        String[] dividedMessage = message.split("_");

        ProfileByUserJpa profileByUser = new ProfileByUserJpa();
        profileByUser.setUserId(Long.parseLong(dividedMessage[0]));
        profileByUser.setProfileId(UUID.fromString(dividedMessage[1]));

        profileByUserJpaRepository.save(profileByUser);
    }

    @RabbitListener(queues = "profile-by-user-deleting-queue")
    @Transactional
    public void processDeletingMessage(String message) {
        log.info("Received the message from RabbitMQ profile-by-user-queue for deleting: {}", message);
        String[] dividedMessage = message.split("_");

        ProfileByUserId profileByUserId = new ProfileByUserId();
        profileByUserId.setUserId(Long.parseLong(dividedMessage[0]));
        profileByUserId.setProfileId(UUID.fromString(dividedMessage[1]));

        profileByUserJpaRepository.deleteById(profileByUserId);
    }
}
