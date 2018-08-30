package com.hansan.fenxiao.entities;

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
@Table(name = "wx_user")
public class WxUser extends BaseBean
{
	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "open_id")
	private String openId;
	@Column(name = "wx_name")
	private String wxName;
	@Column(name = "wx_image_url")
	private String wxImageUrl;
	@Column(name = "wx_qr_url")
	private String wxQrUrl;
	
	@Column(name = "is_auth")
	private Boolean isAuth;

	public Integer getUserId()
	{
		return userId;
	}
	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}
	public String getOpenId()
	{
		return openId;
	}
	public void setOpenId(String openId)
	{
		this.openId = openId;
	}
	public String getWxName()
	{
		return wxName;
	}
	public void setWxName(String wxName)
	{
		this.wxName = wxName;
	}
	public String getWxImageUrl()
	{
		return wxImageUrl;
	}
	public void setWxImageUrl(String wxImageUrl)
	{
		this.wxImageUrl = wxImageUrl;
	}
	public String getWxQrUrl()
	{
		return wxQrUrl;
	}
	public void setWxQrUrl(String wxQrUrl)
	{
		this.wxQrUrl = wxQrUrl;
	}
	public Boolean getAuth()
	{
		return isAuth;
	}
	public void setAuth(Boolean auth)
	{
		isAuth = auth;
	}
}
