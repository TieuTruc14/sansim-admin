package com.osp.modelCustomer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 1/10/2018.
 */
@Entity
@Table(name = "SIMA_TRANSPAY_LOG")
public class TranspayLog implements Serializable {
    private static final long serialVersionUID = -6607832461929594565L;
    @Id
    @SequenceGenerator(name = "SIMA_TRANSPAY_LOG_SEQ", sequenceName = "SIMA_TRANSPAY_LOG_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_TRANSPAY_LOG_SEQ")
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "TYPE",nullable = false,length = 1)
    private Byte type;

    @Column(name = "COMMAND_CODE",nullable = false,length = 20)
    private String commandCode;

    @Column(name = "TRANS_TYPE",nullable = false,length = 20)
    private String transType;

    @Column(name = "SERVICE_NUMBER",nullable = false,length = 10)
    private String serviceNumber;

    @Column(name = "GEN_DATE")
    private Date genDate;

    @Column(name = "MOBILE_OPERATOR",length = 50)
    private String mobileOperator;

    @Column(name = "CARD_SERI",length = 50)
    private String cardSeri;

    @Column(name = "CARD_CODE",length = 50)
    private String cardCode;

    @Column(name = "MSISDN_PAYS",nullable = false,length = 50)
    private String msisdnPay;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "PAY_RESULT",nullable = false)
    private boolean payResult;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "PACKAGE_ID")
    private ConfigPackage configPackage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(String commandCode) {
        this.commandCode = commandCode;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

    public String getMobileOperator() {
        return mobileOperator;
    }

    public void setMobileOperator(String mobileOperator) {
        this.mobileOperator = mobileOperator;
    }

    public String getCardSeri() {
        return cardSeri;
    }

    public void setCardSeri(String cardSeri) {
        this.cardSeri = cardSeri;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getMsisdnPay() {
        return msisdnPay;
    }

    public void setMsisdnPay(String msisdnPay) {
        this.msisdnPay = msisdnPay;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public boolean isPayResult() {
        return payResult;
    }

    public void setPayResult(boolean payResult) {
        this.payResult = payResult;
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
}
