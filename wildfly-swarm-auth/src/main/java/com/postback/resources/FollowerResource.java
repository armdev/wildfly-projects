package com.postback.resources;

import com.postback.dto.FollowersDTO;
import com.postback.entities.Followers;
import com.postback.facades.FollowersFacade;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.PathParam;

@Path("/")
@ApplicationScoped
public class FollowerResource {
    
    @Inject
    private FollowersFacade followersFacade;
    
    @GET
    @Path("/followers/user/{id}")
    @Produces("application/json")
    public List<FollowersDTO> findUserFollowers(@PathParam("id") Long id) {
        return followersFacade.getUserFollowers(id);
    }
    
   

}
