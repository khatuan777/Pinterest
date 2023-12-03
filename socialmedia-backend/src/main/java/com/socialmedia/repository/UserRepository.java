package com.socialmedia.repository;

import com.socialmedia.model.Users;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Integer> {

    public Users findOneByUsername(String Username);
    
    public List<Users> findByEmail(String email);

    public Users findOneByEmail(String Email);

    public Users findOneByPassword(String password);

    public long countByCreatedAtBefore(Date created_at);

    @Query("SELECT COUNT(e) FROM Users e WHERE DATE_FORMAT(e.createdAt, '%Y-%m-%d') = :formattedDate AND permission=null")
    long countByCreatedAt(@Param("formattedDate") String formattedDate);

    @Query("SELECT COUNT(e) FROM Users e WHERE DATE_FORMAT(e.createdAt, '%Y-%m-%d') >= :date1 AND DATE_FORMAT(e.createdAt, '%Y-%m-%d') <= :date2 AND permission=null")
    long countByCreatedAt(@Param("date1") String date1, @Param("date2") String date2);

}
