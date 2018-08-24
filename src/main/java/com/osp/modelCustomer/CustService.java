package com.osp.modelCustomer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SIMA_CUST_SERVICE")
public class CustService implements Serializable {

    private static final long serialVersionUID = -7129376526535202618L;
    @Id
    @SequenceGenerator(name = "SIMA_CUST_SERVICE_SEQ", sequenceName = "SIMA_CUST_SERVICE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_CUST_SERVICE_SEQ")
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID",unique = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACKAGE_ID")
    private ConfigPackage configPackage;

    @Column(name = "STATUS", nullable = false, length = 1)
    private Byte status;

    @Column(name = "EXPIRED_DATE")
    private Date expiredDate;

    @Column(name = "MT_STATUS")
    private Byte mtStatus;

    public Byte getMtStatus() {
        return mtStatus;
    }

    public void setMtStatus(Byte mtStatus) {
        this.mtStatus = mtStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ConfigPackage getConfigPackage() {
        return configPackage;
    }

    public void setConfigPackage(ConfigPackage configPackage) {
        this.configPackage = configPackage;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
