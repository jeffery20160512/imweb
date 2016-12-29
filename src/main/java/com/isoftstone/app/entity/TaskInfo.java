package com.isoftstone.app.entity;

public class TaskInfo {
	private String procInsId;
	private String title;
	private String applyUserName;
	private String assignee;
	private String taskName;
	private String url;
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
