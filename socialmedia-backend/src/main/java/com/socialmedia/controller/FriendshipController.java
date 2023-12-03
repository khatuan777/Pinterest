package com.socialmedia.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.model.Friendships;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Users;
import com.socialmedia.service.FriendshipService;
import com.socialmedia.service.NotificationService;
import com.socialmedia.service.UserService;

@RestController
@RequestMapping("/friendships")
@CrossOrigin
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = { "/count/{id}" })
    public int countFriend(@PathVariable("id") int id) {
        Optional<Users> optional1 = userService.getUserById(id);
        Users user1 = optional1.get();
        Friendships.FriendshipStatus status1 = Friendships.FriendshipStatus.ACCEPTED;
        List<Friendships> list1 = friendshipService.getAllByUser1AndStatus(user1, status1);

        Optional<Users> optional2 = userService.getUserById(id);
        Users user2 = optional2.get();
        Friendships.FriendshipStatus status2 = Friendships.FriendshipStatus.ACCEPTED;
        List<Friendships> list2 = friendshipService.getAllByUser2AndStatus(user2, status2);

        return list1.size() + list2.size();
    }

    @GetMapping(value = { "/listFriend/{id}" })
    public List<Friendships> listFriend(@PathVariable("id") int id) {
        List<Friendships> list = new ArrayList<>();

        Optional<Users> optional1 = userService.getUserById(id);
        Users user1 = optional1.get();
        Friendships.FriendshipStatus status1 = Friendships.FriendshipStatus.ACCEPTED;
        List<Friendships> list1 = friendshipService.getAllByUser1AndStatus(user1, status1);
        list.addAll(list1);

        Optional<Users> optional2 = userService.getUserById(id);
        Users user2 = optional2.get();
        Friendships.FriendshipStatus status2 = Friendships.FriendshipStatus.ACCEPTED;
        List<Friendships> list2 = friendshipService.getAllByUser2AndStatus(user2, status2);
        list.addAll(list2);

        return list;
    }

    @GetMapping(value = { "/listRequest/{id}" })
    public List<Friendships> listRequest(@PathVariable("id") int id) {
        // List<Friendships> list = new ArrayList<>();

        // Optional<Users> optional1 = userService.getUserById(id);
        // Users user1 = optional1.get();
        // Friendships.FriendshipStatus status1 = Friendships.FriendshipStatus.PENDING;
        // List<Friendships> list = friendshipService.getAllByUser1AndStatus(user1,
        // status1);
        // list.addAll(list1);
        Optional<Users> optional2 = userService.getUserById(id);
        Users user2 = optional2.get();
        Friendships.FriendshipStatus status2 = Friendships.FriendshipStatus.PENDING;
        List<Friendships> list = friendshipService.getAllByUser2AndStatus(user2, status2);
        // list.addAll(list2);

        return list;
    }

    @GetMapping(value = { "/listSent/{id}" })
    public List<Friendships> listSent(@PathVariable("id") int id) {
        // List<Friendships> list = new ArrayList<>();

        Optional<Users> optional1 = userService.getUserById(id);
        Users user1 = optional1.get();
        Friendships.FriendshipStatus status1 = Friendships.FriendshipStatus.PENDING;
        List<Friendships> list = friendshipService.getAllByUser1AndStatus(user1, status1);
        // list.addAll(list1);

        // Optional<Users> optional2 = userService.getUserById(id);
        // Users user2 = optional2.get();
        // Friendships.FriendshipStatus status2 = Friendships.FriendshipStatus.PENDING;
        // List<Friendships> list2 = friendshipService.getAllByUser2AndStatus(user2,
        // status2);
        // list.addAll(list2);
        return list;
    }

    @GetMapping(value = "/getByNotification/{notificationId}")
    public Friendships getByNotification(@PathVariable int notificationId) {
        Notifications not = notificationService.getById(notificationId);
        return friendshipService.getByNotifications(not);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Friendships> update(@PathVariable("id") int id, @RequestBody Friendships friendship) {
        Optional<Friendships> optionalFriendship = friendshipService.getById(id);

        if (optionalFriendship.isPresent()) {
            Friendships currentFriendship = optionalFriendship.get();
            currentFriendship.setCreated_at(new Date());
            currentFriendship.setStatus(friendship.getStatus());
            return new ResponseEntity<>(friendshipService.save(currentFriendship), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // @PostMapping("/delete/{id}")
    // public boolean delete(@PathVariable("id") int id) {
    // Optional<Friendships> optional = friendshipService.getById(id);
    // if (optional.isPresent()) {
    // return friendshipService.delete(id);
    // } else {
    // return false;
    // }
    // }
    @PostMapping("/delete")
    public boolean delete(@RequestBody Friendships friendship) {
        if (friendship.getStatus().equals(Friendships.FriendshipStatus.PENDING)) {
            Friendships friendship2 = friendshipService.getByUser1AndUser2AndStatus(friendship.getUser2(),
                    friendship.getUser1(), Friendships.FriendshipStatus.ACCEPTED);
            if (friendship2 != null) {
                friendshipService.delete(friendship2);
                Notifications noti2 = friendship2.getNotification();
                notificationService.delete(noti2);
            }
        } else {
            Friendships friendship2 = friendshipService.getByUser1AndUser2AndStatus(friendship.getUser2(),
                    friendship.getUser1(), Friendships.FriendshipStatus.PENDING);
            if (friendship2 != null) {
                friendshipService.delete(friendship2);
                Notifications noti2 = friendship2.getNotification();
                notificationService.delete(noti2);
            }
        }

        Notifications noti1 = friendship.getNotification();
        friendshipService.delete(friendship);
        notificationService.delete(noti1);

        return true;
    }

    @PostMapping("/add")
    public ResponseEntity<Friendships> add(@RequestBody Friendships frienship) {
        // Đặt thời gian đăng ký
        frienship.setCreated_at(new Date());
        return new ResponseEntity<>(friendshipService.save(frienship), HttpStatus.OK);
    }

    @GetMapping("/checkFriend")
    public Friendships checkFriend(@RequestParam(name = "id1") Integer id1,
            @RequestParam(name = "id2") Integer id2) {

        Optional<Users> optional1 = userService.getUserById(id1);
        Users user1 = optional1.get();
        Optional<Users> optional2 = userService.getUserById(id2);
        Users user2 = optional2.get();
        // Friendships.FriendshipStatus status1 = Friendships.FriendshipStatus.PENDING;

        Friendships friendship1 = friendshipService.getOneByUser1AndUser2(user1, user2);
        System.out.println("friendship1==============" + friendship1);
        if (friendship1 != null) {
            switch (friendship1.getStatus()) {
                case ACCEPTED -> {
                    System.out.println("friendship1+ACCEPTED");
                    return friendship1;
                }
                case PENDING -> {
                    System.out.println("friendship1+PENDING");
                    return friendship1;
                }
            }
        } else {
            Friendships friendship2 = friendshipService.getOneByUser1AndUser2(user2, user1);
            if (friendship2 != null) {
                switch (friendship2.getStatus()) {
                    case ACCEPTED -> {
                        System.out.println("friendship2+ACCEPTED");
                        return friendship2;
                    }
                    case PENDING -> {
                        System.out.println("friendship2+PENDING");
                        return friendship2;
                    }
                }

            }
        }
        return null;
    }
}
