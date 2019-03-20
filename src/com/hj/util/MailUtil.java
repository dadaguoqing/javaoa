package com.hj.util;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.hj.oa.Consts;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.User;
import com.hj.oa.service.MenuService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;

public class MailUtil {
	
	private JavaMailSender mailSender;
	
	public void sendEMail(String to, String copyTo, final String from, final String subject, final String content){
		
		if(Consts.devMode == 1||to == null || to.length()==0){
//			to = "wenqi.fang@itek-training.com";
			to = "wenqi.fang@itek-training.com";
		}
		
		final String fto = to;
		
		if(Consts.devMode != 1 && to.equals(Consts.bossEmail)){ //正式环境，所有发送给刘总的邮件，抄送给张玲玲
			if(StringUtils.isEmpty(copyTo)){
				copyTo = "";
			}else{
				if(copyTo.endsWith(";")){
					copyTo += "";
				}else{
					copyTo += ";";
				}
			}
		}
		
		final String fcopyto = copyTo;
		
		String[] cctp = null;
		if(StringUtils.isNotEmpty(fcopyto)){
			cctp = fcopyto.split(";");
		}
		
		final String[] cc = cctp;
		
		
		Thread t = new Thread(new Runnable(){

			public void run() {
				try{
					MimeMessage mailMessage = mailSender.createMimeMessage();
					MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true);
					
					messageHelper.setTo(fto);//接受者     
					if(StringUtils.isNotEmpty(fcopyto)){//如果有抄送
						//System.out.println("发送给了："+Arrays.toString(cc));
						messageHelper.setCc(cc);
					}
					messageHelper.setFrom(from);//发送者  
					messageHelper.setSubject(subject);//主题  
					messageHelper.setText(content,true);
					mailSender.send(mailMessage);
				
				}catch(Exception e){
					//发送邮件失败
					e.printStackTrace();
				}
			}
			
		});
		
		t.start();
		
	}
	
	//发送给我的。
	public void sendToMe( String subject, String content){
		if(Consts.devMode == 1){
			this.sendEMail("wenqi.fang@itek-training.com", null, Consts.defaultFrom, subject, content);
		}else{
			this.sendEMail("wenqi.fang@itek-training.com", null, Consts.defaultFrom, subject, content);
		}
	}
	
	//发送给我的。
	public void sendToOaTeam( String subject, String content){
		if(Consts.devMode == 1){
			this.sendEMail("wenqi.fang@itek-training.com", null, Consts.defaultFrom, subject, content);
		}else{
			this.sendEMail("wenqi.fang@itek-training.com", null, Consts.defaultFrom, subject, content);
		}
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendForNotice(User spUser, UserService userService, RoleService roleService,
			MenuService menuService, String content, String subject){
		
		User user = spUser;
		//别人代理我的
		User bdlUser = roleService.findDailiByOwnerId(user.getId());
		String copyTo = null;
		
		if(bdlUser!=null){
			List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), user.getId());
			if(OtherUtil.containsMenu(Consts.qjsp, menus)){
				copyTo = bdlUser.getEmail();
			}
		}
		String to = "wenqi.fang@itek-training.com";//user.getEmail();
		if(Consts.devMode == 0){//应用环境
			to = user.getEmail();
		}
		
		if(to != null && to.length()!=0){
			//发送通知，要审批了。
			this.sendEMail(to, copyTo, Consts.defaultFrom, subject, content);
		}
	}
	
	public void sendEMailWithFile(String to, String copyTo, final String from, final String subject, final String content, final String[] filePath, final String[] fileNames){
		
		
		if(Consts.devMode == 1){
			to = "wenqi.fang@itek-training.com";
		}
		
		if(to == null || to.length()==0){
			to = "wenqi.fang@itek-training.com";
		}
		
		final String fto = to;
		final String fcopyto = copyTo;
		
		String[] cctp = null;
		if(StringUtils.isNotEmpty(fcopyto)){
			cctp = fcopyto.split(";");
		}
		
		final String[] cc = cctp;
		
		
		Thread t = new Thread(new Runnable(){
			public void run() {
				try{
					MimeMessage mailMessage = mailSender.createMimeMessage();
					MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true);
					
					messageHelper.setTo(fto);//接受者     
					if(StringUtils.isNotEmpty(fcopyto)){//如果有抄送
						//System.out.println("发送给了："+Arrays.toString(cc));
						messageHelper.setCc(cc);
					}
					messageHelper.setFrom(from);//发送者  
					messageHelper.setSubject(subject);//主题  
					messageHelper.setText(content,true);
					
					mailMessage = messageHelper.getMimeMessage();
					
					//System.out.println("发送附件前。。。。。");
					if (filePath != null) {  
			            BodyPart mdp = new MimeBodyPart();// 新建一个存放信件内容的BodyPart对象  
			            mdp.setContent(content, "text/html;charset=UTF-8");// 给BodyPart对象设置内容和格式/编码方式  
			            Multipart mm = new MimeMultipart();// 新建一个MimeMultipart对象用来存放BodyPart对象  
			            mm.addBodyPart(mdp);// 将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)  
			            // 把mm作为消息对象的内容  
			            MimeBodyPart filePart;  
			            FileDataSource filedatasource;  
			            // 逐个加入附件  
			            for (int j = 0; j < filePath.length; j++) {  
			            	//System.out.println("附件:" + filePath[j]);
			                filePart = new MimeBodyPart();  
			                filedatasource = new FileDataSource(filePath[j]);  
			                filePart.setDataHandler(new DataHandler(filedatasource));  
			                try {  
			                    filePart.setFileName(MimeUtility.encodeText(fileNames[j]));  
			                } catch (Exception e) {  
			                    e.printStackTrace();  
			                }  
			                mm.addBodyPart(filePart);  
			            }  
			            mailMessage.setContent(mm);  
			           // System.out.println("发送附件后。。。。。");
			        }  
					
					mailSender.send(mailMessage);
				
				}catch(Exception e){
					//发送邮件失败
					e.printStackTrace();
				}
			}
			
		});
		
		t.start();
		
	}

}
