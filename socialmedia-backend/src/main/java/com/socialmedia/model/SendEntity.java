package com.socialmedia.model;

import java.util.List;

public class SendEntity {
    private Notifications notifications;
    private List<Pins> listPins;
    private Friendships friendships;
    private Comments comments;
    private Likes likes;

    public Notifications getNotifications() {
        return notifications;
    }

    public void setNotifications(Notifications notifications) {
        this.notifications = notifications;
    }

    public List<Pins> getListPins() {
        return listPins;
    }

    public void setListPins(List<Pins> relatePins) {
        this.listPins = relatePins;
    }

    public Friendships getFriendships() {
        return friendships;
    }

    public void setFriendships(Friendships friendships) {
        this.friendships = friendships;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

}
