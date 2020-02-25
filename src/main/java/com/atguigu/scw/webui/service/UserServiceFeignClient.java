package com.atguigu.scw.webui.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.common.bean.AppResponse;
import com.atguigu.scw.webui.service.impl.UserServiceFeignClientExceptionHandler;
import com.atguigu.scw.webui.vo.TMemberAddress;

@FeignClient(value="SCW-USER",fallback=UserServiceFeignClientExceptionHandler.class)  //声明当前接口是一个feign客户端
public interface UserServiceFeignClient {
	//feignclient需要将  SCW-USER转为注册中心项目对应的 ip地址+端口号== 192.168.37.1:7000
	//通过ip地址+端口号+ url映射地址  拼接去远程调用:192.168.37.1:7000/user/doLogin
	/**
	 * 	Too Many body parameters 
	 * 远程调用如果方法有形参，参数必须使用
	 * 		参数如果是多个简单类型的参数：@RequestParam绑定参数
	 * 		参数如果是对象或者集合  ：必须使用@RequestBody绑定参数
	 * 		绑定后  需要保证两端方法都一样
	 * 
	 * Load balancer does not have available server for client: SCW-USER
	 * 		-错误：  scw-user项目启动后  在注册中心中还没有稳定
	 * 
	 * connect timeout:  一般重新访问 就没有问题了
	 * 
	 * 
	 * 远程调用时，如果方法返回的类型是引用类型，需要将引用类型的类实现序列化接口
	 */
	@PostMapping("/user/doLogin")
	public AppResponse<Object> doLogin(@RequestParam("loginacct")String loginacct ,@RequestParam("userpswd") String userpswd);

	@GetMapping("/user/getAllAddress")
	public AppResponse<List<TMemberAddress>> getAllAddress(@RequestParam("accessToken")String accessToken);
}
