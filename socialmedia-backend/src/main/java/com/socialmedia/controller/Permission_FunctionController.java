/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.controller;

import com.socialmedia.model.Permission_Function;
import com.socialmedia.model.Permissions;
import com.socialmedia.service.PermissionService;
import com.socialmedia.service.Permission_FunctionService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MaiThy
 */
@RestController
@RequestMapping("/permission_function")
@CrossOrigin
public class Permission_FunctionController {

    @Autowired
    private Permission_FunctionService permission_FunctionService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/permissionId/{id}")
    public List<Permission_Function> getFunctionByPermissionId(@PathVariable("id") int id) {
        Optional<Permissions> optionalPermission = permissionService.findById(id);
        if (optionalPermission.isPresent()) {
            Permissions permission = optionalPermission.get();
            List<Permission_Function> list = permission_FunctionService.findAllByPermission(permission);
            return list;
        } else {
            return null;
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Permission_Function> add(@RequestBody Permission_Function permision_function) {
        return new ResponseEntity<>(permission_FunctionService.save(permision_function), HttpStatus.OK);
    }

    @PostMapping("/deleteByPermission")
    public void deleteByPermission(@RequestBody Permissions permission) {
        if (permission != null) {
            permission_FunctionService.deleteByPermission(permission);
        }
    }


}
