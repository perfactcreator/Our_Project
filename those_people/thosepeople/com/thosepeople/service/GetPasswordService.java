package com.thosepeople.service;

import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Service;

/**
 * @author wenxiaobing
 * 
 */

@Service
public interface GetPasswordService {
	public String resetPassword(HttpServletRequest request);
	public String getPassword(String sid,String email);
	public boolean saveResetPassword(HttpServletRequest request);
}