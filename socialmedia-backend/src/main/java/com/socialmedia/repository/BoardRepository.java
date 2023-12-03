
package com.socialmedia.repository;

import com.socialmedia.model.Boards;
import com.socialmedia.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Boards,Integer> {

  public List<Boards> findByUserOrderByIdAsc(Users user);


}