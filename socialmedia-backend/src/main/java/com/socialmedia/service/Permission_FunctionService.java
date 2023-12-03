package com.socialmedia.service;

import com.socialmedia.model.Permission_Function;
import com.socialmedia.model.Permissions;
import java.util.List;

public interface Permission_FunctionService {

    public List<Permission_Function> findAllByPermission(Permissions permission);

    public Permission_Function save(Permission_Function board);

    public void deleteAll(List<Permission_Function> permissionFunctions);

    public void deleteByPermission(Permissions permission);


}
