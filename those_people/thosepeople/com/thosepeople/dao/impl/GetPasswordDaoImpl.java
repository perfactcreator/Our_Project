package com.thosepeople.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.mysql.jdbc.Statement;
import com.thosepeople.dao.GetPasswordDao;
import com.thosepeople.model.PasswordResetInfo;

@Repository
public class GetPasswordDaoImpl extends JdbcDaoSupport implements GetPasswordDao{
	//@Resource(name="dataSource")
	@Autowired
    public void setDatasource(DataSource dataSource) {
    setDataSource(dataSource);
	}

	private static final BeanPropertyRowMapper<PasswordResetInfo> rowMapper = new BeanPropertyRowMapper<PasswordResetInfo>(
			PasswordResetInfo.class);
	static {
		rowMapper.setPrimitivesDefaultedForNullValue(true);
	}
	
	@Override
	public void insertResetItem(String email,String secretKey,long outOfDateTime){
		System.out.println(outOfDateTime);
		final String insertSql="insert password_reset(email,secret_key,time_out) values("
		+"'"
		+email
		+"'"
		+","
		+"'"
		+secretKey
		+"'"
		+","
		+outOfDateTime
		+");";
		//this.getJdbcTemplate().update(insertSql,new Object[]{email,secretKey,outOfDateTime});
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(insertSql,
						Statement.RETURN_GENERATED_KEYS);
				return ps;
			}
		}, keyHolder);
	}
	
	public PasswordResetInfo queryResetItemByEmail(String email){
		String querySql="Select email,secret_key,out_of_date_time from password_reset where email=?";
		List<PasswordResetInfo> queryResult=this.getJdbcTemplate().query(querySql,new Object[]{email}, rowMapper);
        if(!CollectionUtils.isEmpty(queryResult)){
        	return queryResult.get(0);
        }
		return null;
	}
}
