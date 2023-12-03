package com.socialmedia.controller;

import com.socialmedia.model.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.socialmedia.service.TypeService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/types")
@CrossOrigin
public class TypeController {

    @Autowired
    private TypeService service;
    
    @GetMapping("/getAll")
    public List<Types> getAll() {
        List<Types> list = service.getAll();
        return list;
    }

    @GetMapping("/id/{id}")
    public Types getPinById(@PathVariable("id") int id) {
        Optional<Types> optional = service.findById(id);
        Types type = new Types();
        if (optional.isPresent()) {
            type = optional.get();
        }
        return type;
    }

    @PostMapping("/add")
    public ResponseEntity<Types> add(@RequestBody Types type) {
        return new ResponseEntity<>(service.save(type), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return service.delete(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Types> update(@PathVariable("id") int id, @RequestBody Types type) {
        Optional<Types> optional = service.findById(id);

        if (optional.isPresent()) {
            Types currentType = optional.get();
            currentType.setTypeName(type.getTypeName());
            return new ResponseEntity<>(service.save(currentType), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
