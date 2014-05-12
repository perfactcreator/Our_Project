/**
 * 
 */
package com.thosepeople.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.thosepeople.dao.LoveDao;
import com.thosepeople.model.LoveInfo;
import com.thosepeople.service.PostLoveInfoService;

/**
 * @author chenzhuo
 *
 */
public class PostLoveInfoServiceImpl implements InitializingBean, PostLoveInfoService{
    LoveDao postInfoDao;
    
	public void setPostInfoDao(LoveDao postInfoDao) {
		this.postInfoDao = postInfoDao;
	}

	@Override
	public LoveInfo postLoveInfo(int uid,String userSchool,String title, String selfDescribe,
			String expectOther,String contactWay) {
		if(StringUtils.isEmpty(title)||StringUtils.isEmpty(expectOther)||StringUtils.isEmpty(selfDescribe)){
			return null;
		}
		int generateId=postInfoDao.postLove(uid,userSchool,title,selfDescribe, expectOther, contactWay);
		if(generateId>0){
			return postInfoDao.getLoveInfoById(generateId);
		}
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(postInfoDao, "The postInfoDao should not null !");
	}

}
