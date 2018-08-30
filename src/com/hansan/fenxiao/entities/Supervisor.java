package com.hansan.fenxiao.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @version 1.0
 * @description:
 * @projectName: com.hansan.fenxiao.entities
 * @className: fenxiao
 * @author:谭农春
 * @createTime:2018/5/31 20:29
 */
@Entity
@Table(name = "supervisor")
public class Supervisor extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	
	  @Column(name = "parent_user_id")
    private Integer parentUserId;
    @Column(name = "parent_wx_name")
    private String parentWxName;
    @Column(name = "parent_wx_image_url")
    private String parentWxImageUrl;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "level")
    private Integer level;
    @Column(name = "order_id")
    private Integer orderId;

    @Basic
    public Integer getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(Integer parentUserId) {
        this.parentUserId = parentUserId;
    }

    @Basic
    public String getParentWxName() {
        return parentWxName;
    }

    public void setParentWxName(String parentWxName) {
        this.parentWxName = parentWxName;
    }

    @Basic
    public String getParentWxImageUrl() {
        return parentWxImageUrl;
    }

    public void setParentWxImageUrl(String parentWxImageUrl) {
        this.parentWxImageUrl = parentWxImageUrl;
    }

    @Basic
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supervisor that = (Supervisor) o;

        if (parentUserId != null ? !parentUserId.equals(that.parentUserId) : that.parentUserId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return level != null ? level.equals(that.level) : that.level == null;
    }

    @Override
    public int hashCode() {
        int result = parentUserId != null ? parentUserId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }
}
