package com.isoftstone.app.base.common;

import java.util.List;
/**
 *查询结果实体类
 * @author hs
 *
 * @param <T>
 */
public class QueryResult<T> {

	private long total;
	private List<T> data;
	
	public QueryResult(long total, List<T> data) {
		this.total = total;
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public List<T> getData() {
		return data;
	}
	
}
