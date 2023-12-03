package com.socialmedia.service;

import com.socialmedia.model.Functions;
import com.socialmedia.model.Permissions;
import java.util.List;
import java.util.Optional;

public interface FunctionService {

    public List<Functions> getAll();

    public Optional<Functions> findById(int id);

    public Functions save(Functions type);

    public boolean delete(int id);


}
