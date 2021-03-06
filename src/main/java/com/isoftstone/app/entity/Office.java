/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.isoftstone.app.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * 机构Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Office {

	private static final long serialVersionUID = 1L;
	private String id;
	private String parentId;	// 父级编号
	private String parentIds; // 所有父级编号
	private String code; 	// 机构编码
	private String name; 	// 机构名称
	private Integer sort;		// 排序
	private String type; 	// 机构类型
	private String useable;//是否可用
	private String primaryPerson;//主负责人
	private String deputyPerson;//副负责人
	private Date createDate;
	private Date updateDate;
	private String remarks;
	private int delFlag;
	
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUseable() {
		return useable;
	}
	public void setUseable(String useable) {
		this.useable = useable;
	}
	public String getPrimaryPerson() {
		return primaryPerson;
	}
	public void setPrimaryPerson(String primaryPerson) {
		this.primaryPerson = primaryPerson;
	}
	public String getDeputyPerson() {
		return deputyPerson;
	}
	public void setDeputyPerson(String deputyPerson) {
		this.deputyPerson = deputyPerson;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}