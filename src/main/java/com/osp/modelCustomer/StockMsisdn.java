package com.osp.modelCustomer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 12/20/2017.
 */
@Entity
@Table(name = "SIMA_STOCK_MSISDN")
public class StockMsisdn implements Serializable{
    private static final long serialVersionUID = 2788855918979408924L;

    @Id
    @SequenceGenerator(name="SIMA_STOCK_MSISDN_SEQ", sequenceName="SIMA_STOCK_MSISDN_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_STOCK_MSISDN_SEQ")
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
    private Long group;

    @Column(name = "DESCRIPTION",length = 4000)
    private String description;

    @Column(name = "GEN_DATE")
    private Date genDate;

    @Column(name = "LAST_UPDATED")
    private Date lastUpdate;

    @Column(name = "TELCO")
    private Byte telco;

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

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Byte getTelco() {
        return telco;
    }

    public void setTelco(Byte telco) {
        this.telco = telco;
    }
}
