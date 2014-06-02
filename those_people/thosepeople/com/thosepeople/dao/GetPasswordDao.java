package com.thosepeople.dao;

import java.util.List;

import com.thosepeople.model.PasswordResetInfo;

import org.springframework.stereotype.Repository;

@Repository
public interface GetPasswordDao {
	void insertResetItem(String email,String secretKey,long outOfDateTime);
	List<PasswordResetInfo> queryResetItemByEmail(String email);
}
