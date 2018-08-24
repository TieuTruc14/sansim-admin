package com.osp.modelCustomer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 1/10/2018.
 */
@Entity
@Table(name = "MT_LOG")
public class MTLog implements Serializable{
    private static final long serialVersionUID = -9075287827184777088L;
    @Id
    @SequenceGenerator(name = "MT_LOG_SEQ", sequenceName = "MT_LOG_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MT_LOG_SEQ")
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

    @Column(name = "COMMAND_CODE", length = 10,nullable = false)
    private String commandCode;

    @Column(name = "CONTENT_TYPE",nullable = false)
    private Short contentType;//length==3

    @Column(name = "MESSAGE_TYPE")
    private Byte messageType;

    @Column(name = "INFO", length = 1000,nullable = false)
    private String info;

    @Column(name = "TOTAL_SEGMENTS")
    private Byte totalSegments;

    @Column(name = "PROCESS_RESULT")
    private Byte processResult;

    @Column(name = "REQUEST_ID",nullable = false)
    private Long requestId;

    @Column(name = "MT_ID", length = 10,nullable = false)
    private String MTId;

    @Column(name = "NUM_OF_SENDS")
    private Long numOfSends;

    @Column(name = "SOURCE_MT",length = 100)
    private String sourceMT;

    @Column(name = "REQUEST_INFO", length = 200)
    private String requestInfo;

    @Column(name = "LAST_SEND_DATE")
    private Date lastSendDate;

    @Column(name = "GEN_DATE",nullable = false)
    private Date genDate;

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

    public Byte getTotalSegments() {
        return totalSegments;
    }

    public void setTotalSegments(Byte totalSegments) {
        this.totalSegments = totalSegments;
    }

    public Byte getProcessResult() {
        return processResult;
    }

    public void setProcessResult(Byte processResult) {
        this.processResult = processResult;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getMTId() {
        return MTId;
    }

    public void setMTId(String MTId) {
        this.MTId = MTId;
    }

    public Long getNumOfSends() {
        return numOfSends;
    }

    public void setNumOfSends(Long numOfSends) {
        this.numOfSends = numOfSends;
    }

    public String getSourceMT() {
        return sourceMT;
    }

    public void setSourceMT(String sourceMT) {
        this.sourceMT = sourceMT;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public Date getLastSendDate() {
        return lastSendDate;
    }

    public void setLastSendDate(Date lastSendDate) {
        this.lastSendDate = lastSendDate;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }
}
