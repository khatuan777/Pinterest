package com.socialmedia.service;

import com.socialmedia.model.Types;
import java.util.List;
import java.util.Optional;

public interface TypeService {
    public List<Types> getAll();

    public Optional<Types> findById(int id);

    public Types save(Types type);
    
     public boolean delete(int id);
}
