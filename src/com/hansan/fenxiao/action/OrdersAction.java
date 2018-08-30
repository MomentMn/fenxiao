package com.hansan.fenxiao.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hansan.fenxiao.auto.entities.WxUser;
import com.hansan.fenxiao.constants.OrdersStatusEnum;
import com.hansan.fenxiao.constants.PayoffWayEnum;
import com.hansan.fenxiao.entities.*;


import com.hansan.fenxiao.constants.DeletedEnum;
import com.hansan.fenxiao.service.*;
import com.hansan.fenxiao.utils.BjuiJson;
import com.hansan.fenxiao.utils.BjuiPage;
import com.hansan.fenxiao.utils.FreemarkerUtils;
import com.hansan.fenxiao.utils.PageModel;
import freemarker.template.Configuration;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ordersAction")
@Scope("prototype")
public class OrdersAction extends BaseAction
{
  private static final long serialVersionUID = 1L;

  @Resource(name="ordersService")
  private IOrdersService<Orders> ordersService;

  @Resource(name="userAddressService")
  private IUserAddressService<UserAddress> userAddressService;

  @Resource(name="userService")
  private IUserService<User> userService;

  @Resource(name="productService")
  private IProductService<Product> productService;

  @Resource(name="kamiService")
  private IKamiService<Kami> kamiService;

  @Resource(name="financialService")
  private IFinancialService<Financial> financialService;

  @Resource(name="commissionService")
  private ICommissionService<Commission> commissionService;
  private Orders orders;
  private String ftlFileName;

  @Resource(name="configService")
  private IConfigService<Config> configService;

  public void list()
  {
    String key = this.request.getParameter("key");
    String countHql = "select count(*) from Orders where deleted=0";
    String hql = "from Orders where deleted=0";
    if (StringUtils.isNotEmpty(key)) {
      countHql = countHql + " and (user.name='" + key + "' or no='" + key + "' or productName='" + key + "')";
      hql = hql + " and (user.name='" + key + "' or no='" + key + "' or productName='" + key + "')";
    }
    hql = hql + " order by id desc";

    int count = 0;
    count = this.ordersService.getTotalCount(countHql, new Object[0]);
    this.page = new BjuiPage(this.pageCurrent, this.pageSize);
    this.page.setTotalCount(count);
    this.cfg = new Configuration();

    this.cfg.setServletContextForTemplateLoading(
      this.request.getServletContext(), "WEB-INF/templates/admin");
    List ordersList = this.ordersService.list(hql, this.page.getStart(), this.page.getPageSize(), new Object[0]);
    Map root = new HashMap();
    root.put("ordersList", ordersList);
    root.put("page", this.page);
    root.put("key", key);
    FreemarkerUtils.freemarker(this.request, this.response, this.ftlFileName, this.cfg, root);
  }

