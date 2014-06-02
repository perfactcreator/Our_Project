package com.thosepeople.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.thosepeople.service.GetPasswordService;

/**
 * @author wenxiaobing
 * 
 */

@Controller
@RequestMapping(value="/forgetPassword")

public class GetPasswordController{
	@Autowired
	GetPasswordService getPasswordService;
	@RequestMapping(value="/resetPassword")
//	@ResponseBody 
	public ModelAndView resetPassword(HttpServletRequest request,Model model){
		System.out.println("email======="+request.getParameter("email"));
		String returnMessage=getPasswordService.resetPassword(request);
		model.addAttribute("getPasswordReturnMessage", returnMessage);
		return new ModelAndView("/login");
	}
	@RequestMapping(value="/getPassword",method = RequestMethod.GET)
	public ModelAndView getPassword(@RequestParam("sid") String sid,
			@RequestParam("email") String email,HttpServletRequest request,Model model){
		String returnMessage=getPasswordService.getPassword(sid,email);
		if(returnMessage.contains("@")){
			model.addAttribute("email", returnMessage);
			return new ModelAndView("/reset_password");
		}else{
			model.addAttribute("returnMessage", returnMessage);
			return new ModelAndView("/reset_password");
		}
	}
	@RequestMapping(value="/saveResetPassword")
	public ModelAndView saveResetPassword(HttpServletRequest request,Model model){
		System.out.println("MMMMMMMMMMMMMMMMMMMMM");
		boolean isUpdate=getPasswordService.saveResetPassword(request);
		return new ModelAndView("/login");
	}
}