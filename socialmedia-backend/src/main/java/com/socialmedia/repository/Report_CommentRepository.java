package com.socialmedia.repository;

import com.socialmedia.model.Report_Comments;
import com.socialmedia.model.Users;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Report_CommentRepository extends JpaRepository<Report_Comments, Integer> {
    
    public long countByUserRatify(Users userRatify);

    public long countByApprove(boolean approve);

    @Query("SELECT rc.id, c.content AS commentContent, cr.content AS reportContent, u.username, rc.approve, rc.reject "
            + "FROM Report_Comments rc "
            + "JOIN rc.comment c "
            + "JOIN rc.content cr "
            + "OUTER JOIN rc.userRatify u")
    List<Object[]> findAllWithDetails();

    @Query("SELECT rc.id, c.content AS commentContent, cr.content AS reportContent, u.username, rc.approve, rc.reject "
            + "FROM Report_Comments rc "
            + "JOIN rc.comment c "
            + "JOIN rc.content cr "
            + "OUTER JOIN rc.userRatify u "
            + "WHERE rc.id = :id")
    Object[] findDetailsById(@Param("id") int id);

  
}