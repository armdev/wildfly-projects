/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.postback.cdi;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author armenar
 */
@Named
@SessionScoped
public class NamedCDIBean implements Serializable{
    
    public String getUserSecretName(){
        return "secret@gmail.com";
    }
    
}
