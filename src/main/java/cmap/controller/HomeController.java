package cmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cmap.services.MemberService;

@Controller
public class HomeController {

	@Autowired
	MemberService mems;

	
	// --- Tạo user tạm thời
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String create(@RequestParam("fullname") String fullname,
						@RequestParam("username") String username,
						@RequestParam("password") String password,
						@RequestParam(value="teacher", required = false, defaultValue = "false") boolean teacher) {
		// --- Nếu yêu cầu thêm thành viên được chấp nhận
		if (mems.addMember(username, password, fullname, teacher)) {
			return "redirect:login.html";
		}
		return "redirect:signup.html?Ten-tai-khoan-da-ton-tai";
	}
	
	

}
