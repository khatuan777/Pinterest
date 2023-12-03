package com.socialmedia.controller;

import com.socialmedia.model.Permissions;
import com.socialmedia.service.PermissionService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissions")
@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/id/{id}")
    public Permissions getPermissionById(@PathVariable("id") int id) {
        Optional<Permissions> optional = permissionService.findById(id);
        Permissions permission = new Permissions();
        if (optional.isPresent()) {
            permission = optional.get();
        }
        return permission;
    }

    @GetMapping("/getAll")
    public List<Permissions> getAll() {
        List<Permissions> list = permissionService.getAll();
        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<Permissions> add(@RequestBody Permissions permission) {
        return new ResponseEntity<>(permissionService.save(permission), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return permissionService.delete(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Permissions> update(@PathVariable("id") int id, @RequestBody Permissions permission) {
        Optional<Permissions> optional = permissionService.findById(id);

        if (optional.isPresent()) {
            Permissions currentPermission = optional.get();
            currentPermission.setName(permission.getName());
            return new ResponseEntity<>(permissionService.save(currentPermission), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
