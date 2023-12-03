package com.socialmedia.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.socialmedia.model.Likes;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Pins;

public interface LikeRepository extends JpaRepository<Likes, Integer> {

    public List<Likes> findAllByPin(Pins pin);

    public long countByCreatedAtBefore(Date created_at);

    public Likes findByNotification(Notifications notification);

    @Query("SELECT COUNT(e) FROM Likes e WHERE DATE_FORMAT(e.createdAt, '%Y-%m-%d') = :formattedDate")
    long countByCreatedAt(@Param("formattedDate") String formattedDate);

    @Query("SELECT COUNT(e) FROM Likes e WHERE DATE_FORMAT(e.createdAt, '%Y-%m-%d') >= :date1 AND DATE_FORMAT(e.createdAt, '%Y-%m-%d') <= :date2")
    long countByCreatedAt(@Param("date1") String date1, @Param("date2") String date2);

    public List<Likes> findAllByNotification(Notifications notification);
}
