package com.thosepeople.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.thosepeople.service.GetPasswordService;

@Controller
@RequestMapping(value="/forgetPassword")

public class GetPasswordController{
	@Autowired
	GetPasswordService passwordGetService;
	@RequestMapping(value="/resetPassword")
//	@ResponseBody 
	public ModelAndView resetPassword(HttpServletRequest request,Model model){
		System.out.println("email======="+request.getParameter("email"));
		String returnMessage=passwordGetService.resetPassword(request);
		model.addAttribute("getPasswordReturnMessage", returnMessage);
		return new ModelAndView("/login");
	}
	@RequestMapping(value="/getPassword")
	public ModelAndView getPassword(@RequestParam("sid") String sid,
			@RequestParam("email") String email,Model model){
		String returnMessage=passwordGetService.getPassword(sid,email);
		if(returnMessage.contains("@")){
			model.addAttribute("email", returnMessage);
			return new ModelAndView("这里改成修密码修改密码的jsp");
		}else{
			model.addAttribute("returnMessage", returnMessage);
			return new ModelAndView("这里改成修密码修改密码的jsp");
		}
	}
}
