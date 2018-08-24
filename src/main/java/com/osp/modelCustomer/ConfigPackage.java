package com.osp.modelCustomer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 1/2/2018.
 */
@Entity
@Table(name = "SIMA_CONF_PACKAGE")
public class ConfigPackage implements Serializable{
    private static final long serialVersionUID = -7206990932923415595L;
    @Id
    @SequenceGenerator(name="SIMA_CONF_PACKAGE_SEQ", sequenceName="SIMA_CONF_PACKAGE_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_CONF_PACKAGE_SEQ")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "MAX_QUANTITY",nullable = false)
    private Integer maxQuantity;

    @Column(name = "FEE",nullable = false)
    private Long fee;

    @Column(name = "GEN_DATE",nullable = false)
    private Date genDate;

    @Column(name = "CREATE_BY",length = 50)
    private String createBy;

    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;

    @Column(name = "UPDATE_BY",length = 50)
    private String updateBy;

    @Column(name = "PACKAGE_CODE",length = 50,nullable = false,unique = true)
    private String packageCode;

    @Column(name = "PACKAGE_NAME",length = 200,nullable = false)
    private String packageName;

    @Column(name = "PERIOD",nullable = false)
    private Short period;

    @Column(name = "STATUS",nullable = false)
    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public Short getPeriod() {
        return period;
    }

    public void setPeriod(Short period) {
        this.period = period;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
