package com.gdu.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook1.service.CommentService;
import com.gdu.cashbook1.vo.Comment;
import com.gdu.cashbook1.vo.LoginMember;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	// 댓글 수정 페이지 요청
	@GetMapping("/modifyComment")
	public String modifyComment(Model model, HttpSession session, Comment comment, @RequestParam(value="commentNo") int commentNo,  @RequestParam(value="boardNo") int boardNo) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		comment = commentService.getCommentOne(commentNo);
		System.out.println(comment);
		model.addAttribute("comment", comment);
		
		return "modifyComment";
	}
	// 댓글 수정 액션
	@PostMapping("/modifyComment")
	public String modifyComment(HttpSession session, Comment comment, @RequestParam(value="commentNo") int commentNo,  @RequestParam(value="boardNo") int boardNo) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		System.out.println(comment);
		System.out.println(boardNo);
		commentService.modifyComment(comment);
		
		return "redirect:/getBoardListOne?boardNo="+boardNo;
	}
	// 댓글 삭제
	@GetMapping("/removeComment")
	public String removeComment(HttpSession session, @RequestParam(value="commentNo") int commentNo, @RequestParam(value="boardNo") int boardNo) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		System.out.println(boardNo);
		commentService.remove(commentNo);
		return "redirect:getBoardListOne?boardNo="+boardNo;
	}
	// 댓글 입력
	@GetMapping("/addComment")
	public String addComment(HttpSession session, Comment comment, @RequestParam(value="boardNo") int boardNo) {
		// 로그인이 되어있지 않으면
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		comment.setMemberId(memberId);
		comment.setBoardNo(boardNo);
		System.out.println(comment);
		commentService.addComment(comment);
		
		return "redirect:getBoardListOne?boardNo="+boardNo;
	}
}
