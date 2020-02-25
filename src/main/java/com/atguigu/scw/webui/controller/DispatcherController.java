package com.atguigu.scw.webui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.atguigu.common.bean.AppResponse;
import com.atguigu.scw.webui.service.ProjectServiceFeignClient;
import com.atguigu.scw.webui.vo.ProjectVo;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DispatcherController {
	@Autowired
	ProjectServiceFeignClient projectServiceFeignClient;
	
	//2、转发到登录页面的方法
	// <mvc:view-controller path=""   viewname=""/>
	/*@GetMapping(value= {"/login.html"})
	public String toLoginPage() {
		return "user/login";
	}*/
	//1、转发到首页的方法
	@GetMapping(value= {"/" , "/index","/index.html"})
	public String toIndexPage(Model model) {
		//查询项目列表，存到域中
		AppResponse<List<ProjectVo>> response = projectServiceFeignClient.index();
		log.warn("获取到的response对象的数据为:{}",response.getData());
		model.addAttribute("projectVos", response.getData());
		//转发到首页获取遍历显示
		return "index";
	}
}
