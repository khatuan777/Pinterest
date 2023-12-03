package com.socialmedia.service;

import java.util.List;

import com.socialmedia.model.Notifications;
import com.socialmedia.model.enums.Notification_TYPE;

public interface NotificationService {

    public Notifications initNotifications(Notification_TYPE type, int userId);

    public List<Notifications> getNotificationsByUser(int userId);

    public Notifications getById(int id);

    public boolean delete(Notifications noti);

}
