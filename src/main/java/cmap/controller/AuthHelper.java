package cmap.controller;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthHelper {
	public static Md5PasswordEncoder md5 = new Md5PasswordEncoder();
	
	public static String getUserName(){
		UserDetails auth =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return auth.getUsername();
	}
	
	public static String password(String pass){
		return md5.encodePassword(pass,null);
	}
}
