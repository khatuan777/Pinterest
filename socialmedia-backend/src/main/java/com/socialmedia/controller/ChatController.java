/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.controller;

import com.socialmedia.model.Messages;
import com.socialmedia.repository.ConversationRepository;
import com.socialmedia.repository.MessageRepository;
import com.socialmedia.repository.PinRepository;
import com.socialmedia.repository.UserRepository;
import com.socialmedia.config.SimpleMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
/**
 *
 * @author PC
 */
@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {
    
    private Map<String, Integer> roomMap = new HashMap<>();
    
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private PinRepository pinRepository;
    
    @MessageMapping("/chat/conversation_id/{conversation_id}")
    @SendTo("/room/conversation_id/{conversation_id}")
    public Messages chatting(@Payload SimpleMessage sMessage, @DestinationVariable("conversation_id") String conversation_id) throws Exception {
        Thread.sleep(1000); // simulated delay
        Messages message = new Messages();
        message.setContent(sMessage.getContent());
        message.setId(sMessage.getId());
        message.setSend_at(new Date());
        message.setUser(userRepository.findById(sMessage.getUser_id()).get());
        message.setConversation(conversationRepository.findById(sMessage.getConversation_id()).get());
        if(sMessage.getPin_id() > -1) {
            message.setPin(pinRepository.findById(sMessage.getPin_id()).get());
        }
        if(sMessage.getSharedUserId() > -1) {
            message.setSharedUser(userRepository.findById(sMessage.getSharedUserId()).get());
        }
        if(roomMap.get(conversation_id) < 2) {
            message.setSeen(false);
        }
        else {
            message.setSeen(true);
        }
        message = messageRepository.save(message);
        return message;
//        return message;
    }
    
    @MessageMapping("/login")
    public void initRoom(@Payload String conversation_id) throws InterruptedException {
//        Thread.sleep(1000); // simulated delay
        if(!conversation_id.equals("")) {
            int userCount = 1;
            if(roomMap.containsKey(conversation_id)) {
                userCount = 2;
            }
            roomMap.put(conversation_id, userCount);
        }
    }
    
    @MessageMapping("/unsubscribe")
    public void handleUnsubscribe(@Payload String conversation_id) throws InterruptedException {
        roomMap.put(conversation_id, 1);
    }
    
    @MessageMapping("/reload")
    @SendTo("/room/reload")
    public boolean handleReload() {
        return true;
    }
}
