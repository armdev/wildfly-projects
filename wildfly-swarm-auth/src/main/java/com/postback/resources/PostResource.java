package com.postback.resources;

import com.postback.dto.PostDTO;
import com.postback.entities.Post;

import com.postback.facades.PostFacade;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/")
@ApplicationScoped
public class PostResource {

    @Inject
    private PostFacade postFacade;

    @GET
    @Path("/post/user/all/{id}")
    @Produces("application/json")
    public List<PostDTO> findUserPosts(@PathParam("id") Long id) {
        return postFacade.getUserPosts(id);
    }    
    @GET
    @Path("/post/all")
    @Produces("application/json")
    public List<PostDTO> findAll() {
        return postFacade.getAllPosts();
    }
    
    @GET
    @Path("/post/followers/{id}")
    @Produces("application/json")
    public List<Post> findFollowers(@PathParam("id") Long id) {
        return postFacade.getFollowersPosts(id);
    }   

    @POST
    @Path("/post/save")
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Post entity) {
        postFacade.create(entity);        
    }

    @DELETE
    @Path("/post/delete/{id}")
    public void remove(@PathParam("id") Long id) {        
        postFacade.remove(postFacade.find(id));
    }

    @GET
    @Path("/post/find/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Post find(@PathParam("id") Long id) {
        return postFacade.find(id);
    }
    
    @PUT
    @Path("/post/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Post entity) {
        entity.setId(id);
        postFacade.edit(entity);
    }
   

}
