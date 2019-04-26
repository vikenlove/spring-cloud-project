package com.emerging.cloud.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.emerging.cloud.app.service.DemoService;
import com.emerging.framework.core.controller.BaseController;
import com.emerging.framework.core.params.ResStatusEnum;
import com.emerging.framework.core.params.ResponseData;

@RestController
public class DemoController extends BaseController{
	@Autowired
	private DemoService demoService;
	
	@GetMapping("/insert/{name}/{age}")
	public ResponseData<Integer> insertService(@PathVariable String name,@PathVariable Integer age) {
		ResponseData<Integer> rd = new ResponseData<>();
	
		try {
			Integer result = demoService.demoService(name, age);
			rd.setData(result);
		} catch (Exception e) {
			rd.setStatus(ResStatusEnum.FAIL.getCode());
			rd.setMessage(ResStatusEnum.FAIL.getMessage());
			e.printStackTrace();
		}
		return rd;
	}
}
