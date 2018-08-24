package com.osp.modelCustomer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 2/6/2018.
 */
@Entity
@Table(name = "SIMA_GROUP_YEAR")
public class GroupYear implements Serializable {
    private static final long serialVersionUID = -3684188687295746369L;

    @Id
    @SequenceGenerator(name="SIMA_GROUP_YEAR_SEQ", sequenceName="SIMA_GROUP_YEAR_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_GROUP_YEAR_SEQ")
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "YEAR", nullable = false)
    private Integer year;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ORDER_NUMBER",nullable = false)
    private Short orderNumber;
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;
    @Column(name = "GEN_DATE", nullable = false)
    private Date genDate;
    @Column(name = "USER_CREATED", nullable = false)
    private Long userCreated;
    @Column(name = "LAST_UPDATED", nullable = false)
    private Date lastUpdated;
    @Column(name = "USER_UPDATED", nullable = false)
    private Long userUpdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Short orderNumber) {
        this.orderNumber = orderNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }
}
