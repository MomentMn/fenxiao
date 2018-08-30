package com.hansan.fenxiao.constants;

public enum CommissionTypeEnum
{
	withdrawing(0, "扣款"),
	admission(1, "入款"),
	;

	private int code;
	private String desc;

	private CommissionTypeEnum(int code, String desc)
	{
		this.code = code;
		this.desc = desc;
	}

	public int getCode()
	{
		return code;
	}

	public String getDesc()
	{
		return desc;
	}
}
