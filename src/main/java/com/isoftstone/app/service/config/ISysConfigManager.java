/**
 * @(#)ISysConfigManager.java 1.0 2013-4-19
 * @Copyright:  Copyright 2000-2013 Isoftstone Tech.Co.Ltd.All Rights Reserved.
 * @Description: 系统配置项维护接口
 * Modification History:
 * Date:        2013-4-19
 * Author:      husong 30262
 * Version:     MPRSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer: xx
 * Review Date: 2013-4-19
 */
package com.isoftstone.app.service.config;

import java.util.List;

import com.isoftstone.app.base.common.Result;
import com.isoftstone.app.entity.config.SysConfig;


/**
 * @author husong
 */
public interface ISysConfigManager
{
    /**
     * 方法描述：获取配置的应用服务器地址
     * @return 
     */
    String getApplicationUrl();
    
    /**
     * 方法描述：获取配置信息
     * @param key 配置key
     * @return Object 配置值
     */
    Object getValue(String key);
    
    /**
     * 查询配置项
     * @param config 配置项对象
     * @return 返回List<SysConfig>对象
     * @throws Exception 异常处理
     */
    List<SysConfig> querySysConfig(SysConfig config) throws Exception;
    
    /**
     * 修改系统配置项信息
     * @param config 配置项修改参数
     * @return 返回 0/1 成功或失败
     * @throws Exception 异常处理
     */
    int updateSysConfig(SysConfig config) throws Exception;
    /**
     * 根据名称修改系统配置项信息
     * @param config 配置项修改参数
     * @return 返回 0/1 成功或失败
     * @throws Exception 异常处理
     */
    Result updateSysConfigByName(String configName,int count) throws Exception;
    
    /**
     * 根据配置项ID查询相关信息
     * @param id 配置项ID
     * @return 返回SysConfig对象
     * @throws Exception 异常处理
     */
    SysConfig querySysConfigById(String id) throws Exception;
    
    /**
     * 根据配置项keyName查询相关信息
     * @param KeyName 配置项KeyName
     * @return 返回SysConfig对象
     * @throws Exception 异常处理
     */
    SysConfig querySysConfigByKeyName(String keyName) throws Exception;
}
