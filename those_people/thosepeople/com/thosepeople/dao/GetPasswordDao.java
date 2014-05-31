package com.thosepeople.dao;

import com.thosepeople.model.PasswordResetInfo;

import org.springframework.stereotype.Repository;

@Repository
public interface GetPasswordDao {
	void insertResetItem(String email,String secretKey,long outOfDateTime);
	PasswordResetInfo queryResetItemByEmail(String email);
}
