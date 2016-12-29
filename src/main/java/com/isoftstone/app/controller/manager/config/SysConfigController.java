/**
 * @(#)SysConfigController.java 1.0 2015-1-30
 * @Copyright:  Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-1-30
 * Author:      husong 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.isoftstone.app.controller.manager.config;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isoftstone.app.base.common.BaseController;
import com.isoftstone.app.base.common.QueryResult;
import com.isoftstone.app.base.common.Result;
import com.isoftstone.app.base.constants.CommonConstants;
import com.isoftstone.app.base.utils.JsonUtils;
import com.isoftstone.app.entity.User;
import com.isoftstone.app.entity.config.SysConfig;
import com.isoftstone.app.service.config.ISysConfigManager;


/**
 * 系统配置项控制器
 * Copyright:   Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-1-30 下午3:43:46
 * Author:      husong 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
@Controller
@RequestMapping("/sysConfig")
public class SysConfigController extends BaseController
{
	/**
	 * 日志对象
	 */
    private final Logger loger = LoggerFactory.getLogger(getClass());
    /**
	 * sysConfigManager
	 */
    @Resource
    private ISysConfigManager sysConfigManager;
    /**
	 * 无参的构造方法
	 */
    public SysConfigController(){
    	
    }
    /**
     * 查询列表
     * @param config
     * @param request
     * @param response
     */
    @RequestMapping("/list.json")
    public void list(SysConfig config, HttpServletRequest request,
        HttpServletResponse response)
    {
        QueryResult<SysConfig> queryResult = null;
        try
        {
            List<SysConfig> list = sysConfigManager.querySysConfig(config);
            queryResult = new QueryResult<SysConfig>((int) config.getTotalRows(), list);
        }
        catch (Exception e)
        {
            loger.error("查询系统配置项列表出错啦：" + e.getMessage(), e);
        }
        JsonUtils.toJson(response, queryResult);
    }
    
    /**
     * 根据ID，获取系统配置项
     * @param id 主键ID
     * @param request
     * @param response
     */
    @RequestMapping("/get.json")
    public void get(String id, HttpServletRequest request,
        HttpServletResponse response)
    {
        SysConfig sysConfig = null;
        try
        {
            if (StringUtils.isNotBlank(id))
            {
                sysConfig = sysConfigManager.querySysConfigById(id);
            }
        }
        catch (Exception e)
        {
            loger.error("根据ID，获取系统配置项出错啦：" + e.getMessage(), e);
        }
        JsonUtils.toJson(response, sysConfig);
    }
    
    /**
     * 修改配置项
     * @param sysConfig
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/update.json")
    public Result update(SysConfig sysConfig, HttpServletRequest request,
        HttpServletResponse response)
    {
        Result rst = null;
        try
        {
            if (sysConfig == null || StringUtils.isBlank(sysConfig.getKeyId()))
            {
                loger.error(sysConfig == null
                    ? "sysConfig is null." : "params error: "
                        + sysConfig.toString());
                rst = new Result(false, "修改失败", "参数为空");
            }
            else
            {
                User account = (User)request.getSession().getAttribute(CommonConstants.SESSION_USER);
                sysConfig.setEmpId(account.getId());
                int rows = sysConfigManager.updateSysConfig(sysConfig);
                if (rows > 0)
                {
                    rst = new Result(true, "修改成功", null);
                }
                else
                {
                    rst = new Result(false, "修改失败", "入库失败");
                }
            }
        }
        catch (Exception e)
        {
            loger.error("修改系统配置项出错啦：" + e.getMessage(), e);
            rst = new Result(false, "修改失败", "系统异常");
        }
        return rst;
    }
    
    /**
     * 根据key读取配置项值
     * @param keyName key名称
     * @param response
     */
    @RequestMapping("/getConfigByKeyName.json")
    public void getConfigByKeyName(String keyName, HttpServletResponse response)
    {
        String keyValue = "";
        try
        {
            //读取配置项 key->value
            keyValue = (String) sysConfigManager.getValue(keyName); //由平台指定分配 可配置
            
        }
        catch (Exception e)
        {
            loger.error("读取系统配置项出错啦：" + e.getMessage(), e);
        }
        finally
        {
            JsonUtils.toJson(response, keyValue);
        }
    }
    
    
    /**
     * 根据key读取配置对象信息
     * @param keyName key名称
     * @param response
     */
    @RequestMapping("/getSysConfigByKeyName.json")
    public void getSysConfigByKeyName(String keyName, HttpServletResponse response)
    {
    	SysConfig config = null;
        try
        {
        	config = sysConfigManager.querySysConfigByKeyName(keyName); //由平台指定分配 可配置
            
        }
        catch (Exception e)
        {
            loger.error("读取系统配置项出错啦：" + e.getMessage(), e);
        }
        finally
        {
            JsonUtils.toJson(response, config);
        }
    }
}
