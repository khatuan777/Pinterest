package com.socialmedia.service;

import com.socialmedia.model.Content_Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.socialmedia.repository.Content_ReportRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

@Service
public class Content_ReportServiceImpl implements Content_ReportService {

    @Autowired
    private Content_ReportRepository repository;

    @Override
    public List<Content_Report> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Content_Report> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Content_Report save(Content_Report type) {
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
