package com.socialmedia.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.model.Notifications;
import com.socialmedia.model.Pins;
import com.socialmedia.service.CommentService;
import com.socialmedia.service.DetailNotificationService;
import com.socialmedia.service.FriendshipService;
import com.socialmedia.service.NotificationService;

@RestController
@RequestMapping("/news_hub")
@CrossOrigin
public class DetailNotificationController {
    @Autowired
    private DetailNotificationService service;

    @Autowired
    private NotificationService notService;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private CommentService commentService;

    // Lấy các bài pin trong trang news_hub
    @GetMapping("/{id}")
    public Set<Pins> getDetailNotification(@PathVariable("id") int id) {
        Notifications not = notService.getById(id);
        Set<Pins> result = service.getAllByNotification(not);
        return result;
    }

}
