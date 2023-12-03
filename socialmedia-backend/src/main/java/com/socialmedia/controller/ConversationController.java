package com.socialmedia.controller;

import com.socialmedia.model.Conversations;
import com.socialmedia.model.Pins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.service.ConversationService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/conversations")
@CrossOrigin(origins = "http://localhost:3000")
public class ConversationController {
    @Autowired
    private ConversationService service;

    @GetMapping("/getAll")
    public List<Conversations> list(){
        return service.getAllConversation();
    }
    
    @GetMapping(value={"/id/{id}"})
    public Conversations getConversationById(@PathVariable("id") int id) {
        Optional<Conversations> optional = service.getConversationById(id);
        return optional.get();
    }
    
    @PostMapping("/add")
    public Conversations save(@RequestBody Conversations conv) {
        return service.saveConversation(conv);
    }
}