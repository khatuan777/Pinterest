package com.socialmedia.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.socialmedia.model.Comments;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Pins;
import com.socialmedia.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repository;

    @Override
    public List<Comments> getAll() {
        return repository.findAll();
    }

    @Override
    public Comments saveComment(Comments comment) {
        return repository.save(comment);
    }

    @Override
    public long countAll() {
        return repository.count();
    }

    @Override
    public long countByCreatedAtBefore(Date date) {
        return repository.countByCommentAtBefore(date);
    }

    @Override
    public long countByCreatedAt(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);
        return repository.countByCreatedAt(formattedDate);
    }

    @Override
    public long countByCreatedAt(Date date1, Date date2) {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate1 = dateFormat1.format(date1);

        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate2 = dateFormat2.format(date2);
        return repository.countByCreatedAt(formattedDate1, formattedDate2);
    }

    @Override
    public List<Comments> findAllByPin(Pins pin) {
        return repository.findAllByPin(pin);
    }

    @Override
    public Comments getByNotification(Notifications notification) {
        return repository.findByNotification(notification);
    }

    @Override
    public boolean delete(Comments comment) {
        try {
            repository.delete(comment);
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
    public List<Comments> getAllByNotification(Notifications notifications) {
        return repository.findAllByNotification(notifications);
    }
}
