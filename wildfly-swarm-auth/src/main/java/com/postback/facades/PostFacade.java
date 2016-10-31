package com.postback.facades;

import com.postback.dto.PostDTO;
import com.postback.dto.UserDTO;
import com.postback.entities.Post;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;

/**
 *
 * @author armenar
 */
@Stateless
public class PostFacade extends AbstractFacade<Post> {

    @PersistenceContext(name = "userPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PostFacade() {
        super(Post.class);
    }
//http://modelmapper.org/downloads/

    public List<PostDTO> getUserPosts(Long id) {
        List<PostDTO> finalList = new ArrayList<>();
        try {
            List<Post> postList = em.createQuery("SELECT o FROM Post o WHERE o.user.id = ?1 ORDER BY o.postDate DESC").setParameter(1, id).getResultList();

            ModelMapper mapper = new ModelMapper();
            postList.stream().map((u) -> mapper.map(u, PostDTO.class)).forEachOrdered((destObject) -> {
                finalList.add(destObject);
            });
        } catch (Exception e) {
        }
        return finalList;
    }

    public List<Post> getFollowersPosts(Long id) {
        String sql = "SELECT p, u FROM Post, User "
                + "WHERE p.userId.id = u.id and ( "
                + "u.id = :id or "
                + "u.id in (select f.followeeId.id from Followers "
                + "where f.followerId.id = :id))"
                + "order by p.postDate DESC";
        List<Post> postList = new ArrayList<>();
        try {
            postList = em.createQuery(sql).setParameter(1, id).getResultList();
        } catch (Exception e) {
        }
        return postList;
    }

    public List<PostDTO> getAllPosts() {
        List<PostDTO> finalList = new ArrayList<>();
        try {
            List<Post> postList = em.createQuery("SELECT o FROM Post o ORDER BY o.postDate DESC").getResultList();
            ModelMapper mapper = new ModelMapper();            
            postList.stream().map((u) -> mapper.map(u, PostDTO.class)).forEachOrdered((destObject) -> {                
                finalList.add(destObject);
                
            });
        } catch (Exception e) {
        }
        return finalList;
    }

}
