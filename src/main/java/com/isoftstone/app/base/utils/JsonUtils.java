package com.isoftstone.app.base.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;


import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;


/**
 * Json工具类
 * Author:  hs
 * 
 */
public final class JsonUtils
{
    private static final Logger logger =
        LoggerFactory.getLogger(JsonUtils.class);
    
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    static
    {
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    /**
     * 将对象转换成json
     * @param value
     * @return
     */
    public static String toJson(Object value)
    {
        try
        {
            return objectMapper.writeValueAsString(value);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
        return null;
    }
    
    /**
     * 将对象转换成json，可以设置contentType
     * @param response
     * @param contentType
     * @param value
     */
    public static void toJson(HttpServletResponse response, String contentType,
        Object value)
    {
        Assert.notNull(response);
        Assert.hasText(contentType);
        try
        {
            response.setContentType(contentType);
            objectMapper.writeValue(response.getWriter(), value);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
    }
    
    /**
     * 将对象转换成json字符串，放到response中
     * @param response
     * @param value
     */
    public static void toJson(HttpServletResponse response, Object value)
    {
        Assert.notNull(response);
        try
        {
            // 设置编码格式
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            
            objectMapper.writeValue(response.getWriter(), value);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
    }
    
    /**
     * 将map转换成json字符串
     * @param value
     */
    public static String mapToJson(Map<String, Object> map)
    {
    	String json = "";
        try
        {
			// convert map to JSON string(不带格式转换)
			//json = objectMapper.writeValueAsString(map);
			//System.out.println(json);
        	//(带格式转换)
			json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
			// pretty print
			System.out.println(json);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
		return json;
    }
    
    /**
     * 将json字符串转换成对象
     * @param json
     * @param valueType xxx.class
     * @return
     */
    public static <T> T toObject(String json, Class<T> valueType)
    {
        Assert.hasText(json);
        Assert.notNull(valueType);
        try
        {
            return objectMapper.readValue(json, valueType);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
        return null;
    }
    
    /**
     * 字符串转换成较复杂的对象，如泛型对象 (可转换成list map)
     * @param json
     * @param typeReference
     * @return
     */
    public static <T> T toObject(String json, TypeReference<?> typeReference)
    {
        Assert.hasText(json);
        Assert.notNull(typeReference);
        try
        {
            return objectMapper.readValue(json, typeReference);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
        return null;
    }
    
    public static <T> T toObject(String json, JavaType javaType)
    {
        Assert.hasText(json);
        Assert.notNull(javaType);
        try
        {
            return objectMapper.readValue(json, javaType);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
        return null;
    }
    
    public static void main(String[] args) throws Exception {
    	
    	
	}
}
