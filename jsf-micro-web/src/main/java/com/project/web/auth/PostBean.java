/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.auth;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author armenar
 */
@ManagedBean()
@Stateless
@SessionScoped
public class PostBean {

    @ManagedProperty(value = "#{mainDataBean}")
    private MainDataBean mainDataBean;
    private final List<String> messages = new ArrayList<>();
    private String message;

    public PostBean() {
    }

    public void addMessage() {
      //  System.out.println("Add message " + message);
        messages.add(message);
        mainDataBean.getMainMessages().add(message);
        message = null;
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<String> getAllMessages() {
        return mainDataBean.getMainMessages();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMainDataBean(MainDataBean mainDataBean) {
        this.mainDataBean = mainDataBean;
    }

}
