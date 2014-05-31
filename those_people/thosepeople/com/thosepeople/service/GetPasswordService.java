package com.thosepeople.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.thosepeople.exception.BusinessException;

@Service
public interface GetPasswordService {
	public String resetPassword(HttpServletRequest request);
	public String getPassword(String sid,String email);
}