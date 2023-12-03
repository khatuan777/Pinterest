package com.socialmedia.controller;

import com.socialmedia.model.Content_Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.service.Content_ReportService;
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
@RequestMapping("/content_report")
@CrossOrigin
public class Content_ReportController {

    @Autowired
    private Content_ReportService service;

    @GetMapping("/id/{id}")
    public Content_Report getPermissionById(@PathVariable("id") int id) {
        Optional<Content_Report> optional = service.findById(id);
        Content_Report content_report = new Content_Report();
        if (optional.isPresent()) {
            content_report = optional.get();
        }
        return content_report;
    }

    @GetMapping("/getAll")
    public List<Content_Report> getAll() {
        List<Content_Report> list = service.getAll();
        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<Content_Report> add(@RequestBody Content_Report content_report) {
        return new ResponseEntity<>(service.save(content_report), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return service.delete(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Content_Report> update(@PathVariable("id") int id,
            @RequestBody Content_Report content_report) {
        Optional<Content_Report> optional = service.findById(id);

        if (optional.isPresent()) {
            Content_Report currentContent_Report = optional.get();
            currentContent_Report.setContent(content_report.getContent());
            currentContent_Report.setDescription(content_report.getDescription());
            return new ResponseEntity<>(service.save(currentContent_Report), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
