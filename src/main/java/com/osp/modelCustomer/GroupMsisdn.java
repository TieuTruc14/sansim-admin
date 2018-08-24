package com.osp.modelCustomer;

/**
 * Created by Admin on 12/20/2017.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SIMA_GROUP_MSISDN")
public class GroupMsisdn implements Serializable{
    private static final long serialVersionUID = 3689721863584258106L;
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "GROUP_NAME",nullable = false,length = 1000)
    private String groupName;

    @Column(name = "DESCRIPTION",length = 1000)
    private String description;

    @Column(name = "ACTIVE",nullable = false)
    private boolean active;

    @Column(name = "GEN_DATE")
    private Date genDate;

    @Column(name="ORDER_NUMBER")
    private Short orderNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Short getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Short orderNumber) {
        this.orderNumber = orderNumber;
    }
}
