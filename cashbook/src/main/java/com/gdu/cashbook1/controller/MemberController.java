package com.gdu.cashbook1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook1.service.MemberService;
import com.gdu.cashbook1.vo.Member;

@Controller
public class MemberController {
	// 의존객체 자동 주입
	@Autowired
	private MemberService memberService;
	// 회원가입 폼 요청
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
