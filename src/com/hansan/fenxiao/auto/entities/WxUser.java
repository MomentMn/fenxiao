package com.hansan.fenxiao.auto.entities;

import java.util.Date;

public class WxUser {
    private Integer id;

    private Date createdate;

    private Boolean deleted;

    private Integer version;

    private Integer userId;

    private String openId;

    private String wxName;

    private String wxImageUrl;

    private String wxQrUrl;

    private Boolean isAuth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName == null ? null : wxName.trim();
    }

    public String getWxImageUrl() {
        return wxImageUrl;
    }

    public void setWxImageUrl(String wxImageUrl) {
        this.wxImageUrl = wxImageUrl == null ? null : wxImageUrl.trim();
    }

    public String getWxQrUrl() {
        return wxQrUrl;
    }

    public void setWxQrUrl(String wxQrUrl) {
        this.wxQrUrl = wxQrUrl == null ? null : wxQrUrl.trim();
    }

    public Boolean getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Boolean isAuth) {
        this.isAuth = isAuth;
    }
}