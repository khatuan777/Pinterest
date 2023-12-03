package com.socialmedia.service;

import java.util.Date;
import java.util.List;

import com.socialmedia.model.Comments;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Pins;

public interface CommentService {

    public List<Comments> getAll();

    public Comments saveComment(Comments comment);

    public boolean delete(Comments comment);

    public List<Comments> findAllByPin(Pins pin);

    public Comments getByNotification(Notifications notification);

    public long countAll();

    public long countByCreatedAtBefore(Date date);

    public long countByCreatedAt(Date date);

    public long countByCreatedAt(Date date1, Date date2);

    public List<Comments> getAllByNotification(Notifications notification);
}
