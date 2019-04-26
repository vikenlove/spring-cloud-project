package com.emerging.cloud.app.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import com.emerging.cloud.app.service.DemoService;
import com.emerging.framework.core.exception.CommonException;
import com.emerging.framework.core.params.ResStatusEnum;
import com.emerging.framework.core.params.ResponseData;
import com.emerging.service.persistence.model.UserModel;

@RestController
public class DemoController {
	
	  protected static Logger LOG = LogManager.getLogger(DemoController.class);
	
	@Autowired
	private DemoService demoService;
	
	@GetMapping("/save")
	public ResponseData<Integer> saveService() {
		ResponseData<Integer> rd = new ResponseData<>();
		try {
			Integer result = demoService.demoService();
			rd.setData(result);
		} catch (Exception e) {
			rd.setStatus(ResStatusEnum.FAIL.getCode());
			rd.setMessage(ResStatusEnum.FAIL.getMessage());
			e.printStackTrace();
		}
		return rd;
	}
	
	@GetMapping("/getUserModel")
	public ResponseData<List<UserModel>> getUserModel() {
		ResponseData<List<UserModel>> rd = new ResponseData<>();
		try {
			List<UserModel> list = demoService.getUserModel();
			
			rd.setData(list);
		} catch (Exception e) {
			rd.setStatus(ResStatusEnum.FAIL.getCode());
			rd.setMessage(ResStatusEnum.FAIL.getMessage());
			LOG.error(e);
			e.printStackTrace();
		}
		return rd;
	}
	
	@PostMapping("/saveAndUpdate")
	public ResponseData<Object> saveAndUpdate() {
		ResponseData<Object> rd = new ResponseData<>();
		try {
			demoService.saveAndUpdate();
			rd.setData(ResStatusEnum.SUCCESS.getCode());
		} catch (Exception e) {
			rd.setStatus(ResStatusEnum.FAIL.getCode());
			rd.setMessage(ResStatusEnum.FAIL.getMessage());
			e.printStackTrace();
		}
		return rd;
	}
	
}
