package com.postback.facades;

import com.postback.dto.FollowersDTO;
import com.postback.entities.Followers;
import com.postback.entities.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;

/**
 *
 * @author armenar
 */
@Stateless
public class FollowersFacade extends AbstractFacade<Followers> {

    @PersistenceContext(name = "userPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FollowersFacade() {
        super(Followers.class);
    }   

    public void insertFollower(User follower, User followee) {
        Followers followers = new Followers();
        followers.setFollowerId(follower);
        followers.setFolloweeId(followee);
        followers.setFollowDate(new Date(System.currentTimeMillis()));
        em.persist(followers);
        em.flush();
    }

    public boolean isUserFollower(Long follower, Long followee) {
        Followers followers = (Followers) em.createQuery("SELECT o FROM Followers o WHERE o.followerId.id = :follower AND o.followeeId.id = :followee")
                .setParameter("follower", follower).setParameter("followee", followee).setMaxResults(1).getSingleResult();
        return followers != null;
    }

    //I follow
    public List<FollowersDTO> getUserFollowers(Long id) {
        List<FollowersDTO> finalList = new ArrayList<>();
        try {
            List<Followers> postList = em.createQuery("SELECT o FROM Followers o WHERE o.followerId.id = ?1").setParameter(1, id).getResultList();            
            ModelMapper mapper = new ModelMapper();
            postList.stream().map((u) -> mapper.map(u, FollowersDTO.class)).forEachOrdered((FollowersDTO destObject) -> {
                finalList.add(destObject);
            });            
        } catch (Exception e) {
        }
        return finalList;
    }

    //who follow me
    public List<FollowersDTO> getUserFollowees(Long id) {
        List<FollowersDTO> finalList = new ArrayList<>();        
        try {
           List<Followers> postList = em.createQuery("SELECT o FROM Followers o WHERE o.followeeId.id = ?1").setParameter(1, id).getResultList();
           ModelMapper mapper = new ModelMapper();
            postList.stream().map((u) -> mapper.map(u, FollowersDTO.class)).forEachOrdered((FollowersDTO destObject) -> {
                finalList.add(destObject);
            });    
        } catch (Exception e) {
        }
        return finalList;
    }

}
