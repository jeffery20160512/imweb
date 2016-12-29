package com.isoftstone.app.service.sys.internal;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isoftstone.app.entity.Office;
import com.isoftstone.app.entity.OfficeRequest;
import com.isoftstone.app.entity.ResponseEntity;
import com.isoftstone.app.entity.User;
import com.isoftstone.app.entity.UserRequest;
import com.isoftstone.app.service.sys.ISyncService;

@Service
public class SyncService implements ISyncService {

	//命名空间
	private String namespance = "com.isoftstone.app.syncDao.";
	
	@Resource
    private SqlSession sqlSession;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
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
	public ResponseEntity saveUser(UserRequest userRequest) {
		if(userRequest.getId() == null || "".equals(userRequest.getId()))
		{
			return new ResponseEntity(1, "id is null.");
		}
		
		if(userRequest.getOfficeId() == null || "".equals(userRequest.getOfficeId()))
		{
			return new ResponseEntity(1, "officeId is null.");
		}
		
		if(userRequest.getLoginName() == null || "".equals(userRequest.getLoginName()))
		{
			return new ResponseEntity(1, "loginName is null.");
		}
		
		if(userRequest.getNo() == null || "".equals(userRequest.getNo()))
		{
			return new ResponseEntity(1, "no is null.");
		}
		
		User user = new User();
		user.setEmail(userRequest.getEmail());
		user.setLoginName(userRequest.getLoginName());
		user.setMobile(userRequest.getMobile());
		user.setName(userRequest.getName());
		user.setNickName(userRequest.getNickName());
		user.setNo(userRequest.getNo());
		user.setOaId(userRequest.getId());
		user.setOfficeId(userRequest.getOfficeId());
		user.setPassword(userRequest.getPassword());
		user.setPhone(userRequest.getPhone());
		user.setPhoto(userRequest.getPhoto());
		user.setPosition(userRequest.getPosition());
		user.setQuitFlag(userRequest.getQuitFlag());
		user.setSex(userRequest.getSex());
		user.setSourceType(1);
//		user.setUserType(userRequest.getUserType());
		
		if("0".equals(userRequest.getImType()) || "1".equals(userRequest.getImType()))
		{
			User queryUser = sqlSession.selectOne(namespance+"getUserByNo", userRequest.getNo());
			if(null == queryUser)
			{
				user.setCreateDate(new Date());
				user.setUpdateDate(new Date());
				sqlSession.insert(namespance+"saveUser", user);
			}
			else
			{
				user.setUpdateDate(new Date());
				sqlSession.update(namespance+"updateUser", user);
			}
		}
		else if("2".equals(userRequest.getImType()))
		{
			user.setDelFlag(1);
			user.setUpdateDate(new Date());
			sqlSession.update(namespance+"updateUser", user);
		}
		
		return new ResponseEntity(0, "success");
	}
	
	@Override
	public ResponseEntity saveOffice(OfficeRequest officeRequest) {
		
		if(officeRequest.getId() == null || "".equals(officeRequest.getId()))
		{
			return new ResponseEntity(1, "id is null.");
		}
		
		if(officeRequest.getParentId() == null || "".equals(officeRequest.getParentId()))
		{
			return new ResponseEntity(1, "parentId is null.");
		}
		
		if(officeRequest.getName() == null || "".equals(officeRequest.getName()))
		{
			return new ResponseEntity(1, "name is null.");
		}
		
		Office office = new Office();
		office.setCode(officeRequest.getCode());
		office.setCreateDate(new Date());
		office.setDeputyPerson(officeRequest.getDeputyPerson());
		office.setId(officeRequest.getId());
		office.setName(officeRequest.getName());
		office.setParentId(officeRequest.getParentId());
		office.setParentIds(officeRequest.getParentIds());
		office.setPrimaryPerson(officeRequest.getPrimaryPerson());
		office.setSort(officeRequest.getSort());
		office.setType(officeRequest.getType());
		office.setUpdateDate(new Date());
		office.setUseable(officeRequest.getUseable());
		
		if("0".equals(officeRequest.getImType()) || "1".equals(officeRequest.getImType()))
		{
			Office queryOffice = sqlSession.selectOne(namespance+"getOfficeById", officeRequest.getId());
			if(null == queryOffice)
			{
				office.setCreateDate(new Date());
				office.setUpdateDate(new Date());
				sqlSession.insert(namespance+"saveOffice", office);
			}
			else
			{
				office.setUpdateDate(new Date());
				sqlSession.update(namespance+"updateOffice", office);
			}
		}
		else if("2".equals(officeRequest.getImType()))
		{
			office.setDelFlag(1);
			office.setUpdateDate(new Date());
			sqlSession.update(namespance+"updateOffice", office);
		}
		
		return new ResponseEntity(0, "success");
	}
}
