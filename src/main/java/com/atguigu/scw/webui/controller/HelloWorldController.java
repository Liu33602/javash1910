package com.atguigu.scw.webui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.atguigu.scw.webui.bean.TMember;

@Controller  //webui项目属于消费者，用户直接访问的，每个请求都需要相应页面
public class HelloWorldController {

	@GetMapping("/test")
	public String testThymeleaf(Model model, HttpSession session) {
		System.out.println("。。。。。");
		//在域中设置属性值
		//request
		model.addAttribute("reqKey", "reqVal");
		//session
		session.setAttribute("sessionKey", "sessionVal");
		//application
		ServletContext application = session.getServletContext();
		
		session.setAttribute("member", new TMember(1, "1111", null, "anni", "a@1232.com", "1", "1", "adsasd", "2424214", "2"));
		application.setAttribute("appKey", "appVal");
		
		List<TMember> list = new ArrayList<TMember>();
		list.add(new TMember(1, "马呈祥", "123456", "anni", "a@1232.com", "1", "1", "adsasd", "2424214", "2"));
		list.add(new TMember(2, "李琛", "123456", "anni", "a@1232.com", "1", "1", "adsasd", "2424214", "2"));
		list.add(new TMember(3, "杨絮柳", "123456", "anni", null, "1", "1", "adsasd", "2424214", "2"));
		list.add(new TMember(4, "康雷建", "123456", "anni", null, "1", "1", "adsasd", "2424214", "2"));
		list.add(new TMember(5, "熊志超", "123456", "anni", "a@1232.com", "1", "1", "adsasd", "2424214", "2"));
		
		model.addAttribute("members", list);
		return "hello-thymeleaf";
	}
}
