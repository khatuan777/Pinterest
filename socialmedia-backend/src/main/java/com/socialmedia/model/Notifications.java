
package com.socialmedia.model;

import java.util.Date;

import com.socialmedia.model.enums.Notification_TYPE;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    public Users getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNotificationAt(Date notificationAt) {
        this.notificationAt = notificationAt;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    // Cài đặt những giá trị mặc định có thể xảy ra cho các loại thông báo
    @Enumerated(EnumType.STRING)
    private Notification_TYPE notificationType;

    public Notification_TYPE getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(Notification_TYPE notificationType) {
        this.notificationType = notificationType;
    }

    private Date notificationAt;

    public Date getNotificationAt() {
        return notificationAt;
    }

    public void setNotification_at(Date notification_at) {
        this.notificationAt = notification_at;
    }

    public int getId() {
        return id;
    }

}