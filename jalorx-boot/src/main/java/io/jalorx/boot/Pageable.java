package io.jalorx.boot;

import java.util.Collection;

import io.micronaut.data.model.Page;

/**
 * 分页数据对象
 * 
 * @author chenb
 * @param <T> 业务对象类型
 */
public class Pageable<T> implements POJO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -410571025796786344L;

	/**
	 * 页大小
	 */
	private int pageSize;

	/**
	 * 当前页
	 */
	private int page; //

	/**
	 * 满足条件的业务对象集合
	 */
	private Collection<T> data; //

	/**
	 * 满足条件的对象数量
	 */
	private long length; //

	public Pageable() {}
	
	
	private Pageable(Page<T> page) {
		this.page     = page.getPageNumber();
		this.pageSize = page.getSize();
		this.length   = page.getTotalSize();
		this.data     = page.getContent();
	}

	public Pageable(int page, int pageSize, int length, Collection<T> data) {
		this.page     = page;
		this.pageSize = pageSize;
		this.length   = length;
		this.data     = data;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the data
	 */
	public Collection<T> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Collection<T> data) {
		this.data = data;
	}

	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	
	public static <T> Pageable<T> of(Page<T> page) {
		return new Pageable<T>(page);
	}

}
