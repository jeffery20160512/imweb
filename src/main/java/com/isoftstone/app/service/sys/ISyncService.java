package com.isoftstone.app.service.sys;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isoftstone.app.entity.OfficeRequest;
import com.isoftstone.app.entity.ResponseEntity;
import com.isoftstone.app.entity.User;
import com.isoftstone.app.entity.UserRequest;

public interface ISyncService {

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 * @throws Exception
	 */
	User getUserByName(String username) throws Exception;
	
	User getUserById(long id) throws Exception;

	ResponseEntity saveUser(UserRequest userRequest);

	ResponseEntity saveOffice(OfficeRequest officeRequest);
}
