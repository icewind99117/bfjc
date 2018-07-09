package com.lsf.imf.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lsf.imf.entity.JsonResult;
import com.lsf.imf.entity.JsonResult.ResultCode;



@RestController
public class LoginController {
	
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	JsonResult login(@RequestParam Map<String, String> loginData) {
		try {
			
			UsernamePasswordToken token = new UsernamePasswordToken(loginData.get("userName"), loginData.get("pwd"));
			log.info("loginInfo:"+token.toString());
			SecurityUtils.getSubject().login(token);
			return new JsonResult(ResultCode.LOGIN_SUCCESS);
		}catch(Exception e) {
			return new JsonResult(ResultCode.LOGIN_FAIL);	
		}
	}
	
	
}
