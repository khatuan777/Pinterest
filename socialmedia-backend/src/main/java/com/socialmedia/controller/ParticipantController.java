package com.socialmedia.controller;

import com.socialmedia.model.Messages;
import com.socialmedia.model.Participants;
import com.socialmedia.model.Users;
import com.socialmedia.service.ParticipantService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participants")
@CrossOrigin(origins = "http://localhost:3000")
public class ParticipantController {
    @Autowired
    private ParticipantService service;

    @GetMapping(value="/conversation_id/{conversation_id}")
    public List<Participants> getPaticipantsByConversationId(@PathVariable("conversation_id") int conversation_id) {
        return service.getParticipantsByConversationId(conversation_id);
    }
    
    @GetMapping(value="/getConversationJoined/{user_id}")
    public List<Participants> getConversationJoinedByUserId(@PathVariable("user_id") int user_id) {
        return service.getConversationJoinedByUserId(user_id);
    }
    
    @GetMapping(value="/getChattingWith/{user_id}")
    public List<Participants> getFriendChattingWith(@PathVariable("user_id") int user_id) {
        return service.getFriendChattingWith(user_id);
    }
    
    @PostMapping("/add")
    public ResponseEntity<Participants> save(@RequestBody Participants participant) {
        return new ResponseEntity<>(service.save(participant), HttpStatus.OK);
    }
}