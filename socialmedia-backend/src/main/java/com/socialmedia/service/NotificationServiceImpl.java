package com.socialmedia.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialmedia.model.Notifications;
import com.socialmedia.model.Users;
import com.socialmedia.model.enums.Notification_TYPE;
import com.socialmedia.repository.NotificationRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Notifications> getNotificationsByUser(int userId) {
        // TODO Auto-generated method stub
        Optional<Users> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            return repository.findByUser(user);
        } else {
            System.out.println("Cannot find user's notification");
            return null;
        }
    }

    @Override
    public Notifications initNotifications(Notification_TYPE type, int userId) {
        Notifications notifications = new Notifications();
        notifications.setNotificationType(type);
        notifications.setNotification_at(new Date());
        List<Notifications> list = repository.findAll();
        notifications.setId((int) list.size() + 1);
        Users user = userRepository.findById(userId).get();
        notifications.setUser(user);
        return notifications;
    }

    @Override
    public Notifications getById(int id) {
        Optional<Notifications> optionalNot = repository.findById(id);
        if (optionalNot.isPresent()) {
            Notifications not = optionalNot.get();
            return not;
        }
        return null;
    }

    
     @Override
    public boolean delete(Notifications noti) {
        try {
            repository.delete(noti);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            System.out.println("Không tìm thấy thực thể để xóa");
            return false;
        } catch (DataIntegrityViolationException ex) {
            System.out.println("Lỗi liên quan đến tính toàn vẹn dữ liệu hoặc ràng buộc khóa ngoại");
            return false;
        }
    }
}