package com.gdu.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	// index 페이지 요청
	@GetMapping("/index")
	public String index() {
		return "index";
   }
	
	// home 페이지 요청
	@GetMapping("/home")
	public String home(HttpSession session) {
		// 로그인 아닐시에
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		return "home";
	}
}