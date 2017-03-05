package com.project.web.auth;

import com.project.web.handlers.SessionContext;
import com.project.web.model.UserModel;
import com.project.web.rest.UserRESTClient;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


//@ManagedBean(name = "loginBean")
@Model
@RequestScoped
public class LoginBean {

  //  @ManagedProperty(value = "#{userRESTClient}")
    @Inject
    private UserRESTClient userRESTClient = new UserRESTClient();
    //@ManagedProperty("i18n")
    @Inject
    private PropertyResourceBundle  bundle;
    //@ManagedProperty("#{sessionContext}")
    @Inject
    private SessionContext sessionContext;
    private UserModel userModel;

    public LoginBean() {        
    }
    
    @PostConstruct
    public void init(){
         userModel = new UserModel();                
    }

    public String loginUser() {
        System.out.println("loginUser called");
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();       
//        UserModel user = userRESTClient.userLogin(userModel);
//        if (user != null) {
//            sessionContext.setUser(user);
//            return "profile";
//        }        
        FacesMessage msg = new FacesMessage(bundle.getString("nouser"), bundle.getString("nouser"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;       
    }
    
     public String register() {
        System.out.println("register called");
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();       
        UserModel user = userRESTClient.userRegistration(userModel);     
        return "login";       
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }    
    
    private void facesError(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

 

 
}
