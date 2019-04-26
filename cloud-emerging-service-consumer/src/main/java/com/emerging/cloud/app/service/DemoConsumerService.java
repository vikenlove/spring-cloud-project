package com.emerging.cloud.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emerging.cloud.app.feign.demo.DemoFeignClient;
import com.emerging.cloud.app.feign.demo.DemoFeignClient2;
import com.emerging.framework.core.params.ResponseData;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;

@Service
public class DemoConsumerService {
	
	
	@Autowired
	private DemoFeignClient demoFeignClient;
	
	@Autowired
	private DemoFeignClient2 demoFeignClient2;
	
	
	
	
	
	
	@GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
	public void consumerService () {
		

		
		// 调用A 服务  简单save
		ResponseData<Integer> result = demoFeignClient.insertService("test234",111);
		
		if(result.getStatus()==400) {
			System.out.println(result+"+++++++++++++++++++++++++++++++++++++++");
			throw new RuntimeException("this is error1");
		}
		//B 服务，内部先insert 后update ，通过@Transactional 管理 service 内部 事务
		ResponseData<Object> result2 = demoFeignClient2.saveAndUpdate();
		
		if(result2.getStatus()==400) {
			System.out.println(result+"++++");
			throw new RuntimeException("errors mulitpart");
		}
	}
}
