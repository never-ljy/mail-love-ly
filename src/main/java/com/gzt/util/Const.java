package com.gzt.util;

public interface Const {
	class email{
		//配置邮箱服务器的地址"smtp.126.com" 就是smtp + 域名
		public static final String MAIL_HOST = "smtp.163.com";
		
		//QQ邮箱服务器地址配置
		public static final String MAIL_HOST_QQ = "smtp.qq.com";

		//配置发件人的邮箱地址
		public static final String MAIL_FROM = "lei15213306@163.com";
		public static final String MAIL_FROM_QQ = "2500985202@qq.com";

	    //配置发件人的邮箱密码
		public static final String MAIL_PASSWORD = "RPXVBLSBZWRELFCR";
		public static final String MAIL_PASSWORD_QQ = "pvjnsezjqkvqdhgf";
	}
	class time{
		public static final long dayMiles = 24*60*60*1000;
	}
}
