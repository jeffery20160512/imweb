package com.isoftstone.app.base.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 手工获取Bean
 * 
 * @author lixin
 * 
 */
public class SpringUtil implements ApplicationContextAware
{

    private static ApplicationContext context = null;

    public void setApplicationContext(ApplicationContext ctx) throws BeansException
    {
        context = ctx;
    }

    public static Object getBean(String beanName)
    {
        return context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> bean)
    {
        return context.getBean(bean);
    }
}
