package com.emerging.cloud.app.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.emerging.framework.core.exception.CommonException;
import com.emerging.service.persistence.mapper.UserModelMapper;
import com.emerging.service.persistence.model.UserModel;

import io.seata.core.context.RootContext;

@Service
public class DemoService {
	
    protected static Logger LOG = LogManager.getLogger(DemoService.class);
	
	
	@Autowired
	private UserModelMapper userModelMapper;
	
	
	
	public Integer demoService() throws CommonException{
		UserModel record = new UserModel();
		record.setName("这是一个即将报错的SQL11111111111111111111111111111111111111111111111111111111111111111");
		record.setAge(1);
		return userModelMapper.insertSelective(record);
	}

	/**
	 *  开启本地事务
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveAndUpdate() throws CommonException{
		System.out.println("_-B----开启本地事务---------XID--:"+RootContext.getXID());
		
		//先保持 后修改
		UserModel record = new UserModel();
		record.setName("年龄修改");
		record.setAge(12);
		userModelMapper.insertSelective(record);
		
		Integer id = record.getId();
		
		System.out.println(id+"----获取ID自增--------------------------------");
		
		//修改操作
		UserModel record2 = new UserModel();
		record2.setName("tis ");
		record2.setAge(24);
		userModelMapper.insertSelective(record2);
		//userModelMapper.updateByPrimaryKey(record2);
		//userModelMapper.updateByPrimaryKeySelective(record2);
	}
	
	public List<UserModel> getUserModel()throws CommonException{
		
		UserModel record = new UserModel();
		record.setName("年龄修改");
		record.setAge(12);
		userModelMapper.insertSelective(record);
		
		Integer id = record.getId();
		
		System.out.println(id+"------------------------------------");
		
		return userModelMapper.selectByExample(null);
	}
	
	public static void main(String[] args) {
		System.out.println("tis is test".length());
	}
	
}
