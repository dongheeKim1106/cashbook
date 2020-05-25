package com.gdu.cashbook1.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook1.service.BoardService;
import com.gdu.cashbook1.vo.Board;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/getBoardList")
	public String getBoardList(Model model, HttpSession session, Board board) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		List<Board> boardList = boardService.getBoardList(board);
		System.out.println(boardList);
		model.addAttribute("boardList", boardList);
		
		return "getBoardList";
	}
}
