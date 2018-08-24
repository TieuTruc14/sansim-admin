package com.osp.modelCustomer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 1/10/2018.
 */
@Entity
@Table(name = "MO_LOG")
public class MOLog implements Serializable {
    private static final long serialVersionUID = -7622285712014125767L;
    @Id
    @SequenceGenerator(name = "MO_LOG_SEQ", sequenceName = "MO_LOG_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MO_LOG_SEQ")
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    private Long id;

    @Column(name = "SENDER_NUMBER", length = 15)
    private String senderNumber;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "SERVICE_NUMBER", length = 15)
    private String serviceNumber;

    @Column(name = "MOBILE_OPERATOR", length = 10)
    private String mobileOperator;

    @Column(name = "COMMAND_CODE", length = 20)
    private String commandCode;

    @Column(name = "INFO", length = 1000)
    private String info;

    @Column(name = "REQUEST_ID")
    private Long requestId;

    @Column(name = "HASH_CODE")
    private Long hashCode;

    @Column(name = "GEN_DATE")
    private Date genDate;

    public MOLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getMobileOperator() {
        return mobileOperator;
    }

    public void setMobileOperator(String mobileOperator) {
        this.mobileOperator = mobileOperator;
    }

    public String getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(String commandCode) {
        this.commandCode = commandCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getHashCode() {
        return hashCode;
    }

    public void setHashCode(Long hashCode) {
        this.hashCode = hashCode;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }
}
