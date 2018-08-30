 package com.hansan.fenxiao.entities;
 
 import java.io.Serializable;
 import java.util.Date;

 import javax.persistence.*;

 import org.hibernate.annotations.NotFound;
 import org.hibernate.annotations.NotFoundAction;
 
 @Entity
 @Table(name="orders")
 public class Orders extends BaseBean
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @Column(name = "money")
   private Double money;
 
   @ManyToOne(cascade={javax.persistence.CascadeType.PERSIST}, fetch=FetchType.EAGER)
   @JoinColumn(name="user")
   @NotFound(action=NotFoundAction.IGNORE)
   private User user;
   
   @Column(name = "productId")
   private String productId;
   @Column(name = "productName")
   private String productName;
   @Column(name = "productMoney")
   private Double productMoney;
   @Column(name = "productNum")
   private Integer productNum;
   @Column(name = "no")
   private String no;
   @Column(name = "status")
   private Integer status;
   @Column(name = "summary")
   private String summary;
   @Column(name = "payDate")
   @Temporal(TemporalType.TIMESTAMP)
   private Date payDate;
   @Column(name = "address_id")
   private Integer addressId;				//收货地址
   @Column(name = "express_dic_id")
   private Integer expressDicId;		//字典表(快递)id(已无用)
   @Column(name = "carriage_money")
   private Double carriageMoney;		//运费默认0
   @Column(name = "payoff_date")
   @Temporal(TemporalType.TIMESTAMP)
   private Date payoffDate;				//支付时间
   @Column(name = "order_qr_url")
   private String orderQrUrl;				//订单二维码 （userid=id1,id2）
   @Column(name = "payoff_way")
   private Integer payoffWay;			//支付方式(0-微信；1-支付宝，2-银联支付)
   @Column(name = "parent_param")
   private String parentParam;			//记录订单上级（上上级）用户id，parent_parent_id,parent_id
   // 非持久化
   @Transient
   private String productUrl ;
   // 非持久化
   @Transient
   private String phone ;
   // 非持久化
   @Transient
   private String address ;

   public Double getMoney()
   {
     return this.money;
   }
   public void setMoney(Double money) {
     this.money = money;
   }
   public String getNo() {
     return this.no;
   }
   public void setNo(String no) {
     this.no = no;
   }
   public Integer getStatus() {
     return this.status;
   }
   public void setStatus(Integer status) {
     this.status = status;
   }
   public User getUser() {
     return this.user;
   }
   public void setUser(User user) {
     this.user = user;
   }
   public String getProductId() {
     return this.productId;
   }
   public void setProductId(String productId) {
     this.productId = productId;
   }
   public String getProductName() {
     return this.productName;
   }
   public void setProductName(String productName) {
     this.productName = productName;
   }
   public Integer getProductNum() {
     return this.productNum;
   }
   public void setProductNum(Integer productNum) {
     this.productNum = productNum;
   }
   public String getSummary() {
     return this.summary;
   }
   public void setSummary(String summary) {
     this.summary = summary;
   }
   public Double getProductMoney() {
     return this.productMoney;
   }
   public void setProductMoney(Double productMoney) {
     this.productMoney = productMoney;
   }
   public Date getPayDate() {
     return this.payDate;
   }
   public void setPayDate(Date payDate) {
     this.payDate = payDate;
   }
	public Integer getAddressId()
	{
		return addressId;
	}
	public void setAddressId(Integer addressId)
	{
		this.addressId = addressId;
	}
	public Integer getExpressDicId()
	{
		return expressDicId;
	}
	public void setExpressDicId(Integer expressDicId)
	{
		this.expressDicId = expressDicId;
	}
	public Double getCarriageMoney()
	{
		return carriageMoney;
	}
	public void setCarriageMoney(Double carriageMoney)
	{
		this.carriageMoney = carriageMoney;
	}
	public Date getPayoffDate()
	{
		return payoffDate;
	}
	public void setPayoffDate(Date payoffDate)
	{
		this.payoffDate = payoffDate;
	}
	public String getOrderQrUrl()
	{
		return orderQrUrl;
	}
	public void setOrderQrUrl(String orderQrUrl)
	{
		this.orderQrUrl = orderQrUrl;
	}
	public Integer getPayoffWay()
	{
		return payoffWay;
	}
	public void setPayoffWay(Integer payoffWay)
	{
		this.payoffWay = payoffWay;
	}
	public String getParentParam()
	{
		return parentParam;
	}
	public void setParentParam(String parentParam)
	{
		this.parentParam = parentParam;
	}

   public String getProductUrl() {
     return productUrl;
   }

   public void setProductUrl(String productUrl) {
     this.productUrl = productUrl;
   }

   public String getPhone() {
     return phone;
   }

   public void setPhone(String phone) {
     this.phone = phone;
   }

   public String getAddress() {
     return address;
   }

   public void setAddress(String address) {
     this.address = address;
   }
 }