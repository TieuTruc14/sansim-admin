package com.osp.modelCustomer;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 2/26/2018.
 */
@Entity
@Table(name = "SIMA_ARTICLE")
public class Article implements Serializable {
    private static final long serialVersionUID = -4552881075031869105L;
    @Id
    @SequenceGenerator(name="SIMA_ARTICLE_SEQ", sequenceName="SIMA_ARTICLE_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMA_ARTICLE_SEQ")
    @Column(name = "ID", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    @Column(name = "TITLE", nullable = false,length = 1000)
    private String title;
    @Lob
    @Column(name = "CONTENT", nullable = false)
    private String content;
    @Column(name = "SHORT_CONTENT",length = 2000)
    private String shortContent;
    @Column(name = "STATUS", nullable = false)
    private Byte status;
    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;
    @Column(name = "IMAGE_URL",length = 500)
    private String imageUrl;
    @Column(name = "LINK")
    private String link;
    @Column(name = "META_TITLE")
    private String metaTitle;
    @Column(name = "META_DESCRIPTION",length = 500)
    private String metaDescription;
    @Column(name = "META_KEYWORD",length = 2000)
    private String metaKeyword;
    @Column(name = "ALT_IMAGE")
    private String altImage;
    @Column(name = "DATE_CREATED",nullable = false)
    private Date dateCreated;
    @Column(name = "USER_CREATED",nullable = false)
    private Long userCreated;
    @Column(name = "DATE_UPDATED", nullable = false)
    private Date dateUpdated;
    @Column(name = "USER_UPDATED", nullable = false)
    private Long userUpdated;
    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    @Transient
    private MultipartFile file;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaKeyword() {
        return metaKeyword;
    }

    public void setMetaKeyword(String metaKeyword) {
        this.metaKeyword = metaKeyword;
    }

    public String getAltImage() {
        return altImage;
    }

    public void setAltImage(String altImage) {
        this.altImage = altImage;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
