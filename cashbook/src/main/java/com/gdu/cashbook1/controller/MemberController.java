package com.gdu.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook1.service.MemberService;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;

@Controller
public class MemberController {
	// 의존객체 자동 주입
	@Autowired
	private MemberService memberService;
	
	// login폼 요청
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	// login액션
	@PostMapping("/login")
	public String login(LoginMember loginMember, HttpSession session) { // 메게변수로 session을 받아온다
		System.out.println(loginMember);
		// session 사용
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println(returnLoginMember + "<--returnLoginMember");
		// login 실패
		if(returnLoginMember == null) {
			// 로그인 페이지로 이동
			return "redirect:/login";
		} else { // login 성공
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/index";
		}
	}
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// session 초기화
		session.invalidate();
		return "redirect:/index";
	}
	
	// 회원가입폼 요청
	@GetMapping("/addMember")
	public String addMember() {
		return "addMember";
	}
	// member 테이블에 데이터 삽입 후 index페이지로 이동
	@PostMapping("/addMember")
	public String addMember(Member member) { // 칼럼명이랑 같은 도메인 객체
		System.out.println(member);
		memberService.addMember(member);
		return  "redirect:/index";
	}
}
