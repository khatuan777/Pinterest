package com.socialmedia.service;

import java.util.List;
import java.util.Optional;

import com.socialmedia.model.Friendships;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Users;

public interface FriendshipService {

    public List<Friendships> getAllByUser1AndStatus(Users user, Friendships.FriendshipStatus status);

    public List<Friendships> getAllByUser2AndStatus(Users user, Friendships.FriendshipStatus status);

    public Friendships getByUser1AndUser2AndStatus(Users user1, Users user2, Friendships.FriendshipStatus status);

    public List<Friendships> getAllByUser1(Users user);

    public List<Friendships> getAllByUser2(Users user);

    public Friendships getOneByUser1AndUser2(Users user1, Users user2);

    public Optional<Friendships> getById(int id);

    public Friendships save(Friendships friendship);

    public boolean delete(Friendships friendship);

    public Friendships getByNotifications(Notifications notifications);

    public List<Friendships> getAllByNotification(Notifications notification);

}
