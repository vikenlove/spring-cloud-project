package com.emerging.cloud.app.feign.demo;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.emerging.framework.core.params.ResponseData;
import com.emerging.service.persistence.model.UserModel;
//import com.emerging.service.persistence.model.UserModel;

@FeignClient(name="service2-provider")
public interface DemoFeignClient2 {
	
	@GetMapping("/save")
	public ResponseData<Integer>  saveService();
	
	@GetMapping("/getUserModel")
	ResponseData<List<UserModel>> getUserModel();
	
	@PostMapping("/saveAndUpdate")
	ResponseData<Object> saveAndUpdate();
}
