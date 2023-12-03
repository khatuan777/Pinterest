package com.socialmedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialmedia.model.DetailNotification;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Pins;

public interface DetailNotificationRepository extends JpaRepository<DetailNotification, Integer> {

    public List<DetailNotification> findAllByPin(Pins pin);

    public List<DetailNotification> findAllByNotification(Notifications notification);
}
