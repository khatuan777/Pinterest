package com.socialmedia.service;

import com.socialmedia.model.Messages;
import java.util.List;
import java.util.Optional;

public interface MessageService {

    public List<Messages> getAll();
    public List<Messages> getAllMessagesByConversationId(int id);
    public Optional<Messages> getMessageById(int id);
    public Messages saveMessage(Messages message);
    public void changeSeenStatus(int id, boolean status);
}