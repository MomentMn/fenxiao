package com.hansan.fenxiao.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/6/4 17:10
 **/
public class CommissionListDto implements Serializable{
    //总支出
    private String allPayMoney;
    //总收入
    private String incomeMoney;
    //账单佣金列表
    private List commissionList = new ArrayList();

    public String getAllPayMoney() {
        return allPayMoney;
    }

    public void setAllPayMoney(String allPayMoney) {
        this.allPayMoney = allPayMoney;
    }

    public String getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(String incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public List getCommissionList() {
        return commissionList;
    }

    public void setCommissionList(List commissionList) {
        this.commissionList = commissionList;
    }
}
