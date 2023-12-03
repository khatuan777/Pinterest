package com.socialmedia.repository;

import com.socialmedia.model.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversations,Integer> {


}