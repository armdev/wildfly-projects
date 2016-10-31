/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.auth;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author armenar
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class MainDataBean {

    private List<String> mainMessages = new ArrayList<>();
    

    public MainDataBean() {
    }
    
    @PostConstruct
    public void init(){
         mainMessages = new ArrayList<>();
    }
    
       
    
    public List<String> getMainMessages() {
        //if list is empty call database?
        return mainMessages;
    }

    
    

   

}
