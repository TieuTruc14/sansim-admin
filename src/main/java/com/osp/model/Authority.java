package com.osp.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Admin on 12/27/2017.
 */
@Entity
@Table(name = "ADM_AUTHORITIES")
public class Authority implements Serializable {
    private static final long serialVersionUID = 2894810169009008957L;
    @Id
    @SequenceGenerator(name="ADM_AUTHORITIES_SEQ", sequenceName="ADM_AUTHORITIES_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_AUTHORITIES_SEQ")
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "AUTHORITY",nullable = false)
    private String authority;

    @Column(name = "FID",nullable = false)
    private int fid;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ORDER_ID")
    private int orderId;

    @Column(name = "AUTH_KEY")
    private Boolean authKey;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Boolean getAuthKey() {
        return authKey;
    }

    public void setAuthKey(Boolean authKey) {
        this.authKey = authKey;
    }
}
