package com.osp.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 12/26/2017.
 */
@Entity
@Table(name = "ADM_USERS")
public class User implements Serializable,UserDetails {
    private static final long serialVersionUID = -8299255898396933698L;
    @Id
    @SequenceGenerator(name="ADM_USERS_SEQ", sequenceName="ADM_USERS_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_USERS_SEQ")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

//    @NotNull
//    @NotEmpty
    @Column(name = "USER_NAME",nullable = false,unique = true)
    private String username;

//    @NotNull
//    @NotEmpty
    @Column(name = "PASSWORD",nullable = false ,length = 100)
    private String password;

//    @NotNull
//    @NotEmpty
    @Column(name = "FULL_NAME", nullable = false,length = 100)
    private String fullName;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    @Column(name = "LAST_ACCESS_TIME")
    private Date lastAccessTime;

    @Column(name = "STATUS")
    private int status;

    @Column(name = "GEN_DATE")
    private Date genDate;

    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;

    private transient List<GrantedAuthority> grantedAuths;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuths;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
//        return this.status==1? false:true;
    }


    public List<GrantedAuthority> getGrantedAuths() {
        return grantedAuths;
    }

    public void setGrantedAuths(List<GrantedAuthority> grantedAuths) {
        this.grantedAuths = grantedAuths;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
