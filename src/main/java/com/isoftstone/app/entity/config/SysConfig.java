/**
 * @(#)SysConfig.java 1.0 2013-4-19
 * @Copyright:  Copyright 2000-2013 Isoftstone Tech.Co.Ltd.All Rights Reserved.
 * @Description: 系统配置项实体类
 * Modification History:
 * Date:        2013-4-19
 * Author:      hlchend 30262
 * Version:     MPRSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    xxx
 * Review Date: 2013-4-19
 */
package com.isoftstone.app.entity.config;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.isoftstone.app.base.common.Pagination;



/**
 * 系统配置项实体类
 * @author husong
 */
public class SysConfig extends Pagination
{
	private static final long serialVersionUID = 9157159022804438279L;

	/**
     * 配置编号
     */
    private String keyId;
    
    /**
     * 工作人员编号
     */
    private int empId;
    
    /**
     * 配置名称
     */
    private String keyName;
    
    /**
     * 配置描述
     */
    private String keyDesc;
    
    /**
     * 配置值（1:字符串 2：数字 3：时间 4：大字段数据）
     */
    private String keyType;
    
    /**
     * 扩展字段1
     */
    private String keyValue1;
    
    /**
     * 扩展字段2
     */
    private String keyValue2;
    
    /**
     * 扩展字段3
     */
    private String keyValue3;
    
    /**
     * 扩展字段4
     */
    private String keyValue4;
    
    /**
     * blog类型的字段
     */
    private byte[] value4;
    
    /**
     * 录入时间
     */
    private String edtTime;
    
    /**
     * 工作人员名称
     */
    private String empName;
    
    /**
     * 记录开始
     */
    private int startNo;
    /**
     * id
     */
    private String id;
    /**
     * text
     */
    private String text;
	/**
	 * 无参的构造方法
	 */
    public SysConfig(){
    	
    }
    /**
     * 获取 keyId
     * @return keyId
     */
    public String getKeyId()
    {
        return keyId;
    }
    /**
     * 赋值 keyId
     * @param keyId
     */
    public void setKeyId(String keyId)
    {
        this.keyId = keyId;
    }
    /**
     * 获取 empId
     * @return empId
     */
    public int getEmpId()
    {
        return empId;
    }
    /**
     * 赋值 empId
     * @param empId
     */
    public void setEmpId(int empId)
    {
        this.empId = empId;
    }
    /**
     * 获取 keyName
     * @return keyName
     */
    public String getKeyName()
    {
        return keyName;
    }
    /**
     * 赋值 keyName
     * @param keyName
     */
    public void setKeyName(String keyName)
    {
        this.keyName = keyName;
    }
    /**
     * 获取 keyDesc
     * @return keyDesc
     */
    public String getKeyDesc()
    {
        return keyDesc;
    }
    /**
     * 赋值 keyDesc
     * @param keyDesc
     */
    public void setKeyDesc(String keyDesc)
    {
        this.keyDesc = keyDesc;
    }
    /**
     * 获取 keyType
     * @return keyType
     */
    public String getKeyType()
    {
        return keyType;
    }
    /**
     * 赋值 keyType
     * @param keyType
     */
    public void setKeyType(String keyType)
    {
        this.keyType = keyType;
    }
    /**
     * 获取 keyValue1
     * @return keyValue1
     */
    public String getKeyValue1()
    {
        return keyValue1;
    }
    /**
     * 赋值 keyValue1
     * @param keyValue1
     */
    public void setKeyValue1(String keyValue1)
    {
        this.keyValue1 = keyValue1;
    }
    /**
     * 获取 getKeyValue2
     * @return getKeyValue2
     */
    public String getKeyValue2()
    {
        return keyValue2;
    }
    /**
     * 赋值 getKeyValue2
     * @param getKeyValue2
     */
    public void setKeyValue2(String keyValue2)
    {
        this.keyValue2 = keyValue2;
    }
    /**
     * 获取 keyValue3
     * @return keyValue3
     */
    public String getKeyValue3()
    {
        return keyValue3;
    }
    /**
     * 赋值 keyValue3
     * @param keyValue3
     */
    public void setKeyValue3(String keyValue3)
    {
        this.keyValue3 = keyValue3;
    }
    /**
     * 获取 keyValue4
     * @return keyValue4
     */
    public String getKeyValue4()
    {
        return keyValue4;
    }
    /**
     * 赋值 keyValue4
     * @param keyValue4
     */
    public void setKeyValue4(String keyValue4)
    {
        this.keyValue4 = keyValue4;
    }
    /**
     * 获取 edtTime
     * @return edtTime
     */
    public String getEdtTime()
    {
        return edtTime;
    }
    /**
     * 赋值 edtTime
     * @param edtTime
     */
    public void setEdtTime(String edtTime)
    {
        this.edtTime = edtTime;
    }
    /**
     * 获取 empName
     * @return empName
     */
    public String getEmpName()
    {
        return empName;
    }
    /**
     * 赋值 empName
     * @param empName
     */
    public void setEmpName(String empName)
    {
        this.empName = empName;
    }
    /**
     * 获取 startNo
     * @return startNo
     */
    public int getStartNo()
    {
        return startNo;
    }
    /**
     * 赋值 startNo
     * @param startNo
     */
    public void setStartNo(int startNo)
    {
        this.startNo = startNo;
    }
    /**
     * 获取 id
     * @return id
     */
    public String getId()
    {
        return id;
    }
    /**
     * 赋值 id
     * @param id
     */
    public void setId(String id)
    {
        this.id = id;
    }
    /**
     * 获取 text
     * @return text
     */
    public String getText()
    {
        return text;
    }
    /**
     * 赋值 text
     * @param text
     */
    public void setText(String text)
    {
        this.text = text;
    }
    /**
     * 获取 value4
     * @return value4
     */
    public byte[] getValue4()
    {
        if (null == value4)
        {
            return null;
        }
        else
        {
            return value4.clone();
        }
    }
    /**
     * 赋值 value4
     * @param value4
     */
    public void setValue4(byte[] value4)
    {
        if (null == value4)
        {
            this.value4 = null;
        }
        else
        {
        	this.value4=Arrays.copyOf(value4, value4.length);
            this.value4 = value4.clone();
            if (value4.length > 0)
            {
                try
                {
                    keyValue4 = new String(value4, "UTF-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((keyDesc == null) ? 0 : keyDesc.hashCode());
        result = prime * result + ((keyName == null) ? 0 : keyName.hashCode());
        
        return result;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SysConfig other = (SysConfig) obj;
        
        if (keyDesc == null)
        {
            if (other.keyDesc != null)
                return false;
        }
        else if (!keyDesc.equals(other.keyDesc))
            return false;
        if (keyName == null)
        {
            if (other.keyName != null)
                return false;
        }
        else if (!keyName.equals(other.keyName))
            return false;
		return false;
        
    }
    
}
