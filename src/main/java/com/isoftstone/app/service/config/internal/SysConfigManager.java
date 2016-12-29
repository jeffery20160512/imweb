/**
 * @(#)SysConfigManager.java 1.0 2013-4-19
 * @Copyright:  Copyright 2000-2013 Isoftstone Tech.Co.Ltd.All Rights Reserved.
 * @Description: MPR中国注册中心管理系统配置项维护实现类
 * Modification History:
 * Date:        2013-4-19
 * Author:      hlchend 30262
 * Version:     MPRSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    xxx
 * Review Date: 2013-4-19
 */
package com.isoftstone.app.service.config.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.isoftstone.app.base.common.Result;
import com.isoftstone.app.base.constants.CommonConstants;
import com.isoftstone.app.entity.config.SysConfig;
import com.isoftstone.app.service.config.ISysConfigManager;


/**
 * @author husong
 */
@Service
public class SysConfigManager implements ISysConfigManager
{
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 内容类型Mapper映射文件命名空间名称
     */
    private final String MAPPER_NAME_SPACE ="com.isoftstone.app.entity.sysConfig.configDao.";
    
    /**
     * SqlSession对象
     */
    @Resource
    private SqlSession sqlSession;
    
    /**
     * 无参的构造方法
     */
    public SysConfigManager(){
    	
    }
    
    /**
     * 方法描述：获取配置的应用服务器地址
     * @return 
     */
    public String getApplicationUrl()
    {
        SysConfig ipInfo =
            (SysConfig) sqlSession.selectOne(MAPPER_NAME_SPACE
                + "configByKeyName", CommonConstants.APPLICATION_IP);
        SysConfig portInfo =
            (SysConfig) sqlSession.selectOne(MAPPER_NAME_SPACE
                + "configByKeyName", CommonConstants.APPLICATION_PORT);
        StringBuffer sb = new StringBuffer();
        if (ipInfo != null)
        {
            sb.append("http://").append(ipInfo.getKeyValue1());
        }
        if (portInfo != null)
        {
            if (portInfo.getKeyValue1() != null
                && !"".equals(portInfo.getKeyValue1().trim()))
            {
                sb.append(":");
            }
            
            sb.append(portInfo.getKeyValue1().trim());
        }
        logger.info("查询成功");
        return sb.toString();
    }
    
    /**
     * 方法描述：获取配置信息
     * @param key 配置key
     * @return Object 配置值
     */
    @Override
    public Object getValue(String key)
    {
        String result = null;
        SysConfig config =
            (SysConfig) sqlSession.selectOne(MAPPER_NAME_SPACE
                + "configByKeyName", key);
        if (config != null)
        {
            if ("1".equals(config.getKeyType()))
            {
                result = config.getKeyValue1();
            }
            else if ("2".equals(config.getKeyType()))
            {
                result = config.getKeyValue2();
            }
            else if ("3".equals(config.getKeyType()))
            {
                result = config.getKeyValue3();
            }
            else if ("4".equals(config.getKeyType()))
            {
                result = config.getKeyValue4();
            }
        }
        return result;
    }
    
    /**
     * 分页查询配置项列表
     * @param config 配置项对象
     * @return 返回List<SysConfig>对象
     * @throws Exception 异常处理
     */
    @Override
    public List<SysConfig> querySysConfig(SysConfig config)
        throws Exception
    {
    	 List<SysConfig> list = null;
    	 Integer total = (Integer) sqlSession.selectOne(MAPPER_NAME_SPACE + "total", config);
         if (total.intValue() > 0)
         {
        	config.setTotalRows(total.longValue());
            list = sqlSession.selectList(MAPPER_NAME_SPACE + "configList", config);
         }
        return list;
    }
    
    /**
     * 修改系统配置项信息
     * @param config 配置项修改参数
     * @return 返回 0/1 成功或失败
     * @throws Exception 异常处理
     */
    @Override
    public int updateSysConfig(final SysConfig config) throws Exception
    {
        int result = 0;
        if (null != config)
        {
            result =
                sqlSession.update(MAPPER_NAME_SPACE + "updateSysConfig", config);
        }
        return result;
    }
    
    /**
     * 根据名称修改系统配置项信息
     * @param config 配置项修改参数
     * @return 返回 0/1 成功或失败
     * @throws Exception 异常处理
     */
    @Override
    public Result updateSysConfigByName(String configName,int count) throws Exception
    {
    	Result result = null;
        if (null != configName)
        {
        	Map<String, Object> premMap = new HashMap<String, Object>();
        	premMap.put("configName", configName);
        	premMap.put("count", count);
            sqlSession.update(MAPPER_NAME_SPACE + "updateSysConfigByName", premMap);
            result = new Result(true, "修改成功", "修改配置成功");
        }
        return result;
    }
    
    /**
     * 根据配置项ID查询相关信息
     * @param id 配置项ID
     * @return 返回SysConfig对象
     * @throws Exception 异常处理
     */
    @Override
    public SysConfig querySysConfigById(String id) throws Exception
    {
        SysConfig config = null;
        if (null != id)
        {
            config =
                (SysConfig) sqlSession.selectOne(MAPPER_NAME_SPACE
                    + "configById", id);
        }
        return config;
    }

	@Override
	public SysConfig querySysConfigByKeyName(String keyName) throws Exception {
		
		 SysConfig config =
		            (SysConfig) sqlSession.selectOne(MAPPER_NAME_SPACE
		                + "configByKeyName", keyName);
		 return config;
	}
}
