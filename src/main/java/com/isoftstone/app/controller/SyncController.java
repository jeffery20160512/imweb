package com.isoftstone.app.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.app.entity.OfficeRequest;
import com.isoftstone.app.entity.ResponseEntity;
import com.isoftstone.app.entity.UserRequest;
import com.isoftstone.app.service.sys.ISyncService;

@Controller
@RequestMapping("/openapi")
public class SyncController {

    private static final Logger LOG = LoggerFactory.getLogger(SyncController.class);
	
	@Resource
	private ISyncService syncService;
	
	/**
	 * 用户
	 */
	@RequestMapping("/saveUser.json")
	public @ResponseBody ResponseEntity saveUser(@RequestBody UserRequest userRequest)
	{
		return syncService.saveUser(userRequest);
	}
	
	/**
	 * 组织
	 */
	@RequestMapping("/saveOffice.json")
	public @ResponseBody ResponseEntity saveOffice(@RequestBody OfficeRequest officeRequest)
	{
		return syncService.saveOffice(officeRequest);
	}
}
