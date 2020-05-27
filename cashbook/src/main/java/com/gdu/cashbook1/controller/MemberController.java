package com.gdu.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook1.service.AdminService;
import com.gdu.cashbook1.service.MemberService;
import com.gdu.cashbook1.vo.Admin;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;
import com.gdu.cashbook1.vo.MemberForm;

@Controller
public class MemberController {
	// 의존객체 자동 주입
	@Autowired
	private MemberService memberService;
	@Autowired
	private AdminService adminService;
	// 비밀번호 찾기
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		// 로그인중일 때
		if(session.getAttribute("loginMember") != null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		return "findMemberPw";
	}
	// 비밀번호 찾기 액션
	@PostMapping("/findMemberPw")
	public String findMemberPw(Model model, HttpSession session, Member member) {
		// 로그인중일 때
		if(session.getAttribute("loginMember") != null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		int row = memberService.getMemberPw(member);
		String msg="아이디와 메일을 확인하세요";
		if(row == 1) {
			msg = "비밀번호를 입력한 메일로 전송하였습니다";
		}
		model.addAttribute("msg", msg);
		return "memberPwView";
	}
	// 아이디 찾기 페이지 요청
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		// 로그인중일 때
		if(session.getAttribute("loginMember") != null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		return "findMemberId";
	}
	// 아이디 찾기 액션
	@PostMapping("/findMemberId")
	public String findMemberId(Model model, HttpSession session, Member member) {
		// 로그인중일 때
		if(session.getAttribute("loginMember") != null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		String memberIdPart = memberService.getMemberIdByMember(member);
		System.out.println(memberIdPart);
		model.addAttribute("memberIdPart", memberIdPart);
		return "memberIdView";
	}
	// 수정폼 요청
	@GetMapping("/modifyMember")
	public String modifyMember(Model model, HttpSession session) {
		// 로그인중이 아닐때
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// 로그인된 memberId값 형 변환
		Member member = memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		System.out.println(member);
		model.addAttribute("member", member);
		return "modifyMember";
	}
	// 수정 액션
	@PostMapping("/modifyMember")
	public String modifyMember(HttpSession session, MemberForm memberForm) {
		// 로그인중이 아닐때
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		System.out.println(memberForm + "<-- memberForm");
		MultipartFile mf = memberForm.getMemberPic();
		String originName = mf.getOriginalFilename();
		System.out.println(originName);
		if(memberForm.getMemberPic() != null && !originName.equals("")) {
			// 파일은 png, jpg, gif만 사용가능
			if(!memberForm.getMemberPic().getContentType().equals("image/png") && !memberForm.getMemberPic().getContentType().equals("image/jpg") && !memberForm.getMemberPic().getContentType().equals("image/gif")) {
				return "redirect:/modifyMember?imgMsg=n";
			}
		}
		memberService.modifyMember(memberForm);
		return "redirect:/memberInfo";
	}
	// 비밀번호 입력 화면 요청(회원탈퇴)
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session) {
		// 로그인중이 아닐때
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		return "removeMember";
	}
	// 회원탈퇴 액션
	@PostMapping("/removeMember")
	public String remobeMember(HttpSession session, @RequestParam("memberPw") String memberPw) {
		// 로그인중이 아닐때
		if(session.getAttribute("loginMember") == null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		LoginMember loginMember = (LoginMember)(session.getAttribute("loginMember"));
		loginMember.setMemberPw(memberPw);
		memberService.removeMember(loginMember);
		// 세션 초기화
		session.invalidate();
		return "redirect:/";
	}
	// 회원정보 하나 보여주기
	@GetMapping("/memberInfo")
	public String memberInfo(Model model, HttpSession session) {
		// 로그인중이 아닐때
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// 형변환
		Member member = memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		System.out.println(member);
		model.addAttribute("member", member);
		return "memberInfo";
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
			model.addAttribute("msg", "사용할수 없는 아이디 입니다.");
		}
		return "addMember";
	}
	
	// login폼 요청
	@GetMapping("/login")
	public String login(HttpSession session) {
		// 로그인 중
		if(session.getAttribute("loginMember") != null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		// 로그인이 아닐시
		return "login";
	}
	// login액션
	@PostMapping("/login")
	public String login(Model model, HttpSession session, LoginMember loginMember, Admin admin) { // 메게변수로 session을 받아온다
		// 로그인 중
		if(session.getAttribute("loginMember") != null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		System.out.println(loginMember);
		// session 사용
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println(returnLoginMember + "<--returnLoginMember");
		Admin returnLoginAdmin = adminService.adminLogin(admin);
		// login 실패
		if(returnLoginMember == null) {
			// msg 포워딩
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요");
			// 로그인 페이지로 이동
			return "login";
		} else { // login 성공
			session.setAttribute("loginMember", returnLoginMember);
			session.setAttribute("loginAdmin", returnLoginAdmin);
			return "redirect:/home";
		}
	}
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 로그인 아닐때
		if(session.getAttribute("loginMember") == null && session.getAttribute("loginAdmin") == null) {
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
		if(session.getAttribute("loginMember") != null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		return "addMember";
	}
	// member 테이블에 데이터 삽입 후 index페이지로 이동
	@PostMapping("/addMember")
	public String addMember(Model model, HttpSession session, MemberForm memberForm) { // 칼럼명이랑 같은 도메인 객체
		// 로그인 중
		if(session.getAttribute("loginMember") != null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		System.out.println(memberForm + "<-- memberForm");
		MultipartFile mf = memberForm.getMemberPic();
		String originName = mf.getOriginalFilename();
		System.out.println(originName);
		if(memberForm.getMemberPic() != null && !originName.equals("")) {
			// 파일은 png, jpg, gif만 사용가능
			if(!memberForm.getMemberPic().getContentType().equals("image/png") && !memberForm.getMemberPic().getContentType().equals("image/jpg") && !memberForm.getMemberPic().getContentType().equals("image/gif")) {
				return "redirect:/addMember?imgMsg=n";
			}
		}
		// System.out.println(member);
		memberService.addMember(memberForm);
		return  "redirect:/index";
	}
}
