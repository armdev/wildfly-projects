package com.postback.resources;

import com.postback.entities.User;
import com.postback.facades.UserFacade;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/auth")
@Stateless
public class UserResource {

    @Inject
    private UserFacade userFacade;

    @GET
    @Path("/user/all")
    @Produces("application/json")
    public List<User> getAllUsers() {
        return userFacade.findAll();
    }

    @POST
    @Path("/user/register")
    @Consumes({MediaType.APPLICATION_JSON})
    public Long create(User entity) {
        Long id = userFacade.registerNewUser(entity);
        return id;
    }

    @DELETE
    @Path("/user/delete/{id}")
    public void remove(@PathParam("id") Long id) {        
        userFacade.remove(userFacade.find(id));
    }

    @GET
    @Path("/user/find/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public User find(@PathParam("id") Long id) {
        return userFacade.find(id);
    }
    
    @PUT
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, User entity) {
        entity.setId(id);
        userFacade.edit(entity);
    }
   

}
