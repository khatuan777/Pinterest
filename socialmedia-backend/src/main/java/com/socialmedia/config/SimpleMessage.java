/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.config;

import java.util.Date;

/**
 *
 * @author PC
 */
public class SimpleMessage {
    private int id;
    private int user_id;
    private int conversation_id;
    private int pin_id;
    private String content;
    private int sharedUserId;
    
    public SimpleMessage() {
    }

    public SimpleMessage(int id, int user_id, int conversation_id, int pin_id, String content, int sharedUserId) {
        this.id = id;
        this.user_id = user_id;
        this.conversation_id = conversation_id;
        this.pin_id = pin_id;
        this.content = content;
        this.sharedUserId = sharedUserId;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(int conversation_id) {
        this.conversation_id = conversation_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPin_id() {
        return pin_id;
    }

    public void setPin_id(int pin_id) {
        this.pin_id = pin_id;
    }

    public int getSharedUserId() {
        return sharedUserId;
    }

    public void setSharedUserId(int sharedUserId) {
        this.sharedUserId = sharedUserId;
    }
    
}
