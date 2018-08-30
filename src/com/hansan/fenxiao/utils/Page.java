package com.hansan.fenxiao.utils;

import java.util.ArrayList;
import java.util.List;

public class Page<T> extends QueryParameter {
	private List<T> result = new ArrayList<>();

	private int totalCount = -1;
	private int totalRows = 0;


	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page(int pageSize, boolean autoCount) {
		this.pageSize = pageSize;
		this.autoCount = autoCount;
	}

	public List<T> getResult() {
		return this.result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		if (this.totalCount == -1) {
			return -1;
		}
		int count = this.totalCount / this.pageSize;
		if (this.totalCount % this.pageSize > 0) {
			count++;
		}
		return count;
	}	

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public boolean isHasNext() {
		return this.pageNo + 1 <= getTotalPages();
	}

	public int getNextPage() {
		if (isHasNext()) {
			return this.pageNo + 1;
		}
		return this.pageNo;
	}

	public boolean isHasPre() {
		return this.pageNo - 1 >= 1;
	}

	public int getPrePage() {
		if (isHasPre()) {
			return this.pageNo - 1;
		}
		return this.pageNo;
	}
}