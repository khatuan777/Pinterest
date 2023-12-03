package com.socialmedia.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.model.Comments;
import com.socialmedia.model.DetailNotification;
import com.socialmedia.model.Friendships;
import com.socialmedia.model.Likes;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Pins;
import com.socialmedia.model.SendEntity;
import com.socialmedia.repository.NotificationRepository;
import com.socialmedia.service.CommentService;
import com.socialmedia.service.DetailNotificationService;
import com.socialmedia.service.FriendshipService;
import com.socialmedia.service.LikeService;
import com.socialmedia.service.NotificationService;
import com.socialmedia.service.PinService;
import com.socialmedia.service.UserService;

@RestController
@RequestMapping("/notifications")
@CrossOrigin
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private PinService pinService;

    private NotificationService service;

    private NotificationRepository repository;

    private UserService userService;

    private FriendshipService friendshipService;

    private DetailNotificationService detailService;

    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    public NotificationController(PinService pinService, NotificationService service, NotificationRepository repository,
            UserService userService, FriendshipService friendshipService, DetailNotificationService detailService,
            CommentService commentService) {
        this.pinService = pinService;
        this.service = service;
        this.repository = repository;
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.detailService = detailService;
        this.commentService = commentService;
    }

    @GetMapping("/user/{userId}")
    public List<Notifications> getNotifications(@PathVariable("userId") int userId) {
        return service.getNotificationsByUser(userId);
    }

    @MessageMapping("sendNot/{userId}")
    @SendTo("/room/updateNots/{userId}")
    public SendEntity initNotifications(@Payload SendEntity variable, @DestinationVariable int userId) {
        Notifications notifications = service.initNotifications(variable.getNotifications().getNotificationType(),
                userId);
        System.out.println(notifications);
        repository.save(notifications);
        switch (variable.getNotifications().getNotificationType()) {
            case Pin:
                // Trả về thông báo vừa được tạo
                if (notifications != null) {
                    if (variable.getListPins() != null) {
                        List<Pins> list = new ArrayList<>();
                        variable.getListPins().forEach(e -> {
                            list.add(pinService.getPinById(e.getId()).get());
                        });
                        detailService.initDetailNotifications(notifications, list, userId);
                    }
                }
                break;
            case Friend:
                if (variable.getFriendships().getStatus() != null) {
                    Friendships friendships = variable.getFriendships();
                    friendships.setCreated_at(new Date());
                    friendships.setNotification(notifications);
                    friendships.setUser1(userService.getUserById(variable.getFriendships().getUser1().getId()).get());
                    friendships.setUser2(userService.getUserById(variable.getFriendships().getUser2().getId()).get());
                    friendships.setStatus(variable.getFriendships().getStatus());
                    friendshipService.save(friendships);
                }
                break;
            case Comment:
                Comments comment = variable.getComments();
                comment.setCommentAt(new Date());
                comment.setPin(pinService.getPinById(comment.getPin().getId()).get());
                comment.setUser(userService.getUserById(comment.getUser().getId()).get());
                comment.setNotification(notifications);
                commentService.saveComment(comment);

                // Gửi lại comment
                int destination = comment.getPin().getId();
                messagingTemplate.convertAndSend("/room/comment/pin_id/" + destination, comment);
                System.out.println("Send back Comment");

                break;
            case Like:
                Likes likes = variable.getLikes();
                likes.setCreatedAt(new Date());
                likes.setPin(pinService.getPinById(likes.getPin().getId()).get());
                likes.setUser(userService.getUserById(likes.getUser().getId()).get());
                likes.setNotification(notifications);
                likeService.saveLike(likes);
                break;
            default:
                System.out.println("Cannot initial notifications !!!");
                break;
        }
        System.out.println("Init " + notifications.getNotificationType() + " notification success !!!");
        variable.setNotifications(notifications);
        return variable;
    }

    @PostMapping("/deleted/{id}")
    public void delete(@PathVariable("id") int id) {
        boolean delete = true;
        Notifications notification = service.getById(id);
        if (notification != null) {
            // Xóa detail noti
            List<DetailNotification> listDetailNoti = detailService.findAllByNotification(notification);
            for (DetailNotification item : listDetailNoti) {
                boolean deleteDetailNoti = detailService.delete(item.getId());
                if (deleteDetailNoti == false) {
                    delete = false;
                }
            }
            // set notification của friendship null
            List<Friendships> listFriendship = friendshipService.getAllByNotification(notification);
            for (Friendships item : listFriendship) {
                item.setNotification(null);
            }

            // set notification của like null
            List<Likes> listLike = likeService.getAllByNotification(notification);
            for (Likes item : listLike) {
                item.setNotification(null);
            }

            // set notification của comment null
            List<Comments> listcomment = commentService.getAllByNotification(notification);
            for (Comments item : listcomment) {
                item.setNotification(null);
            }

        }
        if (delete) {
            repository.deleteById(id);
        }
    }
}
