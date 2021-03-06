package com.zhang.box.db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "AD_BEAN".
 */
@Entity
public class AdBean {

    @Id
    private Long id;
    private Integer adType;
    private String adVideoFile;
    private String adImgFile;
    private String adUrl;

    @Generated(hash = 1093384756)
    public AdBean() {
    }

    public AdBean(Long id) {
        this.id = id;
    }

    @Generated(hash = 1808075648)
    public AdBean(Long id, Integer adType, String adVideoFile, String adImgFile, String adUrl) {
        this.id = id;
        this.adType = adType;
        this.adVideoFile = adVideoFile;
        this.adImgFile = adImgFile;
        this.adUrl = adUrl;
    }




    @Override
    public String toString() {
        return "AdBean{" +
                "id=" + id +
                ", adType=" + adType +
                ", adVideoFile='" + adVideoFile + '\'' +
                ", adImgFile='" + adImgFile + '\'' +
                ", adUrl='" + adUrl + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAdType() {
        return this.adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    public String getAdVideoFile() {
        return this.adVideoFile;
    }

    public void setAdVideoFile(String adVideoFile) {
        this.adVideoFile = adVideoFile;
    }

    public String getAdImgFile() {
        return this.adImgFile;
    }

    public void setAdImgFile(String adImgFile) {
        this.adImgFile = adImgFile;
    }

    public String getAdUrl() {
        return this.adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }
}
