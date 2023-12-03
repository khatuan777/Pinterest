package com.socialmedia.service;

import java.util.List;

import com.socialmedia.model.Boards;
import com.socialmedia.model.Pins;
import com.socialmedia.model.UserSavePin;
import com.socialmedia.model.Users;

public interface UserSavePinService {

    public UserSavePin saveUserSavePin(UserSavePin userSavePin);

    public List<UserSavePin> getAllUserSavePin();

    public List<UserSavePin> findAllByUserAndBoard(Users user, Boards board);

    public List<UserSavePin> findAllByPin(Pins pin);

    public List<UserSavePin> findAllByBoard(Boards board);

    public List<UserSavePin> findAllByUser(Users user);

    public boolean delete(UserSavePin userSavePin);
    
    public UserSavePin findByPinAndUser(Pins pin, Users user);
    
    public UserSavePin findByPinAndUserAndBoard(Pins pin, Users user, Boards board);

}
