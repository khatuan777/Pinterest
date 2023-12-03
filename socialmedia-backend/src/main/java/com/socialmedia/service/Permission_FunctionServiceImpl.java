package com.socialmedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.socialmedia.model.Permission_Function;
import com.socialmedia.model.Permissions;
import com.socialmedia.repository.Permission_FunctionRepository;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class Permission_FunctionServiceImpl implements Permission_FunctionService {

    @Autowired
    private Permission_FunctionRepository repository;

    @Override
    public List<Permission_Function> findAllByPermission(Permissions permission) {
        return repository.findAllByPermission(permission);
    }

    @Override
    public Permission_Function save(Permission_Function permission_function) {
        return repository.save(permission_function);
    }

    @Override
    public void deleteAll(List<Permission_Function> permissionFunctions) {
        repository.deleteAll(permissionFunctions);
    }

    @Override
    @Transactional
    public void deleteByPermission(Permissions permission) {
        repository.deleteByPermission(permission);
    }


}
