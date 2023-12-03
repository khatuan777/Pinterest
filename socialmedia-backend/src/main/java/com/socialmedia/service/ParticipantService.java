package com.socialmedia.service;

import com.socialmedia.model.Participants;
import java.util.List;

public interface ParticipantService {
    public List<Participants> getAll();
    public List<Participants> getParticipantsByConversationId(int id);
    public List<Participants> getFriendChattingWith(int user_id);
    public List<Participants> getConversationJoinedByUserId(int user_id);
    public Participants save(Participants participant);
}
