package com.hansan.fenxiao.utils;


import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询参数
 *
 * @author liupj
 * @date 20160123
 */
public class QueryParameter  implements Serializable {
	public static final String ASC = "asc";
	public static final String DESC = "desc";

    //当前第几页，默认是第一页
	protected int pageNo = 1;

    //每页记录数，默认10条
	protected int pageSize = 10;

    //是否自动计算总记录数，默认true
	protected boolean autoCount = true;

    //排序字段
    private List<Sort> sortList = new ArrayList<Sort>();

    public List<Sort> getSortList() {
        return sortList;
    }

    public void setSortList(List<Sort> sortList) {
        this.sortList = sortList;
    }

    //添加排序字段，可级联添加
    public QueryParameter addSort(String fieldName, String ascOrDesc) {
        sortList.add(new Sort(fieldName, ascOrDesc));

        return this;
    }

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isPageSizeSetted() {
		return this.pageSize > -1;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getFirst() {
		if ((this.pageNo < 1) || (this.pageSize < 1)) {
			return 0;
		}
		return (this.pageNo - 1) * this.pageSize;
	}

	public boolean isFirstSetted() {
		return (this.pageNo > 0) && (this.pageSize > 0);
	}


	public boolean isOrderBySetted() {
		return !sortList.isEmpty();
	}



	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {

		this.autoCount = autoCount;
	}


     public static class Sort {
        private String fieldName;
        private String ascOrDesc;

        public Sort(String fieldName, String ascOrDesc) {
            this.fieldName = fieldName;
            this.ascOrDesc = ascOrDesc;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getAscOrDesc() {
            return ascOrDesc;
        }

        public void setAscOrDesc(String ascOrDesc) {
            this.ascOrDesc = ascOrDesc;
        }
    }
}