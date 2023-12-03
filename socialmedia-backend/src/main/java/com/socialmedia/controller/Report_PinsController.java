/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.controller;

import com.socialmedia.model.Report_Pins;
import com.socialmedia.model.ResultReport;
import com.socialmedia.model.Users;
import com.socialmedia.repository.Report_PinRepository;
import com.socialmedia.service.Report_PinService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author phmlhuyntrang
 */
@RestController
@RequestMapping("/report_pins")
@CrossOrigin
public class Report_PinsController {

    @Autowired
    private Report_PinRepository reportPinRepository;
    @Autowired
    private Report_PinService reportPinService;

    @GetMapping("/count")
    public Object count() {
        long countAll = reportPinService.countAll();
        long countNotApprovedYet = reportPinService.countByUserRatify(null);
        long countApprove = reportPinService.countByApprove(true);
        ResultReport r = new ResultReport();
        r.countAll = countAll;
        r.countNotApprovedYet = countNotApprovedYet;
        r.countApprove = countApprove;
        return r;
    }

    @GetMapping(value = "/getAll")
    public List<Report_Pins> getAllReportPins() {
        return reportPinService.getAllReportPin();
    }

    @PostMapping("/add")
    public boolean saveReport(@RequestBody Report_Pins reportPin) {
        reportPinService.saveReportPins(reportPin);
        return true;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Object[]>> getPins() {
        try {
            List<Object[]> pins = reportPinRepository.findAllWithDetails();
            return new ResponseEntity<>(pins, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // New endpoint to fetch details for a specific ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Object[]> getCommentById(@PathVariable int id) {
        try {
            Object[] commentDetails = reportPinRepository.findDetailsById(id);
            return new ResponseEntity<>(commentDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/id/{id}/{approveState}")
    public ResponseEntity<String> changeApproveState(
            @PathVariable("id") int id,
            @RequestBody Users userRatify,
            @PathVariable("approveState") Boolean approveState) {

        try {
            System.out.println("======================" + userRatify);
            reportPinService.changeApprove(id, userRatify, approveState);
            return new ResponseEntity<>("Approve State changed successfully", HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to change approve state " + e.getMessage());
        }

    }
}
