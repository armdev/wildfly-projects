package com.postback.dto;

import java.io.Serializable;
import java.util.Date;

public class FollowersDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;    
    private Date followDate;    
    private UserDTO followerId;    
    private UserDTO followeeId;

    public FollowersDTO() {
    }

    public FollowersDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFollowDate() {
        return followDate;
    }

    public void setFollowDate(Date followDate) {
        this.followDate = followDate;
    }

    public UserDTO getFollowerId() {
        return followerId;
    }

    public void setFollowerId(UserDTO followerId) {
        this.followerId = followerId;
    }

    public UserDTO getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(UserDTO followeeId) {
        this.followeeId = followeeId;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FollowersDTO)) {
            return false;
        }
        FollowersDTO other = (FollowersDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.postback.entities.Followers[ id=" + id + " ]";
    }
    
}
