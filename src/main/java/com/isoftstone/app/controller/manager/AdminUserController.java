package com.isoftstone.app.controller.manager;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isoftstone.app.base.common.Result;
import com.isoftstone.app.base.constants.CommonConstants;
import com.isoftstone.app.base.utils.verifyCodeUtil;
import com.isoftstone.app.entity.User;
import com.isoftstone.app.service.user.IUserService;

/**
 * 后台管理员控制类
 * @author husong
 *
 */
@Controller
@RequestMapping("/adminUser")
public class AdminUserController 
{

	private final Logger loger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IUserService userService;
	
	/**
	 * 管理员登陆
	 * @param request
	 * @param username
	 * @param password
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login.json")
    public Result doLogin(HttpServletRequest request, String username, String password,String code) throws Exception {
		Result result = null;
		try{
			//校验用户名是否为空
    		if(StringUtils.isEmpty(username)) {
    			result = new Result(false, "登陆失败", "用户名不能为空");
    			return result;
        	}
    		//校验密码是否为空
        	if(StringUtils.isEmpty(password)) {
        		result = new Result(false, "登陆失败", "密码不能为空");
        		return result;
        	}
        	//校验验证码是否为空
        	if(StringUtils.isEmpty(code)) {
        		result = new Result(false, "登陆失败", "验证码不能为空");
        		return result;
        	}
        	//根据用户名查找是否存在
        	User user = null;
        	try{
        		user = userService.getUserByName(username);
        	}catch (Exception e) {
				e.printStackTrace();
				result = new Result(false, "登陆失败", "系统异常：RPC异常");
				return result;
			}
    		if(user == null) {
    			result = new Result(false, "登陆失败", "帐号不存在");
    			return result;
    		}
    		
    		//判断密码是否正确
    		if(!password.equals(user.getPassword())) {
    			result = new Result(false, "登陆失败", "密码错误");
    			return result;
    		}
    		//判断验证码是否正确
    		String loginCode = (String) request.getSession().getAttribute(CommonConstants.IDENTITY_CODE);
    		if(!code.equalsIgnoreCase(loginCode)) {
    			result = new Result(false, "登陆失败", "验证码错误");
    			return result;
    		}
    		
    		//将帐号信息和权限信息放到session中
    		request.getSession().setAttribute(CommonConstants.SESSION_USER, user);
    		result = new Result(true, "登陆成功", "用户登陆成功");
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		result = new Result(false, "登陆异常", "异常 信息"+ex.getMessage());
    	}
		return result;
    }
	
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserInfo.json")
    public User getUserInfo(HttpServletRequest request) throws Exception {
		User user = null;
		//获取用户信息
		try{
			user = (User)request.getSession().getAttribute(CommonConstants.SESSION_USER);
		}catch (Exception e) {
			loger.error("获取帐号信息异常,session过期："+e.getMessage());
			e.printStackTrace();
		}
    	return user;
    }
	
	/**
	 * 退出系统
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout.json")
    public Result logout(HttpServletRequest request) throws Exception {
		Result result = null;
    	try {
			request.getSession().invalidate();
			result = new Result(true, "退出成功", "用户退出成功");
		} catch (Exception e) {
			result = new Result(false, "退出异常", "异常信息"+e.getMessage());
		}
		return result;
    }
	
	/**
     * 生成验证码
     * 
     * @param response
     * @throws Exception
     */
    @RequestMapping("/genVerifyCode.json")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	// 禁止图像缓存
    	response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        
        Map<String, BufferedImage> imgMap = verifyCodeUtil.createImage();
        String code = null;
        BufferedImage bufferImag = null;
        Set<String> keys = imgMap.keySet();
        for (String key : keys)
        {
            code = key;
            bufferImag = imgMap.get(key);
        }
        request.getSession().setAttribute(CommonConstants.IDENTITY_CODE, code);
        response.setContentType("image/jpeg");
        ImageIO.write(bufferImag, "JPEG", response.getOutputStream());
    }
	
}
