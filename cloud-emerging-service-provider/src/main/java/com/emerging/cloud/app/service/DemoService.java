package com.emerging.cloud.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.emerging.framework.core.exception.CommonException;
import com.emerging.service.persistence.mapper.UserModelMapper;
import com.emerging.service.persistence.model.UserModel;

import io.seata.core.context.RootContext;

@Service
public class DemoService {
	@Autowired
	private UserModelMapper userModelMapper;
	

	public Integer demoService(String name,Integer age) throws CommonException{
		
		System.out.println("_-----A 服务---------XID--:"+RootContext.getXID());
		
		
		UserModel record = new UserModel();
		record.setName(name);
		record.setAge(age);
		return userModelMapper.insertSelective(record);
	
	}
}
