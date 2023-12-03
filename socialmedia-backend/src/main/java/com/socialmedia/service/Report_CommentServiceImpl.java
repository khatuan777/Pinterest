package com.socialmedia.service;

import com.socialmedia.model.Report_Comments;
import com.socialmedia.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.socialmedia.repository.Report_CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class Report_CommentServiceImpl implements Report_CommentService {

    @Autowired
    private Report_CommentRepository repository;

    @Override
    public long countAll() {
        return repository.count();
    }
    
    @Override
    public Report_Comments saveReportComments(Report_Comments reportComment) {
        return repository.save(reportComment);
    }

    @Override
    public long countByUserRatify(Users userRatify) {
        return repository.countByUserRatify(userRatify);
    }

    @Override
    public long countByApprove(boolean approve) {
        return repository.countByApprove(approve);
    }

    @Override
    public void changeApprove(int id, Users userRatify, boolean approveState) {
        Optional<Report_Comments> optional = repository.findById(id);
        if (optional.isPresent()) {
            Report_Comments rc = optional.get();
            if (rc.getApproval() == approveState) {
                System.out.println("Duyệt thành công" + rc.getApproval());
                rc.setUserRatify(userRatify);
                rc.setApproval(approveState);
                rc.setReject(!approveState);
                repository.save(rc);
            } else {
                System.out.println("Duyệt không thành công" + rc.getApproval());
                rc.setUserRatify(userRatify);
                rc.setApproval(approveState);
                rc.setReject(!approveState);
                repository.save(rc);
            }
        } else {
            throw new EntityNotFoundException(" not found to set private");
        }
    }

}