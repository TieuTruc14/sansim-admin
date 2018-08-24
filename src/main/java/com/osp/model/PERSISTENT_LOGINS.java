package com.osp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by Admin on 1/23/2018.
 */
@Entity
@Table(name = "PERSISTENT_LOGINS")
public class PERSISTENT_LOGINS {
    @Id
    @Column(name = "SERIES", nullable = false,length=64)
    private String SERIES;
    @Column(name = "USERNAME", nullable = false,length=64)
    private String USERNAME;
    @Column(name = "TOKEN", nullable = false,length=64)
    private String TOKEN;
    @Column(name = "LAST_USED", nullable = false)
    private Timestamp LAST_USED;

    public String getSERIES() {
        return SERIES;
    }

    public void setSERIES(String SERIES) {
        this.SERIES = SERIES;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public Timestamp getLAST_USED() {
        return LAST_USED;
    }

    public void setLAST_USED(Timestamp LAST_USED) {
        this.LAST_USED = LAST_USED;
    }
}
