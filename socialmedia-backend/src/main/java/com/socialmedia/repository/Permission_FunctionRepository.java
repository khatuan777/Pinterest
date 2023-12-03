package com.socialmedia.repository;

import com.socialmedia.model.Permission_Function;
import com.socialmedia.model.Permissions;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Permission_FunctionRepository extends JpaRepository<Permission_Function, Integer> {

    public List<Permission_Function> findAllByPermission(Permissions permission);

    void deleteByPermission(Permissions permission);
    
}
