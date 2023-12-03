package com.socialmedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialmedia.model.Friendships;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Users;

public interface FriendshipRepository extends JpaRepository<Friendships, Integer> {

  public List<Friendships> findAllByUser1AndStatus(Users user, Friendships.FriendshipStatus status);

  public List<Friendships> findAllByUser2AndStatus(Users user, Friendships.FriendshipStatus status);

  public Friendships findByUser1AndUser2AndStatus(Users user1, Users user2, Friendships.FriendshipStatus status);

  public List<Friendships> findAllByNotification(Notifications notification);

  public List<Friendships> findAllByUser1(Users user);

  public List<Friendships> findAllByUser2(Users user);

  public Friendships findOneByUser1AndUser2(Users user1, Users user2);

  public Friendships findByNotification(Notifications notification);
}
