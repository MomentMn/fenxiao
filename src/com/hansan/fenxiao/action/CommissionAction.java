package com.hansan.fenxiao.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hansan.fenxiao.auto.entities.WxUser;
import com.hansan.fenxiao.dto.CommissionListDto;
import com.hansan.fenxiao.entities.Admin;
import com.hansan.fenxiao.entities.Commission;
import com.hansan.fenxiao.entities.Config;
import com.hansan.fenxiao.entities.User;
import com.hansan.fenxiao.enums.CommissionTypeEnum;
import com.hansan.fenxiao.service.ICommissionService;
import com.hansan.fenxiao.service.IConfigService;
import com.hansan.fenxiao.service.IUserService;
import com.hansan.fenxiao.utils.BjuiJson;
import com.hansan.fenxiao.utils.BjuiPage;
import com.hansan.fenxiao.utils.FreemarkerUtils;
import com.hansan.fenxiao.utils.PageModel;
import freemarker.template.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("commissionAction")
@Scope("prototype")
public class CommissionAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    @Resource(name = "commissionService")
    private ICommissionService<Commission> commissionService;

    @Resource(name = "configService")
    private IConfigService<Config> configService;

    @Resource(name = "userService")
    private IUserService<User> userService;
    private Commission commission;
    private String ftlFileName;

    public void list() {
        String key = this.request.getParameter("key");
        String countHql = "select count(*) from Commission where deleted=0";
        String hql = "from Commission where deleted=0";
        if (StringUtils.isNotEmpty(key)) {
            countHql = countHql + " and (user.name='" + key + "' or no='" + key + "')";
            hql = hql + " and (user.name='" + key + "' or no='" + key + "')";
        }
        hql = hql + " order by id desc";

        int count = 0;
        count = this.commissionService.getTotalCount(countHql, new Object[0]);
        this.page = new BjuiPage(this.pageCurrent, this.pageSize);
        this.page.setTotalCount(count);
        this.cfg = new Configuration();

        this.cfg.setServletContextForTemplateLoading(
                this.request.getServletContext(), "WEB-INF/templates/admin");
        List commissionList = this.commissionService.list(hql, this.page.getStart(), this.page.getPageSize(), new Object[0]);
        Map root = new HashMap();
        root.put("commissionList", commissionList);
        root.put("page", this.page);
        root.put("key", key);
        FreemarkerUtils.freemarker(this.request, this.response, this.ftlFileName, this.cfg, root);
    }


    /**
     * @param
     * @description: 佣金提现
     * @author:谭农春
     * @createTime: 2018/6/5 16:28
     */

    public void payforCommission() throws JSONException {
        // 提现金额
        String key = this.request.getParameter("val");
        HttpSession session = this.request.getSession();
        WxUser loginUser = (WxUser) session.getAttribute("loginUser");
        Map<String, String> maps = new HashMap<String, String>();
        String path = request.getServletContext().getRealPath("/cert/");
        try {
            Double commission = commissionService.wxPayfor(loginUser, Double.parseDouble(key), path);
            // 返回状态
            maps.put("code", "200");
            maps.put("msg", "提现成功");
            // 剩余资金
            maps.put("amount", String.valueOf(commission));

        } catch (RuntimeException e) {
            maps.put("code", "400");
            maps.put("msg", e.getMessage());
        }
        renderJson(maps);

    }

    public void add() {
        this.cfg = new Configuration();

        this.cfg.setServletContextForTemplateLoading(
                this.request.getServletContext(), "WEB-INF/templates/admin");
        Map root = new HashMap();
        FreemarkerUtils.freemarker(this.request, this.response, this.ftlFileName, this.cfg, root);
    }

    public void save() {
        HttpSession session = this.request.getSession();
        Admin loginAdmin = (Admin) session.getAttribute("loginAdmin");
        PrintWriter out = null;
        try {
            out = this.response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String callbackData = "";
        try {
            if (this.commission == null) {
                callbackData = BjuiJson.json("300", "参数错误", "", "", "", "", "", "");
            } else {
                User findUser = this.userService.getUserByName(this.commission.getUser().getName());
                if (findUser == null) {
                    callbackData = BjuiJson.json("300", "用户名不存在", "", "", "", "", "", "");
                } else {
                    this.commission.setMoney(Double.valueOf(-this.commission.getMoney().doubleValue()));

                    this.commission.setOperator(loginAdmin.getName());

                    this.commission.setUser(findUser);

                    this.commission.setCreateDate(new Date());
                    this.commission.setDeleted(false);
                    findUser.setBalance(Double.valueOf(findUser.getBalance().doubleValue() + this.commission.getMoney().doubleValue()));
                    this.commission.setRemark("扣除游戏额度");
                    boolean res = this.commissionService.saveOrUpdate(this.commission);
                    if (res) {
                        callbackData = BjuiJson.json("200", "添加成功", "", "", "", "true", "", "");

                        this.userService.saveOrUpdate(findUser);
                    } else {
                        callbackData = BjuiJson.json("300", "添加失败，请重试", "", "", "", "", "", "");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        out.print(callbackData);
        out.flush();
        out.close();
    }

    public void info() {
        String idStr = this.request.getParameter("id");
        String callbackData = "";
        PrintWriter out = null;
        try {
            out = this.response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if ((idStr == null) || ("".equals(idStr))) {
                callbackData = BjuiJson.json("300", "参数不能为空", "", "", "",
                        "", "", "");
            } else {
                int id = 0;
                try {
                    id = Integer.parseInt(idStr);
                } catch (Exception e) {
                    callbackData = BjuiJson.json("300", "参数错误", "", "", "",
                            "", "", "");
                }
                Commission findcommission = (Commission) this.commissionService.findById(Commission.class, id);
                if (findcommission == null) {
                    callbackData = BjuiJson.json("300", "用户不存在", "", "",
                            "", "", "", "");
                } else {
                    this.cfg = new Configuration();

                    this.cfg.setServletContextForTemplateLoading(
                            this.request.getServletContext(),
                            "WEB-INF/templates/admin");
                    Map root = new HashMap();
                    root.put("commission", findcommission);
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

    public void update() {
        PrintWriter out = null;
        try {
            out = this.response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String callbackData = "";
        try {
            if (this.commission == null) {
                callbackData = BjuiJson.json("300", "参数错误", "", "", "", "",
                        "", "");
            } else {
                Commission findcommission = (Commission) this.commissionService.findById(Commission.class, this.commission.getId().intValue());
                this.commission.setCreateDate(findcommission.getCreateDate());
                this.commission.setDeleted(findcommission.isDeleted());
                this.commission.setVersion(findcommission.getVersion());
                boolean result = this.commissionService.saveOrUpdate(this.commission);

                if (result) {
                    callbackData = BjuiJson.json("200", "修改成功", "",
                            "", "", "true", "", "");
                } else {
                    callbackData = BjuiJson.json("300", "修改失败", "",
                            "", "", "", "", "");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        out.print(callbackData);
        out.flush();
        out.close();
    }

    public void delete() {
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
                } catch (Exception e) {
                    callbackData = BjuiJson.json("300", "参数错误", "", "", "",
                            "", "", "");
                }
                Commission findcommission = (Commission) this.commissionService.findById(Commission.class, id);
                if (findcommission == null) {
                    callbackData = BjuiJson.json("300", "用户不存在", "", "",
                            "", "true", "", "");
                } else {
                    boolean result = this.commissionService.delete(findcommission);
                    if (result) {
                        callbackData = BjuiJson.json("200", "删除成功", "",
                                "", "", "", "", "");
                    } else {
                        callbackData = BjuiJson.json("300", "删除失败", "",
                                "", "", "", "", "");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        out.print(callbackData);
        out.flush();
        out.close();
    }

    public void userCommissionList() throws Exception {
        this.request.setCharacterEncoding("UTF-8");
        String pStr = this.request.getParameter("p");
        int p = 1;
        if (!StringUtils.isEmpty(pStr)) {
            p = Integer.parseInt(pStr);
        }

        HttpSession session = this.request.getSession();
        WxUser loginUser = (WxUser) session.getAttribute("loginUser");
        Integer loginUserId = loginUser.getUserId();
        //Integer loginUserId = 27;
        String Ym = (String) request.getParameter("date");
        System.out.println(Ym);
        if (null == Ym) {
            Date date = new Date();
            String Y = String.format("%tY", date);
            String m = String.format("%tm", date);
            Ym = Y + "-" + m;
        }
        //总支出
        Double allPayMoneyN = commissionService.getAllPayMoney(loginUserId, Ym, CommissionTypeEnum.PAY.getId());
        //总收入
        Double incomeMoneyN = commissionService.getIncomeMoney(loginUserId, Ym, CommissionTypeEnum.INCOME.getId());
        if (null == allPayMoneyN) {
            allPayMoneyN = 0D;
        }
        if (null == incomeMoneyN) {
            incomeMoneyN = 0D;
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        String allPayMoney = df.format(allPayMoneyN);
        String incomeMoney = df.format(incomeMoneyN);

        String countHql = "select count(*) from Commission where deleted=0 and user.id =" + loginUserId;
        countHql = countHql + " and createDate like '" + Ym + "%'";
        String hql = "from Commission where deleted=0 and user.id=" + loginUserId;
        hql = hql + " and createDate like '" + Ym + "%' order by id desc";

        int count = 0;
        count = this.commissionService.getTotalCount(countHql, new Object[0]);
        PageModel pageModel = new PageModel();
        pageModel.setAllCount(count);
        pageModel.setCurrentPage(p);
        List<Commission> ordersList = this.commissionService.list(hql, pageModel.getStart(), pageModel.getPageSize(), new Object[0]);
        for (Commission commission : ordersList) {
            if (null==commission.getRemark()){
                commission.setRemark("");
            }
            if (commission.getType().equals(CommissionTypeEnum.PAY.getId())) {
                commission.setTypeStr(CommissionTypeEnum.PAY.getName());
                commission.setMoneyStr("-" + commission.getMoney());
            }
            if (commission.getType().equals(CommissionTypeEnum.INCOME.getId())) {
                commission.setTypeStr(CommissionTypeEnum.INCOME.getName());
                commission.setMoneyStr("+" + commission.getMoney());
            }
        }
        CommissionListDto commissionListDto = new CommissionListDto();
        commissionListDto.setAllPayMoney(allPayMoney);
        commissionListDto.setIncomeMoney(incomeMoney);
        commissionListDto.setCommissionList(ordersList);
        JSONObject json = new JSONObject();
        if (ordersList.size() == 0) {
            json.put("status", "0");

            json.put("isNextPage", "0");
        } else {
            json.put("status", "1");
            if (ordersList.size() == pageModel.getPageSize()) {
                json.put("isNextPage", "1");
            } else {
                json.put("isNextPage", "0");
            }
            json.put("commissionListDto", commissionListDto);
        }
        PrintWriter out = null;
        try {
            out = this.response.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        out.print(JSON.toJSONString(json, SerializerFeature.WriteMapNullValue));
        out.flush();
        out.close();
    }

    //获取用户佣金
    public void getUserCommission() {
        HttpSession session = this.request.getSession();
        WxUser loginUser = (WxUser) session.getAttribute("loginUser");
        Integer userId = loginUser.getUserId();
        //Integer userId = 27;
        Double commission = userService.getUserCommission(userId);

        JSONObject json = new JSONObject();
        json.put("commission", commission);
        PrintWriter out = null;
        try {
            out = this.response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(json.toJSONString());
        out.flush();
        out.close();
    }

    public Commission getCommission() {
        return this.commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
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
