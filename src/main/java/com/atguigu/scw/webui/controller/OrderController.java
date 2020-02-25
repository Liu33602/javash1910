package com.atguigu.scw.webui.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.common.bean.AppResponse;
import com.atguigu.scw.webui.service.UserServiceFeignClient;
import com.atguigu.scw.webui.vo.TMemberAddress;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/order")
@Slf4j
public class OrderController {
	@Autowired
	UserServiceFeignClient userServiceFeignClient;
	
	//1、在paystep1页面中点击去结算的 请求处理方法
	@GetMapping("/pay-step-2.html")
	public String toPayStep2(@RequestHeader("referer") String ref,Model model,Integer rtnCount,HttpSession session) {
		//1、验证当前用户是否登录
		Map map = (Map) session.getAttribute("user");
		if(CollectionUtils.isEmpty(map)) {
			//用户未登录或者登录超时，转发到登录页面给用户提示
			String message = "结算操作必须先登录";
			model.addAttribute("errorMsg", message);
			session.setAttribute("ref", ref);//paystep1页面地址
			//转发到登录页面进行错误提示
			return "user/login";
		}
		//2、根据用户的accessToken查询用户的收货地址列表
		String accessToken = (String) map.get("accessToken");
		//远程调用scw-user 查询用户的收货地址列表
		AppResponse<List<TMemberAddress>> response = userServiceFeignClient.getAllAddress(accessToken);
		List<TMemberAddress> addresses = response.getData();
		//3、将数据存到request域中共享
		model.addAttribute("rtnCount", rtnCount);
		model.addAttribute("addresses", addresses);
		log.warn("登录状态对象获取的token:{},接受的回报数量：{},查询到的地址对象列表：{}", accessToken,rtnCount , addresses);
		//4、转发到paystep2页面显示数据
		return "order/pay-step-2";
		
	}
	
	
}
