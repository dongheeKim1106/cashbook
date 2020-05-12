package com.gdu.cashbook1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	// index 페이지 요청
	@GetMapping("/index")
	public String index() {
		return "index";
   }
}