package com.gdu.cashbook1.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook1.service.AdminService;
import com.gdu.cashbook1.service.CategoryService;
import com.gdu.cashbook1.service.MemberService;
import com.gdu.cashbook1.vo.Admin;
import com.gdu.cashbook1.vo.LoginMember;

@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private CategoryService categoryService;
	
	// 관리자 삭제
	@GetMapping("/removeAdmin")
	public String removeAdmin(HttpSession session, @RequestParam(value="memberId") String memberId) {
		// 관리자 로그인이 되어있지 않으면
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		memberService.removeMemberAdmin(memberId);
		return "redirect:/";
	}
	// 관리자 categoryList
	@GetMapping("/categoryAdmin")
	public String getCategroy(Model model, HttpSession session, @RequestParam(value="currentPage", defaultValue = "1") int currentPage) {
		// 관리자 로그인이 되어있지 않으면
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		System.out.println(currentPage);
		Map<String, Object> map = categoryService.getCategoryList(currentPage);
		
		System.out.println(map.get("list") + "<--getBoardListOne list");
		System.out.println(map.get("lastPage") + "getBoardListOne lastPage");
		model.addAttribute("list", map.get("list"));
		model.addAttribute("lastPage", map.get("lastPage"));
	
		return "categoryAdmin";
	}
	// 관리자 memberList
	@GetMapping("/memberAdmin")
	public String getMemberList(Model model, HttpSession session, @RequestParam(value="currentPage", defaultValue = "1") int currentPage, @RequestParam(value="searchWord", defaultValue = "") String searchWord) {
		// 관리자 로그인이 되어있지 않으면
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		System.out.println(searchWord);
		System.out.println(currentPage);
		
		Map<String, Object> map = memberService.getMemberListAdmin(currentPage, searchWord);
		System.out.println(map.get("list"));
		System.out.println(map.get("lastPage"));
		
		// model에 list 담아서 보내주기
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("currentPage", currentPage); 
		return "memberAdmin";
	}
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
