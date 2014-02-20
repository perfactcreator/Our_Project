/**
 * 
 */
package com.thosepeople.dao;

import java.sql.Timestamp;

/**
 * @author chenzhuo
 * 
 */
public interface UserDao {
	int registUser(String realName, String nickName, String email,
			String passWord);

	int ifEmailHasBeenRegistered(String email);

	String getPassWordByEmail(String email);

	int completeUserInfoDetail(int uid, byte age, Boolean gender, String city,
			String school, String major, Timestamp enrollmentDate,
			int educationBackground, String signature);
}