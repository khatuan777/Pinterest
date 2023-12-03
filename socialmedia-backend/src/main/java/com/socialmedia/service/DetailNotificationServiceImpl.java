package com.socialmedia.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.socialmedia.model.DetailNotification;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Pins;
import com.socialmedia.repository.DetailNotificationRepository;
import com.socialmedia.repository.NotificationRepository;
import com.socialmedia.repository.PinRepository;

@Service
public class DetailNotificationServiceImpl implements DetailNotificationService {

    @Autowired
    private DetailNotificationRepository repository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PinRepository pinRepository;

    @Autowired
    private SimpMessagingTemplate message;

    @Override
    public List<DetailNotification> findAllByPin(Pins pin) {
        return repository.findAllByPin(pin);
    }
    
     @Override
    public List<DetailNotification> findAllByNotification(Notifications notification) {
        return repository.findAllByNotification(notification);
    }

    public Set<Pins> getRelatedPins(List<Pins> pins) {
        Set<Pins> result = new HashSet<>();
        pins.forEach(e -> {
            // if (e.getType() != null) {
            result.addAll(pinRepository.findByType(e.getType()));
            // }
        });
        // Bỏ những bài đăng đã lưu
        result.removeAll(pins);

        return result;
    }

    public void initDetailNotifications(Notifications notifications, List<Pins> pins, int userId) {
        Set<Pins> result = getRelatedPins(pins);
        if (!result.isEmpty()) {
            result.forEach(e -> {
                DetailNotification res = new DetailNotification();
                res.setPin(e);
                res.setNotification(notifications);
                repository.save(res);
            });
        } else {
            System.out.println("No related pins found");
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            System.out.println("Không tìm thấy thực thể để xóa");
            return false;
        } catch (DataIntegrityViolationException ex) {
            System.out.println("Lỗi liên quan đến tính toàn vẹn dữ liệu hoặc ràng buộc khóa ngoại");
            return false;
        }
    }

    @Override
    public Set<Pins> getAllByNotification(Notifications notifications) {
        Set<Pins> result = new HashSet<>();
        repository.findAllByNotification(notifications).forEach(e -> {
            result.add(e.getPin());
        });
        return result;
    }
}
