package com.thosepeople.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.thosepeople.dao.GetPasswordDao;
import com.thosepeople.model.PasswordResetInfo;
import com.thosepeople.service.GetPasswordService;
import com.thosepeople.service.RegisterService;
import com.thosepeople.util.EncryptUtil;

/**
 * @author wenxiaobing
 * 
 */


@Service
public class GetPasswordServiceImpl implements GetPasswordService{
	@Autowired
	GetPasswordDao getPasswordDao;
	@Autowired
	RegisterService registerService;
	@Autowired
	JavaMailSender mailSender;
	
	PasswordResetInfo passwordResetInfo=new PasswordResetInfo();
	public String resetPassword(HttpServletRequest request){
		String email=request.getParameter("email").trim();
		String returnMessage=null;
		boolean isRegister=registerService.verifyTheEmail(email);
		if(isRegister==false){
			returnMessage="用户不存在";
			return returnMessage;
		}
		try{  
			//密钥     
			String secretKey= UUID.randomUUID().toString(); 
			//忽略毫秒数       
			Timestamp timeout = new Timestamp(System.currentTimeMillis()+30*60*1000);//30分钟后过期         
			long date = timeout.getTime()/1000*1000;                 
			//保存到数据库       
			passwordResetInfo.setEmail(email);
			passwordResetInfo.setSecretKey(secretKey);
			passwordResetInfo.setTimeOut(date);           
			getPasswordDao.insertResetItem(email, secretKey, date);			      
			String key = email+"$"+date+"$"+secretKey; 
			//数字签名      
			String digitalSignature = EncryptUtil.generatePassWord("a", key);
			String path = request.getContextPath();         
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";          
			String resetPassHref =  basePath+"forgetPassword/getPassword.do?sid="+digitalSignature+"&email="+email; 
			String emailContent = "点击下面的链接,重设密码<br/><a href="+resetPassHref +" target='_BLANK'>点击我重新设置密码</a>" +             
			"<br/>本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'";
			
			//发送邮件部分
			MimeMessage message=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8");
			helper.setFrom("xiaobingup@sina.cn");
			helper.setTo(email);
			helper.setSubject("those_people密码找回");
			helper.setText(emailContent,true);
			
//			ClassPathResource  image=new ClassPathResource("pic.png");
//			helper.addInline("getPasswordLogo", image);
			mailSender.send(message);		
			returnMessage = "操作成功,已经发送找回密码链接到您邮箱。请在30分钟内重置密码";           
			
		}
			catch (Exception e){
			e.printStackTrace(); 
			returnMessage="邮箱不存在,请联系管理员";
			}        
			return returnMessage;
	}
	
	
	public String getPassword(String sid,String email){
		String returnMessage=null;
		boolean isRegister=registerService.verifyTheEmail(email.trim());
		if(isRegister==false){
			returnMessage="用户不存在";
			return returnMessage;
		}
		try{
			List<PasswordResetInfo> passwordResetInfoList=getPasswordDao.queryResetItemByEmail(email);
			for(PasswordResetInfo passwordResetInfo:passwordResetInfoList){
				long  timeOut =passwordResetInfo.getTimeOut();
				String key = email+"$"+timeOut+"$"+passwordResetInfo.getSecretKey();
				String digitalSignature = EncryptUtil.generatePassWord("a", key);
				if(digitalSignature.equals(sid)&&timeOut< System.currentTimeMillis()){
					return email;
				}
			}
			returnMessage="链接已经过期，请重新申请修改密码";
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnMessage;
	}
	public boolean saveResetPassword(HttpServletRequest request){
		String email=request.getParameter("email");
		String passWord=request.getParameter("newPassword");
		String encryptPassWord = EncryptUtil.generatePassWord(email, passWord);
		System.out.println("encryptPassWord===="+encryptPassWord);
		int result=getPasswordDao.updatePassword(email,encryptPassWord);
		System.out.println("result===="+result);
		return true;
	}
	
		
}
