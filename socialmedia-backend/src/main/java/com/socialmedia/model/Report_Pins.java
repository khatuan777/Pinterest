/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author MaiThy
 */
@Entity
public class Report_Pins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users userReport;

    @ManyToOne
    @JoinColumn(name = "userRatifyId")
    private Users userRatify;

    @ManyToOne
    @JoinColumn(name = "pinId")
    private Pins pin;

    @ManyToOne
    @JoinColumn(name = "content_ReportId")
    private Content_Report content;

    private boolean reject;
    private boolean approve;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUserReport() {
        return userReport;
    }

    public void setUserReport(Users userReport) {
        this.userReport = userReport;
    }

    public Pins getPin() {
        return pin;
    }

    public void setPin(Pins pin) {
        this.pin = pin;
    }

    public Content_Report getContent() {
        return content;
    }

    public void setContent(Content_Report content) {
        this.content = content;
    }

    public Boolean getReject() {
        return reject;
    }

    public void setReject(Boolean reject) {
        this.reject = reject;
    }

    public Boolean getApproval() {
        return approve;
    }

    public void setApproval(Boolean approve) {
        this.approve = approve;
    }

    public Users getUserRatify() {
        return userRatify;
    }

    public void setUserRatify(Users userRatify) {
        this.userRatify = userRatify;
    }
    
    

}
