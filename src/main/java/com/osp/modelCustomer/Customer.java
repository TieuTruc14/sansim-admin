package com.osp.modelCustomer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 12/14/2017.
 */
@Entity
@Table(name = "SIMA_CUSTOMER")
public class Customer implements Serializable {

    private static final long serialVersionUID = -3552661732973732446L;
    @Id
    @SequenceGenerator(name="ADM_CUSTOMER_SEQ", sequenceName="ADM_CUSTOMER_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_CUSTOMER_SEQ")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "USERNAME",nullable = false,unique = true)
    private String username;

    @Column(name = "PASSWORD",nullable = false ,length = 100)
    private String password;

    @Column(name = "MSISDN", nullable = false,length = 20)
    private String msisdn;

    @Column(name = "FULLNAME",nullable = false,length = 100)
    private String fullName;

    @Column(name = "IDNO",length = 50)
    private String indo;

    @Column(name = "EMAIL",length = 100)
    private String email;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "LAST_LOGIN")
    private Date lastLogin;

    @Column(name="active",nullable = false)
    private Byte active;

    @Column(name = "GEN_DATE")
    private Date genDate;

    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIndo() {
        return indo;
    }

    public void setIndo(String indo) {
        this.indo = indo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
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
