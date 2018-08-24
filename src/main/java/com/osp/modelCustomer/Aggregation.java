package com.osp.modelCustomer;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Admin on 1/26/2018.
 */
@Entity
@Table(name = "SIMA_AGGREGATION")
public class Aggregation implements Serializable {

    private static final long serialVersionUID = 2832610547752127457L;
    @Id
    @SequenceGenerator(name="SIMA_AGGREGATION_SEQ", sequenceName="SIMA_AGGREGATION_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_AGGREGATION_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "CUS_TOTAL", nullable = false)
    private long cusTotal;//tong khach hang
    @Column(name = "CUS_ACTIVE", nullable = false)
    private long cusActive;//tong khach hang active
    @Column(name = "CUS_NEW", nullable = false)
    private int cusNew;//so khach hang moi
    @Column(name = "MSISDN_NEW", nullable = false)
    private int msisdnNew;//so dang moi, tinh ca so ma nguoi dung xoa di dang lai
    @Column(name = "MSISDN_TOTAL", nullable = false)
    private long msisdnTotal;//tong thue bao
    @Column(name = "MSISDN_ACTIVE", nullable = false)
    private long msisdnActive;//tong thue bao hien thi binh thuong. status=1, neu =0 tuc khoa, =2 la admin duyet
    @Column(name = "MSISDN_NEW_REAL", nullable = false)
    private int msisdnNewReal;//so dang moi that su, truoc do nguoi dung chua up len
    @Column(name = "MSISDN_CONFIRM", nullable = false)
    private int msisdnConfirm;//so tin nhan xac thuc
    @Column(name = "RENEWAL_CONFIRM", nullable = false)
    private int renewalConfirm;//so ban tin gia han xac thuc trong ngay
    @Column(name = "REGISTER_PACK_NUMBER", nullable = false)
    private int registerPackNumber;//so tin nhan dang ky goi cuoc moi
    @Column(name = "RENEWAL_PACK_NUMBER", nullable = false)
    private int renewalPackNumber;//so tin nhan gia han goi cuoc
    @Column(name = "DESTROY_PACK_NUMBER", nullable = false)
    private int destroyPackNumber;//so goi cuoc khach bi huy do het han
    @Column(name = "DESTROY_CUS_PACK_NUMBER", nullable = false)
    private int destroyCusPackNumber;//so goi cuoc khach tu huy
    @Column(name = "REGISTER_REVENUE", nullable = false)
    private long registerRevenue;//doanh thu dang ky
    @Column(name = "RENEWAL_REVENUE", nullable = false)
    private long renewalRevenue;//doanh thu gia han
    @Column(name = "AUTHENTICATION_REVENUE", nullable = false)
    private long authenticationRevenue;//doanh thu xac thuc
    @Column(name = "RENEWAL_AUTHENTICATION_REVENUE", nullable = false)
    private long renewalAuthenticationRevenue;//doanh thu gia han xac thuc
    @Column(name = "REVENUE_TOTAL", nullable = false)
    private long revenueTotal;//tong doanh thu
    @Column(name = "DAY_OF_MONTH", nullable = false)
    private int dayOfMonth;
    @Column(name = "WEEK_OF_YEAR", nullable = false)
    private int weekOfYear;
    @Column(name = "MONTH", nullable = false)
    private int month;
    @Column(name = "QUARTER", nullable = false)
    private int quarter;
    @Column(name = "YEAR", nullable = false)
    private int year;
    @Column(name = "IN_DATE", nullable = false,unique = true)
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCusNew() {
        return cusNew;
    }

    public void setCusNew(int cusNew) {
        this.cusNew = cusNew;
    }

    public int getMsisdnNew() {
        return msisdnNew;
    }

    public void setMsisdnNew(int msisdnNew) {
        this.msisdnNew = msisdnNew;
    }

    public int getMsisdnNewReal() {
        return msisdnNewReal;
    }

    public void setMsisdnNewReal(int msisdnNewReal) {
        this.msisdnNewReal = msisdnNewReal;
    }

    public int getMsisdnConfirm() {
        return msisdnConfirm;
    }

    public void setMsisdnConfirm(int msisdnConfirm) {
        this.msisdnConfirm = msisdnConfirm;
    }

    public int getRenewalConfirm() {
        return renewalConfirm;
    }

    public void setRenewalConfirm(int renewalConfirm) {
        this.renewalConfirm = renewalConfirm;
    }

    public int getRegisterPackNumber() {
        return registerPackNumber;
    }

    public void setRegisterPackNumber(int registerPackNumber) {
        this.registerPackNumber = registerPackNumber;
    }

    public int getRenewalPackNumber() {
        return renewalPackNumber;
    }

    public void setRenewalPackNumber(int renewalPackNumber) {
        this.renewalPackNumber = renewalPackNumber;
    }

    public int getDestroyPackNumber() {
        return destroyPackNumber;
    }

    public void setDestroyPackNumber(int destroyPackNumber) {
        this.destroyPackNumber = destroyPackNumber;
    }

    public long getRegisterRevenue() {
        return registerRevenue;
    }

    public void setRegisterRevenue(long registerRevenue) {
        this.registerRevenue = registerRevenue;
    }

    public long getRenewalRevenue() {
        return renewalRevenue;
    }

    public void setRenewalRevenue(long renewalRevenue) {
        this.renewalRevenue = renewalRevenue;
    }

    public long getAuthenticationRevenue() {
        return authenticationRevenue;
    }

    public void setAuthenticationRevenue(long authenticationRevenue) {
        this.authenticationRevenue = authenticationRevenue;
    }

    public long getRenewalAuthenticationRevenue() {
        return renewalAuthenticationRevenue;
    }

    public void setRenewalAuthenticationRevenue(long renewalAuthenticationRevenue) {
        this.renewalAuthenticationRevenue = renewalAuthenticationRevenue;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDestroyCusPackNumber() {
        return destroyCusPackNumber;
    }

    public void setDestroyCusPackNumber(int destroyCusPackNumber) {
        this.destroyCusPackNumber = destroyCusPackNumber;
    }

    public long getCusTotal() {
        return cusTotal;
    }

    public void setCusTotal(long cusTotal) {
        this.cusTotal = cusTotal;
    }

    public long getCusActive() {
        return cusActive;
    }

    public void setCusActive(long cusActive) {
        this.cusActive = cusActive;
    }

    public long getMsisdnTotal() {
        return msisdnTotal;
    }

    public void setMsisdnTotal(long msisdnTotal) {
        this.msisdnTotal = msisdnTotal;
    }

    public long getMsisdnActive() {
        return msisdnActive;
    }

    public void setMsisdnActive(long msisdnActive) {
        this.msisdnActive = msisdnActive;
    }

    public long getRevenueTotal() {
        return revenueTotal;
    }

    public void setRevenueTotal(long revenueTotal) {
        this.revenueTotal = revenueTotal;
    }
}
