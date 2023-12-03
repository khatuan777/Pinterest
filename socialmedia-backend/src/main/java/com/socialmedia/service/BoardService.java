
package com.socialmedia.service;

import com.socialmedia.model.Boards;
import com.socialmedia.model.Users;
import java.util.List;
import java.util.Optional;

public interface BoardService {
    public List<Boards> findByUserOrderByIdAsc(Users user);

    public Optional<Boards> findById(int id);
    public Boards save(Boards board);
    
    public boolean delete(int id);
}
