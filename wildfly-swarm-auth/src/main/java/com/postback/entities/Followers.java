/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.postback.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author armenar
 */
@Entity
@Table(name = "followers")
public class Followers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "follow_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date followDate;
    
    @JoinColumn(name = "follower_id", referencedColumnName = "id")
    @ManyToOne
    private User followerId;
    @JoinColumn(name = "followee_id", referencedColumnName = "id")
    @ManyToOne
    private User followeeId;

    public Followers() {
    }

    public Followers(Long id) {
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

    public User getFollowerId() {
        return followerId;
    }

    public void setFollowerId(User followerId) {
        this.followerId = followerId;
    }

    public User getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(User followeeId) {
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
        if (!(object instanceof Followers)) {
            return false;
        }
        Followers other = (Followers) object;
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
