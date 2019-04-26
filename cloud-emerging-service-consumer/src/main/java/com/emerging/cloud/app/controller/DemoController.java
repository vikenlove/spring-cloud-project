package com.emerging.cloud.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emerging.cloud.app.feign.demo.DemoFeignClient;
import com.emerging.cloud.app.feign.demo.DemoFeignClient2;
import com.emerging.cloud.app.service.DemoConsumerService;
import com.emerging.framework.core.params.ResponseData;
import com.emerging.service.persistence.model.UserModel;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;

@RestController
public class DemoController {
	@Autowired
	private DemoFeignClient demoFeignClient;
	
	@Autowired
	private DemoFeignClient2 demoFeignClient2;
	
	@Autowired
	private DemoConsumerService demoConsumerService;
	
	
	@GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
	@GetMapping("/getdemo")
	public String demo() {
		System.out.println("_--------------XID--:"+RootContext.getXID());
		// 调用A 服务  简单save
		ResponseData<Integer> result = demoFeignClient.insertService("test",1);
		if(result.getStatus()==400) {
			System.out.println(result+"+++++++++++++++++++++++++++++++++++++++");
			throw new RuntimeException("this is error1");
		}
	
		// 调用B 服务。报错测试A 服务回滚
		ResponseData<Integer>  result2 = demoFeignClient2.saveService();
	
		if(result2.getStatus()==400) {
			System.out.println(result2+"+++++++++++++++++++++++++++++++++++++++");
			throw new RuntimeException("this is error2");
		}

		return "SUCCESS";
	}
	
	/**
	 * 查询返回
	 * @return
	 */
	@GetMapping("/getUserModel")
	public ResponseData<List<UserModel>> demo2() {
		return demoFeignClient2.getUserModel();
	}
	
	
	@PostMapping("/saveAndUpdate")
//	@GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
	public String saveAndUpdate(){
		
//		// 调用A 服务  简单save
//		ResponseData<Integer> result = demoFeignClient.insertService("test234",111);
//		
//		if(result.getStatus()==400) {
//			System.out.println(result+"+++++++++++++++++++++++++++++++++++++++");
//			throw new RuntimeException("this is error1");
//		}
//		//B 服务，内部先insert 后update ，通过@Transactional 管理 service 内部 事务
//		ResponseData<Object> result2 = demoFeignClient2.saveAndUpdate();
//		
//		if(result2.getStatus()==400) {
//			System.out.println(result+"++++");
//			throw new RuntimeException("errors mulitpart");
//		}
		
		// 移除注解，到独立service中。并将GlobalTransactional 注解标注在方法中
		demoConsumerService.consumerService();
		
		
		return "SUCCESS";
	}
	
	
}
