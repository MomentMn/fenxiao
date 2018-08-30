package com.hansan.fenxiao.service;


public abstract interface IQrCodeService {
	String getQrCodeUrl(int userId,int productID,int...ids)throws Exception;
}
