package com.thosepeople.dao;

import java.util.List;

import com.thosepeople.model.PasswordResetInfo;

import org.springframework.stereotype.Repository;

/**
 * @author wenxiaobing
 * 
 */


@Repository
public interface GetPasswordDao {
	public void insertResetItem(String email,String secretKey,long outOfDateTime);
	public List<PasswordResetInfo> queryResetItemByEmail(String email);
	public int updatePassword(String email,String encryptPassWord);
}
