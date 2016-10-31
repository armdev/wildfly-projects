package com.project.web.auth;

import com.project.web.handlers.SessionContext;
import com.project.web.model.UserModel;
import com.project.web.rest.UserRESTClient;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean {

    @ManagedProperty(value = "#{userRESTClient}")
    private UserRESTClient userRESTClient;
//    @ManagedProperty("#{i18n}")
//    private ResourceBundle bundle = null;
    @ManagedProperty("#{sessionContext}")
    private SessionContext sessionContext = null;
    private UserModel userModel;

    public LoginBean() {        
    }
    
    @PostConstruct
    public void init(){
         userModel = new UserModel();                
    }

    public String loginUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();       
//        UserModel user = userRESTClient.userLogin(userModel);
//        if (user != null) {
//            sessionContext.setUser(user);
//            return "profile";
//        }        
        //FacesMessage msg = new FacesMessage(bundle.getString("nouser"), bundle.getString("nouser"));
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        return "profile";       
    }

    
    public void setUserRESTClient(UserRESTClient userRESTClient) {
        this.userRESTClient = userRESTClient;
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

    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

   

}
