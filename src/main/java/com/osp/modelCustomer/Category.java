package com.osp.modelCustomer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 2/26/2018.
 */
@Entity
@Table(name = "SIMA_CATEGORY")
public class Category implements Serializable {
    private static final long serialVersionUID = -6202125778049294544L;
    @Id
    @SequenceGenerator(name="SIMA_CATEGORY_SEQ", sequenceName="SIMA_CATEGORY_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_CATEGORY_SEQ")
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;
    @Column(name = "DELETED", nullable = false)
    private boolean deleted;
    @Column(name = "TYPE", nullable = false)
    private Byte type;//loai the loai
    @Column(name = "VALUE", nullable = false)
    private Integer value;//gia tri tham chieu,tranh ko phai loc tai giao dien customer theo id
    @Column(name = "DATE_CREATED", nullable = false)
    private Date dateCreated;
    @Column(name = "USER_CREATED", nullable = false)
    private Long userCreated;
    @Column(name = "DATE_UPDATED", nullable = false)
    private Date dateUpdated;
    @Column(name = "USER_UPDATED", nullable = false)
    private Long userUpdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
