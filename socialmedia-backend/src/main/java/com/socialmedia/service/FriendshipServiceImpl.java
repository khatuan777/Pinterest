package com.socialmedia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.socialmedia.model.Friendships;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Users;
import com.socialmedia.repository.FriendshipRepository;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipRepository repository;

    @Override
    public List<Friendships> getAllByUser1AndStatus(Users user, Friendships.FriendshipStatus status) {
        return repository.findAllByUser1AndStatus(user, status);

    }

    @Override
    public List<Friendships> getAllByUser2AndStatus(Users user, Friendships.FriendshipStatus status) {
        return repository.findAllByUser2AndStatus(user, status);
    }

    @Override
    public Friendships getByUser1AndUser2AndStatus(Users user1, Users user2, Friendships.FriendshipStatus status) {
        return repository.findByUser1AndUser2AndStatus(user1, user2, status);
    }

    @Override
    public List<Friendships> getAllByUser1(Users user) {
        return repository.findAllByUser1(user);
    }

    @Override
    public List<Friendships> getAllByUser2(Users user) {
        return repository.findAllByUser2(user);
    }

    @Override
    public Optional<Friendships> getById(int id) {
        return repository.findById(id);
    }

    @Override
    public Friendships save(Friendships friendship) {
        return repository.save(friendship);
    }

    @Override
    public boolean delete(Friendships friendship) {
        try {
            repository.delete(friendship);
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
    public Friendships getOneByUser1AndUser2(Users user1, Users user2) {
        return repository.findOneByUser1AndUser2(user1, user2);
    }

    @Override
    public Friendships getByNotifications(Notifications notifications) {
        return repository.findByNotification(notifications);
    }

    @Override
    public List<Friendships> getAllByNotification(Notifications notifications) {
        return repository.findAllByNotification(notifications);
    }

}
