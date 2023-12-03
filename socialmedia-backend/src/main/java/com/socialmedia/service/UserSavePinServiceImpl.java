package com.socialmedia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.socialmedia.model.Boards;
import com.socialmedia.model.Pins;
import com.socialmedia.model.UserSavePin;
import com.socialmedia.model.Users;
import com.socialmedia.repository.UserSavePinRepository;

@Service
public class UserSavePinServiceImpl implements UserSavePinService {

    @Autowired
    private UserSavePinRepository repository;

    @Override
    public UserSavePin saveUserSavePin(UserSavePin userSavePin) {
        return repository.save(userSavePin);
    }

    @Override
    public List<UserSavePin> getAllUserSavePin() {
        return repository.findAll();
    }

    @Override
    public List<UserSavePin> findAllByUserAndBoard(Users user, Boards board) {
        return repository.findAllByUserAndBoard(user, board);
    }

    @Override
    public List<UserSavePin> findAllByPin(Pins pin) {
        return repository.findAllByPin(pin);
    }

    @Override
    public List<UserSavePin> findAllByBoard(Boards board) {
        return repository.findAllByBoard(board);
    }

    @Override
    public List<UserSavePin> findAllByUser(Users user) {
        return repository.findAllByUser(user);
    }

    @Override
    public boolean delete(UserSavePin userSavePin) {
        try {
            repository.delete(userSavePin);
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
    public UserSavePin findByPinAndUser(Pins pin, Users user) {
        return repository.findByPinAndUser(pin,user);
    }

       @Override
    public UserSavePin findByPinAndUserAndBoard(Pins pin, Users user, Boards board) {
        return repository.findByPinAndUserAndBoard(pin,user, board);
    }
}