  public void add()
  {
    String pidStr = this.request.getParameter("pid");
    int pid = 0;
    try {
      pid = Integer.parseInt(pidStr);
    } catch (Exception e) {
      this.request.setAttribute("status", "0");
      this.request.setAttribute("message", "参数错误");
      try {
        this.request.getRequestDispatcher("cart.jsp").forward(this.request, this.response);
      } catch (ServletException e1) {
        e1.printStackTrace();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      return;
    }
    Product findProduct = (Product)this.productService.findById(Product.class, pid);
    if (findProduct == null) {
      this.request.setAttribute("status", "0");
      this.request.setAttribute("message", "商品不存在");
    } else {
      this.request.setAttribute("status", "1");
      this.request.setAttribute("product", findProduct);
    }
    try {
      this.request.getRequestDispatcher("cart.jsp").forward(this.request, this.response);
    } catch (ServletException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void save()
  {
    String pidStr = this.request.getParameter("pid");
    String numStr = this.request.getParameter("num");
    int pid = 0;
    int num = 1;
    try {
      pid = Integer.parseInt(pidStr);
      num = Integer.parseInt(numStr);
    } catch (Exception e) {
      this.request.setAttribute("status", "0");
      this.request.setAttribute("message", "参数错误");
      try {
        this.request.getRequestDispatcher("orderAdd.jsp").forward(this.request, this.response);
      } catch (ServletException e1) {
        e1.printStackTrace();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      return;
    }
    Product findProduct = (Product)this.productService.findById(Product.class, pid);
    if (findProduct == null) {
      this.request.setAttribute("status", "0");
      this.request.setAttribute("message", "商品不存在");
    } else {
      HttpSession session = this.request.getSession();
      com.hansan.fenxiao.auto.entities.WxUser loginUser = (com.hansan.fenxiao.auto.entities.WxUser)session.getAttribute("loginUser");
      System.out.println(loginUser.getWxName());
      if ((loginUser == null) || (loginUser.getUserId() == null)) {
        this.request.setAttribute("status", "0");
        this.request.setAttribute("message", "您未登陆或者登陆失效，请重新登陆");
      } else {


        try {
          //this.response.sendRedirect("settle?no=" + no);
          this.request.setAttribute("product", findProduct);
          this.request.setAttribute("num", num);
          try {
            this.request.getRequestDispatcher("settle.jsp").forward(this.request, this.response);
          } catch (ServletException e) {
            e.printStackTrace();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void settle()
  {
    String no = this.request.getParameter("no");
    Orders findOrders = this.ordersService.findByNo(no);
    Product product = productService.findById(Product.class,Integer.parseInt(findOrders.getProductId()));
    if (findOrders == null) {
      this.request.setAttribute("status", "0");
      this.request.setAttribute("message", "订单不存在");
    } else {
      HttpSession session = this.request.getSession();
      User loginUser = (User)session.getAttribute("loginUser");
      if ((loginUser == null) || (loginUser.getId() == null)) {
        this.request.setAttribute("status", "0");
        this.request.setAttribute("message", "您未登陆或者登陆失效，请重新登陆");
      } else {
        this.request.setAttribute("orders", findOrders);
        this.request.setAttribute("product", product);
        try {
          this.request.getRequestDispatcher("settle.jsp").forward(this.request, this.response);
        } catch (ServletException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 提交订单和地址
   */
 public void pay()
  {

    JSONObject json = new JSONObject();
    String pid = request.getParameter("pid");
    String num = request.getParameter("num");
    String name = request.getParameter("name");
    String tel = request.getParameter("tel");
    String code = request.getParameter("code");
    String location = request.getParameter("location");
    String content = request.getParameter("content");
    String summary = request.getParameter("summary");

    HttpSession session = request.getSession();
    com.hansan.fenxiao.auto.entities.WxUser loginUser = (com.hansan.fenxiao.auto.entities.WxUser)session.getAttribute("loginUser");
    System.out.println("loginUser:===================== "+loginUser.getWxName());
    if ((loginUser == null) || (loginUser.getUserId() == null)) {
      json.put("status", "0");
      json.put("message", "您未登陆或者登陆失效，请重新登陆");
    } else {
      //
      User user = userService.findById(User.class,loginUser.getUserId());
      UserAddress userAddress = new UserAddress();
      userAddress.setUserId(loginUser.getUserId());
      userAddress.setCityCode(code);
      userAddress.setCityFullName(location);
      userAddress.setName(name);
      userAddress.setDetails(content);
      userAddress.setPhone(tel);
      userAddress.setCreateDate(new Date());
      userAddressService.saveOrUpdate(userAddress);

      Product product = productService.findById(Product.class,Integer.parseInt(pid));
      Orders orders = new Orders();
      orders.setProductId(""+pid);
      orders.setProductName(product.getTitle());
      orders.setProductNum(Integer.valueOf(num));
      orders.setProductMoney(product.getMoney());
      orders.setUser(user);
      orders.setStatus(OrdersStatusEnum.unpaid.getId());
      orders.setSummary(summary);
      orders.setPayDate(new Date());
      orders.setAddressId(userAddress.getId());
      orders.setPayoffWay(PayoffWayEnum.wechat.getCode());
      orders.setMoney(Double.valueOf(Integer.valueOf(num) * product.getMoney().doubleValue()));
      // 获取上级
      String parenrparam = (String) request.getSession().getAttribute("parentparam");
      logger.info("parentparam is " + parenrparam);
      // // BEGIN UPDATE BY TANNC 2018-06-08 20:42:00
      if(parenrparam!=null){
        orders.setParentParam(parenrparam);
      }else{
        orders.setParentParam(null);
      }
      Random random = new Random();
      int n = random.nextInt(9999);
      n += 10000;

      String no = ""+System.currentTimeMillis() + n;
      orders.setNo(no);
      orders.setDeleted(false);
      orders.setCreateDate(new Date());
      this.ordersService.saveOrUpdate(orders);
      request.getSession().removeAttribute("parentparam");
      json.put("status", "1");
      json.put("oid",orders.getId());
      json.put("message", "提交成功");

    }
    PrintWriter out = null;
    try {
      out = this.response.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    out.print(json.toString());
    out.flush();
    out.close();
  }

  public void detail()
  {
    String no = this.request.getParameter("no");
    Orders findOrders = this.ordersService.findByNo(no);
    if (findOrders == null) {
      this.request.setAttribute("status", "0");
      this.request.setAttribute("message", "订单不存在");
    } else {
      HttpSession session = this.request.getSession();
      User loginUser = (User)session.getAttribute("loginUser");
      if (findOrders.getUser().getId() != loginUser.getId()) {
        this.request.setAttribute("status", "0");
        this.request.setAttribute("message", "没有权限");
      } else {
        this.request.setAttribute("orders", findOrders);
        try {
          this.request.getRequestDispatcher("ordersDetail.jsp").forward(this.request, this.response);
        } catch (ServletException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void indexList() {
    String pStr = this.request.getParameter("p");
    int p = 1;
    if (!StringUtils.isEmpty(pStr)) {
      p = Integer.parseInt(pStr);
    }

    String type = this.request.getParameter("type");
    HttpSession session = this.request.getSession();
    WxUser loginUser = (WxUser)session.getAttribute("loginUser");
    String countHql = "select count(*) from Orders where deleted=0 and user.id=" + loginUser.getUserId();
    String hql = "from Orders where deleted=0 and user.id=" + loginUser.getUserId();
    if (("0".equals(type)) || ("1".equals(type))) {
      countHql = countHql + " and status=" + type;
      hql = hql + " and status=" + type;
    }
    hql = hql + " order by id desc";
    int count = 0;
    count = this.ordersService.getTotalCount(countHql, new Object[0]);
    PageModel pageModel = new PageModel();
    pageModel.setAllCount(count);
    pageModel.setCurrentPage(p);
    List<Orders> ordersList = this.ordersService.list(hql, pageModel.getStart(), pageModel.getPageSize(), new Object[0]);
    // 产品的图片
    if(null!=ordersList && !ordersList.isEmpty()){
          Set<Integer> productids = new HashSet<Integer>();
          for(Orders order : ordersList){
            //产品Id集合
            productids.add(Integer.parseInt(order.getProductId()));
          }
          String selectHql = "from  Product where id in ( " + StringUtils.join(productids,",") +" )";
          List<Product>  productList =  productService.list(selectHql);
           // id 映射
          Map<Integer,String>  idMapUrl = new HashMap<Integer,String>();
         if(null != productList && !productList.isEmpty()){
           for(Product pro :productList){
             idMapUrl.put(pro.getId(),pro.getPicture());
           }
         }
         // 通过id取 头像
      for(Orders order : ordersList){
        if(null!=order.getAddressId()) {

          //取地址
          UserAddress userAddress= userAddressService.findById(UserAddress.class, order.getAddressId());
          if(null!= userAddress){
            // 联系方式
            order.setPhone(userAddress.getPhone());
            // 联系方式
            order.setAddress(userAddress.getCityFullName() + userAddress.getDetails());
          }
        }
        //产品Id集合
        order.setProductUrl(idMapUrl.get(Integer.parseInt(order.getProductId())));
      }

    }
    JSONObject json = new JSONObject();
    if (ordersList.size() == 0)
    {
      json.put("status", "0");
      json.put("isNextPage", "0");
    }
    else {
      json.put("status", "1");
      if (ordersList.size() == pageModel.getPageSize())
      {
        json.put("isNextPage", "1");
      }
      else {
        json.put("isNextPage", "0");
      }
      JSONArray listJson = (JSONArray)JSONArray.toJSON(ordersList);
      json.put("list", listJson);
    }
    PrintWriter out = null;
    try {
      out = this.response.getWriter();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    out.print(json);
    out.flush();
    out.close();
  }

  public void info()
  {
    String idStr = this.request.getParameter("id");
    String callbackData = "";
    PrintWriter out = null;
    try {
      out = this.response.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try
    {
      if ((idStr == null) || ("".equals(idStr))) {
        callbackData = BjuiJson.json("300", "参数不能为空", "", "", "", 
          "", "", "");
      } else {
        int id = 0;
        try {
          id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
          callbackData = BjuiJson.json("300", "参数错误", "", "", "", 
            "", "", "");
        }
        Orders findorders = (Orders)this.ordersService.findById(
          Orders.class, id);
        if (findorders == null)
        {
          callbackData = BjuiJson.json("300", "订单不存在", "", "", 
            "", "", "", "");
        } else {
          this.cfg = new Configuration();

          this.cfg.setServletContextForTemplateLoading(
            this.request.getServletContext(), 
            "WEB-INF/templates/admin");
          Map root = new HashMap();
          root.put("orders", findorders);
          FreemarkerUtils.freemarker(this.request, this.response, this.ftlFileName, this.cfg, root);
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    out.print(callbackData);
    out.flush();
    out.close();
  }

  public void update()
  {
    PrintWriter out = null;
    try {
      out = this.response.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    String callbackData = "";
    try {
      if (this.orders == null) {
        callbackData = BjuiJson.json("300", "参数错误", "", "", "", "", 
          "", "");
      } else {
        Orders findorders = (Orders)this.ordersService.findById(Orders.class, this.orders.getId().intValue());
        this.orders.setCreateDate(findorders.getCreateDate());
        this.orders.setDeleted(findorders.isDeleted());
        this.orders.setVersion(findorders.getVersion());
        boolean result = this.ordersService.saveOrUpdate(this.orders);

        if (result) {
          callbackData = BjuiJson.json("200", "修改成功", "", 
            "", "", "true", "", "");
        }
        else
          callbackData = BjuiJson.json("300", "修改失败", "", 
            "", "", "", "", "");
      }
    }
    catch (JSONException e) {
      e.printStackTrace();
    }
    out.print(callbackData);
    out.flush();
    out.close();
  }

  public void delete()
  {
    PrintWriter out = null;
    try {
      out = this.response.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    String callbackData = "";
    try {
      String idStr = this.request.getParameter("id");

      if ((idStr == null) || ("".equals(idStr))) {
        callbackData = BjuiJson.json("300", "参数错误", "", "", "", "", 
          "", "");
      } else {
        int id = 0;
        try {
          id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
          callbackData = BjuiJson.json("300", "参数错误", "", "", "", 
            "", "", "");
        }
        Orders findorders = (Orders)this.ordersService.findById(Orders.class, id);
        if (findorders == null)
        {
          callbackData = BjuiJson.json("300", "订单不存在", "", "", 
            "", "true", "", "");
        } else {
          boolean result = this.ordersService.delete(findorders);
          if (result)
            callbackData = BjuiJson.json("200", "删除成功", "", 
              "", "", "", "", "");
          else
            callbackData = BjuiJson.json("300", "删除失败", "", 
              "", "", "", "", "");
        }
      }
    }
    catch (JSONException e) {
      e.printStackTrace();
    }
    out.print(callbackData);
    out.flush();
    out.close();
  }

  /**
   *
   * 验证当前用是否已经登录
   */
  public void promoteValid (){
    HttpSession session = this.request.getSession();
    WxUser wxUser = (WxUser)session.getAttribute("loginUser");
    String countHql = "select count(*) from Orders where deleted=0 ";
    countHql =countHql+" and user.id = "+String.valueOf(wxUser.getUserId());
   // 已支付
    countHql =countHql+ " and status = "+ OrdersStatusEnum.already_paid.getId();
    Integer count = 0;
    count = this.ordersService.getTotalCount(countHql, new Object[0]);
    Map<String,Object> map = new  HashMap<String,Object>();
    map.put("code","200");
    map.put("data",count);
    // 返回到前端
    renderJson(map);

  }

  /**
   * @description:  
   *     删除订单
   * @param
   *     -- 订单id
   * @author:谭农春
   * @createTime: 2018/6/6 22:10 
   */
  
  public void deleteOrder (){
    String id = this.request.getParameter("id");
    Orders order = ordersService.findById(Orders.class,Integer.parseInt(id));
    // 逻辑删除
    order.setDeleted(DeletedEnum.deleted.getCode());
    ordersService.saveOrUpdate(order);
    Map<String,Object> map = new  HashMap<String,Object>();
    map.put("code","200");
    map.put("data",0);
    // 返回到前端
    renderJson(map);
  }
  public Orders getOrders()
  {
    return this.orders;
  }

  public void setOrders(Orders orders) {
    this.orders = orders;
  }

  @Override
  public String getFtlFileName() {
    return this.ftlFileName;
  }

  @Override
  public void setFtlFileName(String ftlFileName) {
    this.ftlFileName = ftlFileName;
  }
}
