package com.dju.linkup.domain.chat;

import com.dju.linkup.domain.chat.model.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivateChatRepository extends JpaRepository<PrivateChat, String> {

    List<PrivateChat> findByUser1IdOrUser2Id(String user1Id, String user2Id);
}
