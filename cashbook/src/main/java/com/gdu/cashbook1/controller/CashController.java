package com.gdu.cashbook1.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook1.service.CashService;
import com.gdu.cashbook1.vo.Cash;
import com.gdu.cashbook1.vo.Category;
import com.gdu.cashbook1.vo.DayAndPrice;
import com.gdu.cashbook1.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	
	// 가계부 입력 페이지 요청
	@GetMapping("/addCash")
	public String addCash(Model model, HttpSession session, @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		System.out.println(day + "<-- CashController:addCash day");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		System.out.println(year);
		model.addAttribute("year", year);
		List<Category> categoryList = cashService.getCategoryList();
		System.out.println(categoryList);
		model.addAttribute("categoryList", categoryList);
		return "addCash";
	}
	// 월별 가계부 페이지 요청
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(Model model, HttpSession session, @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		Calendar cDay = Calendar.getInstance();
		// System.out.println(cDay.get(Calendar.MONTH) + 1);
		
		if(day == null) {
			day = LocalDate.now();
		} else {
			// day -- cDay 변환
			// LocalDate -> Calendar
			// 오늘 날짜에서 day값과 동일하게 변경
			cDay.set(day.getYear(), day.getMonthValue()-1, day.getDayOfMonth());
		}
		// 일별 수입지출 총액 받아오기
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		System.out.println(memberId + "<-- CashController.getCashListByMonth : memberId");
		int year = cDay.get(Calendar.YEAR);
		System.out.println(year + "<-- CashController.getCashListByMonth : year");
		int month = cDay.get(Calendar.MONTH)+1;
		System.out.println(month + "<-- CashController.getCashListByMonth : month");
		List<DayAndPrice> dayAndPriceList = cashService.getDayAndPrice(memberId, year, month);
		System.out.println(dayAndPriceList);
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// 0. 오늘 LocalDate타입 1. 오늘이 Calendar 무슨달 2. 이번달의 마지막 일 3. 이번달 1일의 요일
		model.addAttribute("day", day);
		// 지금 년도
		model.addAttribute("dayAndPriceList", dayAndPriceList);
		model.addAttribute("year", cDay.get(Calendar.YEAR));
		// 지금 월
		model.addAttribute("month", cDay.get(Calendar.MONTH)+1);
		// 그 달의 마지막 일
		model.addAttribute("lastDay", cDay.getActualMaximum(Calendar.DATE));
		for(DayAndPrice dp : dayAndPriceList) {
            System.out.println(dp);
        }
		// 날짜를 하나 더 구해서
		Calendar firstDay = cDay;
		// 1로 변경
		firstDay.set(Calendar.DATE, 1);
		// 요일 1=일요일,.......7=토요일
		// cDay.get(Calendar.DAY_OF_WEEK);
		model.addAttribute("fristDayOfWeek", firstDay.get(Calendar.DAY_OF_WEEK));
		System.out.println(firstDay.get(Calendar.DAY_OF_WEEK));
		
		return "getCashListByMonth";
	}
	// cash 리스트 삭제 후 다시 삭제한 날짜리스트로 이동
	@GetMapping("/removeCash")
	public String removeCash(HttpSession session, @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day, int cashNo) {
		if(day == null) {
			day = LocalDate.now();
		}
		System.out.println(day + "<-- CashController.removeCash:day");
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		cashService.removeCash(cashNo);
		return "redirect:/getCashListByDate?day="+day;
	}
	
	// 오늘날짜 가계부 리스트 페이지 요청
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(Model model, HttpSession session, @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(day == null) {
			day = LocalDate.now();
		}
		System.out.println(day);
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// 로그인 아이디 받아오는 것
		String loginMember = ((LoginMember)session.getAttribute("loginMember")).getMemberId();;
		// 연도 받아오기
		// Calendar 인스턴스 생성
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		System.out.println(year);
		model.addAttribute("year", year);
		// 요일 표시
		DayOfWeek dayOfWeek = day.getDayOfWeek();
		model.addAttribute("dayOfWeek", dayOfWeek);
		// 날짜 형식을 원하는 문자열 형태로 변경
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
	    String dday = day.format(formatter); 
		System.out.println(dday + "<-- CashController:getCashListByDate dday");

		// loginMember 로그인 아이디와 날짜 타입 받아야한다 date("yyyy-mm-dd")
		Cash cash = new Cash();
		cash.setMemberId(loginMember);
		cash.setCashDate(dday);
		Map<String, Object> map = cashService.getCashListByDate(cash);
		model.addAttribute("cashList", map.get("cashList"));
		model.addAttribute("cashKindSum", map.get("cashKindSum"));
		model.addAttribute("day", day);
		
		return "getCashListByDate";
	}
}
