package com.atguigu.scw.webui.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atguigu.common.bean.AppResponse;
import com.atguigu.scw.webui.service.UserServiceFeignClient;
import com.atguigu.scw.webui.vo.TMemberAddress;
@Service
public class UserServiceFeignClientExceptionHandler implements UserServiceFeignClient {

	@Override
	public AppResponse<Object> doLogin(String loginacct, String userpswd) {
		return AppResponse.fail("20000", "服务器异常，请重试");
	}

	@Override
	public AppResponse<List<TMemberAddress>> getAllAddress(String accessToken) {
		return AppResponse.fail("20000", "服务器异常，请重试");
	}

}
