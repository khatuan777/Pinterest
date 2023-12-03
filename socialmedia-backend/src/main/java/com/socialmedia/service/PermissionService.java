package com.socialmedia.service;

import com.socialmedia.model.Permissions;
import java.util.List;
import java.util.Optional;

public interface PermissionService {

    public List<Permissions> getAll();

    public Optional<Permissions> findById(int id);

    public Permissions save(Permissions type);

    public boolean delete(int id);
}
