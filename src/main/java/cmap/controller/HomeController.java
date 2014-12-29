package cmap.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cmap.services.AssignService;
import cmap.services.FeedBackService;
import cmap.services.MemberService;

@Controller
public class HomeController {

	@Autowired
	MemberService mems;

	// --- Tự động liên kết assign service : Dependency injection
	@Autowired
	private AssignService assigns;

	@Autowired
	private FeedBackService feeds;

	@Autowired
	private ServletContext servle;

	// --- Tạo user tạm thời
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String create(
			@RequestParam("fullname") String fullname,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value = "teacher", required = false, defaultValue = "false") boolean teacher) {
		// --- Nếu yêu cầu thêm thành viên được chấp nhận
		if (mems.addMember(username, password, fullname, teacher)) {
			return "redirect:login.html";
		}
		return "redirect:signup.html?Ten-tai-khoan-da-ton-tai";
	}

	

}
