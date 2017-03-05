package com.postback.facades;

import com.postback.dto.UserDTO;
import com.postback.entities.User;
import com.postback.utils.HashUtil;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;


/**
 *
 * @author armenar
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(name = "userPU")
    private EntityManager em;


    public UserFacade() {
        super(User.class);
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserDTO doLogin(String email, String password) {
        UserDTO destObject = null;
        try {
            User user = (User) em.createQuery("SELECT o FROM User o WHERE o.email = ?1 AND o.passwd = ?2")
                    .setParameter(1, email)
                    .setParameter(2, HashUtil.hashPassword(password)).getSingleResult();          
            if (user == null) {
                return null;
            }            
            ModelMapper modelMapper = new ModelMapper();
            destObject = modelMapper.map(user, UserDTO.class);                 
        } catch (Exception e) {
        }
        return destObject;
    }
    
    public Long registerNewUser(User user){           
         user.setPasswd(HashUtil.hashPassword(user.getPasswd()));
         user.setRegisterDate(new Date(System.currentTimeMillis()));         
         em.persist(user);
         em.flush();
         Long userId = user.getId();
        return userId;
    }
    


    public User findById(Long id) {
        return this.find(id);
    }

    public Integer getCount() {
        return this.count();
    }

}
