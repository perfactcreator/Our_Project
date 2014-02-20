/**
 * 
 */
package com.thosepeople.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thosepeople.exception.BusinessException;
import com.thosepeople.service.LoginService;

/**
 * @author chenzhuo
 * 
 */
@Controller
@RequestMapping("/login")
public class Login {
	@Autowired
	@Qualifier("loginService")
	LoginService loginService;

	
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@ResponseBody
	@RequestMapping("/verifyPassWord")
	public Map<String, Boolean> verifyThePassWord(
			@RequestParam("email") String email,
			@RequestParam("passWord") String passWord) throws BusinessException {
		Map<String, Boolean> result = new HashMap<>(1);
		boolean verifyResult = loginService.verrifyTheUserPassWord(email,
				passWord);
		result.put("result", verifyResult);
		return result;
	}
	
	@RequestMapping("/goIntoWeb")
	public ModelAndView login(@RequestParam("email") String email){
		
		return new ModelAndView();
	}
}