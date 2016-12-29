package com.isoftstone.app.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.app.base.utils.JsonUtils;
import com.isoftstone.app.entity.RegisterUserRequest;
import com.isoftstone.app.entity.ResponseEntity;
import com.isoftstone.app.entity.TaskInfo;
import com.isoftstone.app.entity.User;
import com.isoftstone.app.service.user.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Resource
	private IUserService userService;
	
	/**
	 * 注册用户
	 * @param id
	 */
	@RequestMapping("/registerUser.json")
	public @ResponseBody ResponseEntity registerUser(@RequestBody RegisterUserRequest registerUserRequest)
	{
		return userService.registerUser(registerUserRequest);
	}
	
	/**
	 * 注册用户
	 * @param id
	 */
	@RequestMapping("/getTest.json")
	public @ResponseBody TaskInfo[] getTest()
	{
//		TaskInfo[] tasks= new TaskInfo[2];
//		TaskInfo taskInfo1 = new TaskInfo();
//		 taskInfo1.setProcInsId(("procInsId"));
//		 taskInfo1.setTitle(("title"));
//		 taskInfo1.setApplyUserName(("applyUserName"));
//		 taskInfo1.setAssignee(("assignee"));
//		 taskInfo1.setTaskName(("taskName"));
//		 taskInfo1.setUrl(("url"));
//		 
//		 TaskInfo taskInfo2 = new TaskInfo();
//		 taskInfo2.setProcInsId(("procInsId2"));
//		 taskInfo2.setTitle(("title2"));
//		 taskInfo2.setApplyUserName(("applyUserName2"));
//		 taskInfo2.setAssignee(("assignee2"));
//		 taskInfo2.setTaskName(("taskName2"));
//		 taskInfo2.setUrl(("url2"));
//		 tasks[0] = taskInfo1;
//		 tasks[1] = taskInfo2;
//		return tasks;
		
		return new TaskInfo[]{};
	}
	
	/**
	 * 获取代办事项
	 * @param id
	 */
	@RequestMapping("/getTaskList.json")
	public @ResponseBody List<TaskInfo> getTaskList(@RequestBody RegisterUserRequest registerUserRequest)
	{
		return userService.getTaskList(registerUserRequest);
	}
	
	/**
	 * 用户
	 * @param id
	 */
	@RequestMapping("/saveUser.json")
	public void saveUser()
	{
		userService.saveUser();
	}
	
	/**
	 * 根据ID查询用户
	 * @param id
	 */
	@RequestMapping("/getUserById.json")
	public void getUserById(long id,HttpServletResponse response)
	{
		User user = null;
		try {
			user = userService.getUserById(id);
		} catch (Exception e) {
			LOG.error("查询异常");
			e.printStackTrace();
		}
		JsonUtils.toJson(response, user);
	}
	
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		
		String k = "key1";
		if(map.containsKey(k)){
			System.out.println("赋值前"+map.get(k));
			map.put(k, k);
			System.out.println("赋值后"+map.get(k));
		}
	}
}
