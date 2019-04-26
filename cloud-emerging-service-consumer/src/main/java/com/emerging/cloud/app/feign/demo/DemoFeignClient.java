package com.emerging.cloud.app.feign.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.emerging.framework.core.params.ResponseData;

@FeignClient(name="service-provider")
public interface DemoFeignClient {
	
	@GetMapping("/insert/{name}/{age}")
	public ResponseData<Integer> insertService(@PathVariable String name,@PathVariable Integer age);
}
