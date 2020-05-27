package com.gdu.cashbook1.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook1.service.BoardService;
import com.gdu.cashbook1.service.CommentService;
import com.gdu.cashbook1.vo.Board;
import com.gdu.cashbook1.vo.LoginMember;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private CommentService commentService;
	
	// 게시판 수정 페이지 요청
	@GetMapping("/modifyBoard")
	public String modifyBoard(Model model, HttpSession session, @RequestParam(value="boardNo") int boardNo) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		Board board = boardService.getBoardListOne(boardNo);
		System.out.println(board + "<-- BoardController:modifyBoard board");
		model.addAttribute("board", board);
		
		return "modifyBoard";
	}
	// 게시판 수정 액션
	@PostMapping("/modifyBoard")
	public String modifyBoard(HttpSession session, Board board, @RequestParam(value="boardNo") int boardNo) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		System.out.println(board + "<-- BoardController:modifyBoard board 액션");
		boardService.modifyBoard(board);
		
		return "redirect:/getBoardListOne?boardNo="+boardNo;
	}
	// 게시판 삭제
	@GetMapping("/removeBoard")
	public String removeBoard(HttpSession session, @RequestParam(value="boardNo") int boardNo) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		boardService.removeBoard(boardNo);
		
		return "redirect:/getBoardList";
	}
	// 게시판 입력 페이지 요청
	@GetMapping("/addBoard")
	public String addBoard(HttpSession session) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		return "addBoard";
	}
	// 게시판 입력 액션
	@PostMapping("/addBoard")
	public String addBoard(HttpSession session, Board board) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// memberId session값과 동일
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		System.out.println(memberId + "<--BoardController:addBoard memberId");
		board.setMemberId(memberId);
		boardService.addBoard(board);
		System.out.println(board + "<--BoardController:addBoard board");
		return "redirect:/getBoardList";
	}
	
	// 게시판 상세페이지 요청
	@GetMapping("/getBoardListOne")
	public String getBoardListOne(Model model, HttpSession session, @RequestParam(value="boardNo") int boardNo, @RequestParam(value="currentPage", defaultValue = "1") int currentPage) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// 다음
		int nextBoradNo = boardService.nextBoardNo(boardNo);
		System.out.println(nextBoradNo + "<-- BoardController:getBoardListOne memberId nextBoradNo");
		model.addAttribute("nextBoradNo", nextBoradNo);
		// 이전
		int previousBoradNo = boardService.previousBoradNo(boardNo);
		System.out.println(previousBoradNo + "<-- BoardController:getBoardListOne memberId previousBoradNo");
		model.addAttribute("previousBoradNo", previousBoradNo);
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		System.out.println(memberId + "<-- BoardController:getBoardListOne memberId");
		model.addAttribute("memberId", memberId);
		Board board = boardService.getBoardListOne(boardNo);
		System.out.println(board + "<-- BoardController:getBoardListOne board");
		model.addAttribute("board", board);
		// 댓들 리스트 출력 및 페이징
		System.out.println(currentPage + "<--getBoardListOne currentPage");
		Map<String, Object> map = commentService.getCommentList(currentPage, boardNo);
		System.out.println(boardNo);
		System.out.println(map.get("commentList") + "<--getBoardListOne list");
		System.out.println(map.get("lastPage") + "getBoardListOne lastPage");
		
		model.addAttribute("commentList", map.get("commentList"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("currentPage", currentPage);
		return "getBoardListOne";
	}
	// 게시판 페이지 요청
   @GetMapping("/getBoardList")
   public String getBoardList(Model model, HttpSession session, @RequestParam(value="currentPage", defaultValue = "1") int currentPage, @RequestParam(value="searchWord", defaultValue = "") String searchWord) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		  
		// searchWord 디버깅
		System.out.println(searchWord);
		
		// currentPage받아온 값 디버깅
		System.out.println(currentPage);
		
		// 리스트 디버깅
		Map<String, Object> map = boardService.getBoardList(currentPage, searchWord);
		System.out.println(map.get("list"));
		System.out.println(map.get("lastPage"));
		
		// model에 list 담아서 보내주기
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("currentPage", currentPage); 
		return "getBoardList";
	}
}
