package com.hj.oa.bean;

import java.util.List;

/**
 * 
 * @ClassName:  Page   
 * @Description: 分页帮助类
 * @author: wqfang
 * @date:   2018年9月20日 下午4:05:10   
 *
 */
public class Page<T> {
	
	public final static Integer PAGE_SIZE_FIFTEEN = 14;
	public final static Integer PAGE_SIZE_TEN = 9;
	public final static Integer PAGE_SIZE_TWENTY = 19;
	
	// 指定的或是页面参数
	private int currentPage;// 当前页
	private int pageSize;// 每页显示多少条

	// 查询数据库
	private int recordCount;// 总记录数

	// 计算
	private int pageCount;// 总页数
	private int beginPageIndex;// 页码列表的开始索引
	private int endPageIndex;// 页码列表的结束索引
	
	private List<T> list;

	/**
	 * 
	 * @Title:  Page   
	 * @Description:   初始化分页参数
	 * @param:  当前页数
	 * @param:  每页显示结果数目
	 * @param:  结果总记录数目
	 * @throws
	 */
	public Page(int currentPage, int pageSize, int recordCount) {
		pageSize++;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		// 计算总页码
		pageCount = (recordCount + pageSize - 1) / pageSize;
		// 计算beginPageIndex 和 endPageIndex
		beginPageIndex = (currentPage - 1) * pageSize;
		if (recordCount < pageSize * currentPage) {
			endPageIndex = recordCount - 1;
		} else {
			endPageIndex = pageSize * currentPage - 1;
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
