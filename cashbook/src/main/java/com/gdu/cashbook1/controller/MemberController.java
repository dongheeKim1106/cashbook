package com.gdu.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook1.service.MemberService;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;

@Controller
public class MemberController {
	// 의존객체 자동 주입
	@Autowired
	private MemberService memberService;
	// 아이디 찾기
	@GetMapping("/findMember")
	public String findMember(HttpSession session) {
		// 로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "findMember";
	}
	// 아이디 찾기액션
	@PostMapping("/findMember")
	public String findMember(Model model, HttpSession session, @RequestParam("memberPhone") String memberPhone) {
		System.out.println(memberPhone);
		// 로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		String findMemberPhone = memberService.findMember(memberPhone);
		System.out.println(findMemberPhone);
		if(findMemberPhone == null) {
			// 전화번호가 DB에 없으면
			System.out.println("찾기 불가능");
			model.addAttribute("msg", "아이디가 없습니다");
		} else {
			// 전화번호가 DB에 있으면
			System.out.println("찾기 가능");
			Member member = memberService.getMemberIdPw(memberPhone);
			model.addAttribute("member", member);
			System.out.println(member);
		}
		return "findMember";
	}
	// 아이디 중복체크
	@PostMapping("/checkMemberId")
	public String checkMemberId(Model model, HttpSession session, @RequestParam("memberIdCheck") String memberIdCheck) {
		// 로그인 중
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		String confirmMemberId = memberService.checkMemberId(memberIdCheck);
		System.out.println(confirmMemberId);
		if(confirmMemberId == null) {
			// 값이 없으면 사용할수 있는 아이디
			System.out.println("아이디 사용 가능");
			model.addAttribute("memberIdCheck", memberIdCheck);
			model.addAttribute("msg1", "사용가능한 아이디 입니다.");
		} else {
			// 값이 있으면 중복된 아이디
			System.out.println("아이디 사용 불가");
			model.addAttribute("msg", "사용중인 아이디 입니다.");
		}
		return "addMember";
	}
	
	// login폼 요청
	@GetMapping("/login")
	public String login(HttpSession session) {
		// 로그인 중
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		// 로그인이 아닐시
		return "login";
	}
	// login액션
	@PostMapping("/login")
	public String login(Model model, HttpSession session, LoginMember loginMember) { // 메게변수로 session을 받아온다
		// 로그인 중
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		System.out.println(loginMember);
		// session 사용
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println(returnLoginMember + "<--returnLoginMember");
		// login 실패
		if(returnLoginMember == null) {
			// msg 포워딩
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요");
			// 로그인 페이지로 이동
			return "login";
		} else { // login 성공
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/index";
		}
	}
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 로그인 아닐때
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// session 초기화
		session.invalidate();
		return "redirect:/index";
	}
	
	// 회원가입폼 요청
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		// 로그인 중
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "addMember";
	}
	// member 테이블에 데이터 삽입 후 index페이지로 이동
	@PostMapping("/addMember")
	public String addMember(Member member, HttpSession session) { // 칼럼명이랑 같은 도메인 객체
		// 로그인 중
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		System.out.println(member);
		memberService.addMember(member);
		return  "redirect:/index";
	}
}
