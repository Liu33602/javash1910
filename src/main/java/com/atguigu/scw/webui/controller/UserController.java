package com.atguigu.scw.webui.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.common.bean.AppResponse;
import com.atguigu.scw.webui.service.UserServiceFeignClient;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserServiceFeignClient userServiceFeignClient;
	//1、处理登录请求的方法
	@PostMapping("/doLogin")
	public String doLogin(HttpSession session,String loginacct , String userpswd,Model model) {
		System.out.println(loginacct+" , "+userpswd);
		//处理登录业务返回登录成功的用户对象：远程访问scw-user  :  doLogin
		AppResponse<Object> response = userServiceFeignClient.doLogin(loginacct, userpswd);
		System.out.println(response);
		String code = response.getCode();
		if(!"00000".equals(code)) {
			//登录不成功
			String message = response.getMessage();
			model.addAttribute("errorMsg", message);
			//转发到登录页面进行错误提示
			return "user/login";
		}
		
		//如果是从paystep1页面跳转过来的，登录成功需要跳转回登录之前的页面
		String ref = (String) session.getAttribute("ref");
		if(!StringUtils.isEmpty(ref)) {
			//跳转回之前的页面
			session.setAttribute("user", response.getData());
			session.removeAttribute("ref");
			return "redirect:"+ref;
		}
		//将用户对象共享到会话域中     Map
		session.setAttribute("user", response.getData());
		return "redirect:/";//给浏览器一个重定向相应
	}
	
}
