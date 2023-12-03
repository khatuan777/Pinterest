package com.socialmedia.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.socialmedia.model.Messages;
import com.socialmedia.service.ConversationService;
import com.socialmedia.service.MessageService;
import com.socialmedia.service.UserService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ConversationService conversationService;
    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<Messages> getAll() {
        return messageService.getAll();
    }

    @GetMapping(value = "/conversation_id/{conversation_id}")
    public List<Messages> getMessagesByConversationId(@PathVariable("conversation_id") int id) {
        List<Messages> tempList = messageService.getAllMessagesByConversationId(id);
        if(tempList.isEmpty()) {
            return null;
        }
        else {
            return messageService.getAllMessagesByConversationId(id);   
        }
    }    
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<Messages> update(@PathVariable("id") int id, @RequestBody Messages message) {
        Optional<Messages> optional = messageService.getMessageById(id);

        if (optional.isPresent()) {
            Messages currentMessage = new Messages();
            currentMessage.setContent(message.getContent());
            currentMessage.setId(message.getId());
            currentMessage.setSend_at(new Date());
            currentMessage.setUser(userService.getUserById(message.getUser().getId()).get());
            currentMessage.setConversation(conversationService.getConversationById(message.getConversation().getId()).get());
            currentMessage.setSeen(true);
            currentMessage.setPin(message.getPin());
            return new ResponseEntity<>(messageService.saveMessage(currentMessage), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<Messages> save(@RequestBody Messages message) {
        return new ResponseEntity<>(messageService.saveMessage(message), HttpStatus.OK);
    }
}