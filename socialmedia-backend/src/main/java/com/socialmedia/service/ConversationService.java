package com.socialmedia.service;

import com.socialmedia.model.Conversations;
import java.util.List;
import java.util.Optional;

public interface ConversationService {
    public Conversations saveConversation(Conversations conversation);
    public List<Conversations> getAllConversation();
    public Optional<Conversations> getConversationById(int id);
}
