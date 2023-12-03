/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.controller;

import com.socialmedia.model.Comments;
import com.socialmedia.service.CommentService;
import com.socialmedia.service.PinService;
import com.socialmedia.service.UserService;
import com.socialmedia.config.SimpleComment;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author phmlhuyntrang
 */
@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class RealTimeCommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PinService pinService;
    @Autowired
    private UserService userService;
    @MessageMapping("/addComment/pin_id/{pin_id}")
    @SendTo("/room/comment/pin_id/{pin_id}")
    public Comments addComment(@DestinationVariable("pin_id") int pin_id, SimpleComment sComment) throws Exception{
        Thread.sleep(1000);
        Comments comment = new Comments();
        comment.setCommentAt(new Date());
        comment.setContent(sComment.getContent());
        comment.setId(sComment.getCommentId());
        comment.setUser(userService.getUserById(sComment.getUserId()).get());
        comment.setPin(pinService.getPinById(pin_id).get());
        return commentService.saveComment(comment);
//        return comment;
    }
}
