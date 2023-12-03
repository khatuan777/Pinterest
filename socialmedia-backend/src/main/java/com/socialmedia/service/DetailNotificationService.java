package com.socialmedia.service;

import java.util.List;
import java.util.Set;

import com.socialmedia.model.DetailNotification;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Pins;

public interface DetailNotificationService {

    public List<DetailNotification> findAllByPin(Pins pin);
    
    public List<DetailNotification> findAllByNotification(Notifications notification);

    public void initDetailNotifications(Notifications notifications, List<Pins> pins, int userId);

    public Set<Pins> getAllByNotification(Notifications notifications);

    public boolean delete(int id);

}
