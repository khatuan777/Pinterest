package com.socialmedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialmedia.model.Boards;
import com.socialmedia.model.Pins;
import com.socialmedia.model.UserSavePin;
import com.socialmedia.model.Users;

public interface UserSavePinRepository extends JpaRepository<UserSavePin, Integer> {

    public List<UserSavePin> findAllByUserAndBoard(Users user, Boards board);

    public List<UserSavePin> findAllByUser(Users user);

    public List<UserSavePin> findAllByPin(Pins pin);

    public List<UserSavePin> findAllByBoard(Boards board);

    public UserSavePin findByPinAndUser(Pins pin, Users user);

    public UserSavePin findByPinAndUserAndBoard(Pins pin, Users user, Boards board);

}
