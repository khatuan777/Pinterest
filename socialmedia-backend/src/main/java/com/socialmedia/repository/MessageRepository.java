package com.socialmedia.repository;

import com.socialmedia.model.Messages;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messages,Integer> {
    public List<Messages> findByConversationId(int id);
}