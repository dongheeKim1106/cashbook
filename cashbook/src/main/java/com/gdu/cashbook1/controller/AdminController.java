package com.gdu.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook1.service.AdminService;
import com.gdu.cashbook1.service.MemberService;
import com.gdu.cashbook1.vo.Admin;
import com.gdu.cashbook1.vo.LoginMember;

@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private MemberService memberService;
	// 관리자 로그인 화면 요청
	@GetMapping("/adminLogin")
	public String adminLogin(HttpSession session) {
		// 로그인 중
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		return "adminLogin";
	}
	// 관리자 로그인
	@PostMapping("/adminLogin")
	public String adminLogin(Model model, HttpSession session, Admin admin, LoginMember loginMember) {
		Admin returnLoginAdmin = adminService.adminLogin(admin);
		System.out.println(returnLoginAdmin + "<--returnLoginAdmin");
		LoginMember returnLoginMember = memberService.login(loginMember);
		session.setAttribute("loginMember", returnLoginMember);

		if(returnLoginAdmin == null) {
			// msg 포워딩
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요");
			// 로그인 페이지로 이동
			return "redirect:adminLogin";
		} else { // login 성공
			session.setAttribute("loginAdmin", returnLoginAdmin);
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/home";
		}
	}
}
