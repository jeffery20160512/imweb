package com.isoftstone.app.service.user.internal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isoftstone.app.entity.RegisterUserRequest;
import com.isoftstone.app.entity.ResponseEntity;
import com.isoftstone.app.entity.TaskInfo;
import com.isoftstone.app.entity.User;
import com.isoftstone.app.service.user.IUserService;
import com.isoftstone.app.utils.StringUtils;

@Service
public class UserService implements IUserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	//命名空间
	private String namespance = "com.isoftstone.app.userDao.";
	
	ObjectMapper objectMapper = new ObjectMapper();
	@Value("${oa.todoUrl}")
	private String oaTodoUrl;
	@Resource
    private SqlSession sqlSession;
	
	@Override
	public User getUserById(long id) {
		User user = sqlSession.selectOne(namespance+"getUserById", id);
		return user;
	}
	
	@Override
	public User getUserByName(String username) throws Exception {
		User user = sqlSession.selectOne(namespance+"getUserByName", username);
		return user;
	}
	
	@Override
	public void saveUser() {
		User user1 = new User();
		user1.setLoginName("hh");
		user1.setPassword("123456");
		sqlSession.insert(namespance+"saveUser", user1);
		System.out.println("===============");
		
		/*User user2 = new User();
		user2.setUsername("hasdfasdfasdfsdfasdfasdfasdfasdffghdfghdfghdfgh");
		user2.setPassword("123456");
		sqlSession.insert(namespance + "saveUser", user2);*/
		
	}

	@Override
	public ResponseEntity registerUser(RegisterUserRequest registerUserRequest) {
		if(registerUserRequest.getLoginName() == null || "".equals(registerUserRequest.getLoginName()))
		{
			return new ResponseEntity(201, "loginName is null.");
		}
		
		if(registerUserRequest.getName() == null || "".equals(registerUserRequest.getName()))
		{
			return new ResponseEntity(201, "name is null.");
		}
		
		if(registerUserRequest.getPassword() == null || "".equals(registerUserRequest.getPassword()))
		{
			return new ResponseEntity(201, "password is null.");
		}
		
		if(registerUserRequest.getConfirmPassword() == null || "".equals(registerUserRequest.getConfirmPassword()))
		{
			return new ResponseEntity(201, "confirmPassword is null.");
		}
		
		if(!registerUserRequest.getConfirmPassword().equals(registerUserRequest.getPassword()))
		{
			return new ResponseEntity(202, "password not match");
		}
		
		User user = new User();
		user.setLoginName(registerUserRequest.getLoginName());
		user.setName(registerUserRequest.getName());
		user.setPassword(StringUtils.entryptPassword(registerUserRequest.getPassword()));
		user.setSourceType(2);
		
		User queryUser = sqlSession.selectOne(namespance+"getUserByLoginName", registerUserRequest.getLoginName());
		if(null == queryUser)
		{
			user.setCreateDate(new Date());
			user.setUpdateDate(new Date());
			sqlSession.insert(namespance+"saveUser", user);
		}
		else
		{
			return new ResponseEntity(203, "user already exists");
		}
		
		return new ResponseEntity(200, "success");
	}

	@Override
	public List<TaskInfo> getTaskList(RegisterUserRequest registerUserRequest) {

		User queryUser = sqlSession.selectOne(namespance+"getUserByLoginName", registerUserRequest.getLoginName());
		if(null != queryUser)
		{
			return postFormD(queryUser.getNo());
		}
		else
		{
			return new ArrayList<TaskInfo>();
		}
	}
	
	private List<TaskInfo> postFormD(String userNo) {
		
		List<TaskInfo> taskList = new ArrayList<TaskInfo>();
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpGet httpget = new HttpGet(oaTodoUrl + "/?userNo=" + userNo);

		try {

//			System.out.println("executing request " + httpget.getURI());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				//System.out.println("--"+EntityUtils.toString(entity, "UTF-8"));
				JSONArray jsonArr = JSONArray.fromObject(EntityUtils.toString(entity, "UTF-8"));
				TaskInfo taskInfo = null;
				for (int i = 0; i < jsonArr.size(); i++) {
					
					 taskInfo = new TaskInfo();
					 taskInfo.setProcInsId(jsonArr.getJSONObject(i).getString("procInsId"));
					 taskInfo.setTitle(jsonArr.getJSONObject(i).getString("title"));
					 taskInfo.setApplyUserName(jsonArr.getJSONObject(i).getString("applyUserName"));
					 taskInfo.setAssignee(jsonArr.getJSONObject(i).getString("assignee"));
					 taskInfo.setTaskName(jsonArr.getJSONObject(i).getString("taskName"));
					 taskInfo.setUrl(jsonArr.getJSONObject(i).getString("reserved1"));
	                 taskList.add(taskInfo);
	            }
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			LOG.error("request task error", e);
		} catch (UnsupportedEncodingException e1) {
			LOG.error("request task error", e1);
		} catch (IOException e) {
			LOG.error("request task error", e);
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				LOG.error("request task error", e);
			}
		}
		
//		System.out.println("taskList.size():" + taskList.size());
		
		return taskList;
	}
	
	public static void main(String[] args)
	{
	String aa = "[{\"isNewRecord\":true,\"taskId\":\"509031\",\"taskName\":\"01 直属上级审批\",\"taskDefKey\":\"HR_02_01\",\"procInsId\":\"509006\",\"procDefId\":\"overtime:17:476020\",\"procDefKey\":\"overtime\",\"procDefName\":\"加班申请\",\"title\":\"李扬的加班申请\",\"status\":\"todo\",\"flag\":\"yes\",\"assignee\":\"707128\",\"applyUserName\":\"李扬\",\"applyUserId\":\"05c54a384ce64177b20984c9cebc0781\",\"beginDate\":\"2016-12-09 10:27:59\",\"reserved1\":\"http://172.16.3.220:8123/tempusoa/a/act/task/form?taskId=509031&taskName=01+%E7%9B%B4%E5%B1%9E%E4%B8%8A%E7%BA%A7%E5%AE%A1%E6%89%B9&taskDefKey=HR_02_01&procInsId=509006&procDefId=overtime:17:476020&status=todo&backFlag=5&random=1482475617921\",\"todoTask\":true,\"finishTask\":false},{\"isNewRecord\":true,\"taskId\":\"509031\",\"taskName\":\"01 直属上级审批\",\"taskDefKey\":\"HR_02_01\",\"procInsId\":\"509006\",\"procDefId\":\"overtime:17:476020\",\"procDefKey\":\"overtime\",\"procDefName\":\"加班申请\",\"title\":\"李扬的加班申请\",\"status\":\"todo\",\"flag\":\"yes\",\"assignee\":\"707128\",\"applyUserName\":\"李扬\",\"applyUserId\":\"05c54a384ce64177b20984c9cebc0781\",\"beginDate\":\"2016-12-09 10:27:59\",\"reserved1\":\"http://172.16.3.220:8123/tempusoa/a/act/task/form?taskId=509031&taskName=01+%E7%9B%B4%E5%B1%9E%E4%B8%8A%E7%BA%A7%E5%AE%A1%E6%89%B9&taskDefKey=HR_02_01&procInsId=509006&procDefId=overtime:17:476020&status=todo&backFlag=5&random=1482475617921\",\"todoTask\":true,\"finishTask\":false}]";
	JSONArray jsonArr = JSONArray.fromObject(aa);
		for (int i = 0; i < jsonArr.size(); i++) {
			
			 System.out.println("-=------------procInsId-------" + jsonArr.getJSONObject(i).getString("applyUserName"));
		}
	}
}
