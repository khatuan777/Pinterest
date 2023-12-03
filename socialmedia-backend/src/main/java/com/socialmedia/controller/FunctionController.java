package com.socialmedia.controller;

import com.socialmedia.model.Functions;
import com.socialmedia.model.Permissions;
import com.socialmedia.service.FunctionService;
import com.socialmedia.service.PermissionService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/functions")
@CrossOrigin
public class FunctionController {

    @Autowired
    private FunctionService functionService;


    @GetMapping("/id/{id}")
    public Functions getFunctionById(@PathVariable("id") int id) {
        Optional<Functions> optional = functionService.findById(id);
        Functions function = new Functions();
        if (optional.isPresent()) {
            function = optional.get();
        }
        return function;
    }

    @GetMapping("/getAll")
    public List<Functions> getAll() {
        List<Functions> list = functionService.getAll();
        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<Functions> add(@RequestBody Functions function) {
        return new ResponseEntity<>(functionService.save(function), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return functionService.delete(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Functions> update(@PathVariable("id") int id, @RequestBody Functions function) {
        Optional<Functions> optional = functionService.findById(id);

        if (optional.isPresent()) {
            Functions currentFunction = optional.get();
            currentFunction.setName(function.getName());
            return new ResponseEntity<>(functionService.save(currentFunction), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
