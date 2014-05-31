package com.thosepeople.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thosepeople.exception.BusinessException;
import com.thosepeople.service.GetPasswordService;

@Controller
@RequestMapping(value="/forgetPassword")

public class GetPasswordController{
	@Autowired
	GetPasswordService passwordGetService;
	@RequestMapping(value="/resetPassword")
//	@ResponseBody 
	public String resetPassword(HttpServletRequest request,Model model){
		System.out.println("email======="+request.getParameter("email"));
		passwordGetService.resetPassword(request);
		//这里最好跳转到登陆页面，此处需要修改
		return "redirect:/user/forgetPassword.do";
	}
	@RequestMapping(value="/getPassword")
	public void getPassword(@RequestParam("sid") String sid,
			@RequestParam("email") String email){
		passwordGetService.getPassword(sid,email);
	}
}
