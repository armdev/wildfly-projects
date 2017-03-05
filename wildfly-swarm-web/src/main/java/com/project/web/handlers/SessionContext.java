package com.project.web.handlers;



import com.project.web.model.UserModel;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;


//@ManagedBean(name = "sessionContext")
@SessionScoped
@Model
public class SessionContext implements Serializable {
    
    private UserModel user;
   
    public SessionContext() {               
    }
    
    @PostConstruct
    public void init(){
        user = new UserModel();
    }
    

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    
    
  


}
