package com.osp.modelCustomer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 1/8/2018.
 */
@Entity
@Table(name = "MT_QUEUE")
public class MTQueue implements Serializable {
    private static final long serialVersionUID = 1624620487769700699L;

    @Id
    @SequenceGenerator(name = "MT_QUEUE_SEQ", sequenceName = "MT_QUEUE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MT_QUEUE_SEQ")
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    private Long id;

    @Column(name = "SENDER_NUMBER", length = 15,nullable = false)
    private String senderNumber;

    @Column(name = "RECEIVER_NUMBER", length = 15,nullable = false)
    private String receiverNumber;

    @Column(name = "SERVICE_NUMBER", length = 15,nullable = false)
    private String serviceNumber;

    @Column(name = "MOBILE_OPERATOR", length = 10,nullable = false)
    private String mobileOperator;

    @Column(name = "COMMAND_CODE", length = 20,nullable = false)
    private String commandCode;

    @Column(name = "CONTENT_TYPE",length = 3,nullable = false)
    private Short contentType;

    @Column(name = "MESSAGE_TYPE",length = 2,nullable = false)
    private Byte messageType;

    @Column(name = "INFO",length = 1000,nullable = false)
    private String info;

    @Column(name = "REQUEST_ID",nullable = false)
    private Long requestId;

    @Column(name = "SOURCE",length = 100)
    private String source;

    @Column(name = "REQUEST_INFO",length = 200)
    private String requestInfo;

    @Column(name = "TELCO",length = 10)
    private String telco;

    @Column(name = "GEN_DATE",nullable = false)
    private Date genDate;

    @Column(name = "CREATED_BY",length = 50)
    private String createBy;

    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;

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

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
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

    public Short getContentType() {
        return contentType;
    }

    public void setContentType(Short contentType) {
        this.contentType = contentType;
    }

    public Byte getMessageType() {
        return messageType;
    }

    public void setMessageType(Byte messageType) {
        this.messageType = messageType;
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


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public String getTelco() {
        return telco;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
