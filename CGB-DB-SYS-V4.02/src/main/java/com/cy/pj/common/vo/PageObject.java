package com.cy.pj.common.vo;

import java.io.Serializable;
import java.util.List;

import com.cy.pj.sys.entity.SysLog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * VO:
 * 基于此对象在业务层封装数据层获取数据，并进行分页计算，
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class PageObject<T> implements Serializable{
	 private static final long serialVersionUID = -7768848413303590649L;
	 private Integer rowCount;	   //总行数
	 private List<T> records;	   //当前页记录
	 private Integer pageCount;	   //总页数
	 private Integer pageCurrent;  //当前页的页码值
	 private Integer pageSize;     //页面行数
	 
	 //...
	 public PageObject(Integer rowCount, List<T> records, Integer pageCurrent, Integer pageSize) {
		super();
		this.rowCount = rowCount;
		this.records = records;
		this.pageCurrent = pageCurrent;
		this.pageSize = pageSize;
		
		//计算总页数
		//方法1：
		//this.pageCount=rowCount/pageSize;
		//if(rowCount%pageSize!=0)pageCount++;
		//方法2：
		this.pageCount=(rowCount-1)/pageSize+1;
		
		
	 }
	 
}
