package com.gdu.cashbook1.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook1.service.CashService;
import com.gdu.cashbook1.vo.Cash;
import com.gdu.cashbook1.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	
	// 오늘날짜 가계부 리스트 페이지 요청
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(Model model, HttpSession session) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// 로그인 아이디 받아오는 것
		String loginMember = ((LoginMember)session.getAttribute("loginMember")).getMemberId();;
		// 오늘 날짜 받아오는 것
		Date today = new Date();
		// 날짜 형식을 원하는 문자열 형태로 변경
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strToday = sdf.format(today);
		System.out.println(strToday + "<-- CashController:getCashListByDate strToday");
		// loginMember 로그인 아이디와 날짜 타입 받아야한다 date("yyyy-mm-dd")
		Cash cash = new Cash();
		cash.setMemberId(loginMember);
		cash.setCashDate(strToday);
		List<Cash> cashList = cashService.getCashListByDate(cash);
		model.addAttribute("cashList", cashList);
		model.addAttribute("today", strToday);
		for(Cash c : cashList) {
			System.out.println(c);
		}
		return "getCashListByDate";
	}
}
