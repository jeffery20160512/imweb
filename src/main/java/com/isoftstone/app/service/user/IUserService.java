package com.isoftstone.app.service.user;

import java.util.List;

import com.isoftstone.app.entity.RegisterUserRequest;
import com.isoftstone.app.entity.ResponseEntity;
import com.isoftstone.app.entity.TaskInfo;
import com.isoftstone.app.entity.User;

public interface IUserService {

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 * @throws Exception
	 */
	User getUserByName(String username) throws Exception;
	
	User getUserById(long id) throws Exception;

	void saveUser();

	ResponseEntity registerUser(RegisterUserRequest registerUserRequest);

	List<TaskInfo> getTaskList(RegisterUserRequest registerUserRequest);
}
