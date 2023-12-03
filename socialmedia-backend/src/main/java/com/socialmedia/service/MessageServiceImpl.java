package com.socialmedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.socialmedia.model.Messages;
import com.socialmedia.repository.MessageRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository  repository;

    @Override
    public List<Messages> getAll() {
//        java.sql.Types.
        return repository.findAll();
    }

    @Override
    public List<Messages> getAllMessagesByConversationId(int id) {
        return repository.findByConversationId(id);
    }

    @Override
    public Messages saveMessage(Messages message) {
        return repository.save(message);
    }

    @Override
    public Optional<Messages> getMessageById(int id) {
        return repository.findById(id);
    }

    @Override
    public void changeSeenStatus(int id, boolean status) {
        Optional<Messages> optional = repository.findById(id);
        if(optional.isPresent()) {
            Messages message = optional.get();
            message.setSeen(status);
            repository.save(message);
        }
    }
}