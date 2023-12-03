package com.socialmedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.socialmedia.model.Conversations;
import com.socialmedia.repository.ConversationRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationRepository  repository;

    @Override
    public Conversations saveConversation(Conversations conversation) {
        return repository.save(conversation);
    }

    @Override
    public List<Conversations> getAllConversation() {
        return repository.findAll();
    }

    @Override
    public Optional<Conversations> getConversationById(int id) {
        return repository.findById(id);
    }


}