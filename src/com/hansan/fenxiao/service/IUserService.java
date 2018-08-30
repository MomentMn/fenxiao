package com.hansan.fenxiao.service;

import java.util.List;

import com.hansan.fenxiao.entities.User;

public abstract interface IUserService<T extends User> extends IBaseService<T> {
    public abstract User getUserByName(String paramString);

    public abstract User getUserByPhone(String paramString);

    public abstract User getUserByNameAndPhone(String paramString1, String paramString2);

    public abstract User login(String paramString1, String paramString2);

    public abstract User getUserByNo(String paramString);

    public abstract List<User> levelUserList(String paramString);

    public abstract List<User> levelUserTodayList(String paramString);

    public abstract List<User> levelUserTodayStatusList(String paramString);

    //获取用户佣金余额
    Double getUserCommission(Integer userId);
    
    /**
     * @description:通过用户id获取微信openId
     * @param userId
     * @return
     * @author:衷文涛
     * @createTime:2018年6月6日 上午11:35:14
     */
    public String getOpenIdByUserId(int userId);
}
 