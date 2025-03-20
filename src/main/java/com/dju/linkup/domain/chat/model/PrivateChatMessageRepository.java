package com.dju.linkup.domain.chat.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PrivateChatMessageRepository extends MongoRepository<PrivateChatMessage, String> {

    Optional<PrivateChatMessage> findTopByRoomIdOrderByTimeStampDesc(String roomId);

    List<PrivateChatMessage> findByRoomId(String roomId);
}
