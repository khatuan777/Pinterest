package com.socialmedia.service;

import java.util.List;
import com.socialmedia.model.Users;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    public Users saveUser(Users user);

    public List<Users> getAllUsers();

    public Users getUserByUsername(String username);
    
    public Users getUserByEmail(String email);
    
     public List<Users> getAllUserByEmail(String email);


    public Optional<Users> getUserById(int id);

    public Users getUserByPassword(String password);

    public boolean delete(int id);

    public long countAll();

    public long countByCreatedAtBefore(Date date);

    public long countByCreatedAt(Date date);

    public long countByCreatedAt(Date date1, Date date2);

    public void changeUserPassword(int id, String currentPassword, String newPassword);

    public void updateAvatar(Integer id, MultipartFile avatarfile);

    public void changeUserPrivateState(int id, boolean currentState);

    public void changeUserbirthday(int id, Date birthday);

}
