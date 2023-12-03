package com.socialmedia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.socialmedia.model.Users;
import com.socialmedia.repository.UserRepository;
import java.util.Date;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import jakarta.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public Users saveUser(Users user) {
        return repository.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public Users getUserByUsername(String username) {
        return repository.findOneByUsername(username);
    }
    
    
    @Override
    public Users getUserByEmail(String email) {
        return repository.findOneByEmail(email);
    }

     @Override
    public List<Users> getAllUserByEmail(String email) {
      return repository.findByEmail(email);
    }

    @Override
    public Optional<Users> getUserById(int id) {
        return repository.findById(id);

    }

    @Override
    public Users getUserByPassword(String password) {
        return repository.findOneByPassword(password);
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
    public long countAll() {
        return repository.count();
    }

    @Override
    public long countByCreatedAtBefore(Date date) {
        return repository.countByCreatedAtBefore(date);
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
    public void updateAvatar(Integer id, MultipartFile avatarfile) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void changeUserPassword(int id, String currentPassword, String newPassword) {
        Optional<Users> optional = repository.findById(id);
        if (optional.isPresent()) {
            Users user = optional.get();
            System.out.println("user pass +" + user.getPassword());
            System.out.println("current pass : " + currentPassword);
            System.out.println("new pass: " + newPassword);
            // thưc hien kiem tra mat khau cu
            if (user.getPassword().equals(currentPassword)) {
                user.setPassword(newPassword);
                repository.save(user);
            } else {
                System.out.println("current password is not correct");
            }

        } else {
            throw new EntityNotFoundException("user not found");
        }
    }

    @Override
    public void changeUserPrivateState(int id, boolean currentState) {
        Optional<Users> optional = repository.findById(id);
        if (optional.isPresent()) {
            Users user = optional.get();
            if (user.isPrivateBool()) {
                System.out.println("User is private, change to UnPrivate" + user.isPrivateBool());
                user.setPrivateBool(currentState);
                repository.save(user);

            }
            if (!(user.isPrivateBool())) {
                System.out.println("User is not private, change to Private" + user.isPrivateBool());
                user.setPrivateBool(currentState);
                repository.save(user);
            }
        } else {
            throw new EntityNotFoundException("User not found to set private");
        }
    }

    @Override
    public void changeUserbirthday(int id, Date birthday) {
        Optional<Users> optional = repository.findById(id);
        if (optional.isPresent()) {
            try {
                Users user = optional.get();
                user.setBirthdate(birthday);
                repository.save(user);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else {
            throw new EntityNotFoundException("user not found to set birthday");
        }
    }

   

}
