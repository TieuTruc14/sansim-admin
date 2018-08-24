package com.osp.modelCustomer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 1/15/2018.
 */
@Entity
@Table(name = "SIMA_MSISDN_LOG")
public class MsisdnLog implements Serializable {
    private static final long serialVersionUID = 935254226943765036L;

    @Id
    @SequenceGenerator(name="SIMA_MSISDN_LOG_SEQ", sequenceName="SIMA_MSISDN_LOG_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_MSISDN_LOG_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "MSISDN",nullable = false)
    private String msisdn;

    @Column(name = "PRICE",nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "CONFIRM_STATUS",nullable = false,length = 1)
    private boolean confirmStatus;

    @Column(name = "CONFIRM_EXPIRED")
    private Date confirmExpired;

    @Column(name = "STATUS",nullable = false,length = 1)
    private byte status;

    @Column(name = "GROUP_ID",nullable = false,columnDefinition = "int default 0")
    private Integer group;

    @Column(name = "DESCRIPTION",length = 4000)
    private String description;

    @Column(name = "GEN_DATE")
    private Date genDate;

    @Column(name = "SRC_ID")
    private Long srcId;

    @Column(name="TYPE")
    private Byte type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(boolean confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Date getConfirmExpired() {
        return confirmExpired;
    }

    public void setConfirmExpired(Date confirmExpired) {
        this.confirmExpired = confirmExpired;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

    public Long getSrcId() {
        return srcId;
    }

    public void setSrcId(Long srcId) {
        this.srcId = srcId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
