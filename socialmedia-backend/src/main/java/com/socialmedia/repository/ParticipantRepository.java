package com.socialmedia.repository;

import com.socialmedia.model.Participants;
import com.socialmedia.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participants,Integer> {

    public List<Participants> findByConversationId(int id);
    public List<Participants> findByUser(Users user);
}