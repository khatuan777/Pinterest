package com.socialmedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.socialmedia.model.Functions;
import com.socialmedia.model.Permissions;
import com.socialmedia.repository.FunctionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

@Service
public class FunctionServiceImpl implements FunctionService {

    @Autowired
    private FunctionRepository repository;

    @Override
    public List<Functions> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Functions> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Functions save(Functions type) {
        return repository.save(type);
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
    
    

}
