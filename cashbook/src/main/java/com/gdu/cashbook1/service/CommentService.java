package com.gdu.cashbook1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.CommentMapper;
import com.gdu.cashbook1.vo.Comment;

@Service
@Transactional
public class CommentService {
	@Autowired
	private CommentMapper commentMapper;
	
	// 댓글 상세 보기
	public Comment getCommentOne(int commentNo) {
		return commentMapper.selectCommentOne(commentNo);
	}
	// 댓글 수정
	public int modifyComment(Comment comment) {
		return commentMapper.updateComment(comment);
	}
	// 댓글 삭제
	public int remove(int commentNo) {
		return commentMapper.deleteComment(commentNo);
	}
	// 댓글 입력
	public int addComment(Comment comment) {
		return commentMapper.insertComment(comment);
	}
	// 댓글 리스트 페이징
	public Map<String, Object> getCommentList(int currentPage, int boardNo) {
		int rowPerPage = 5;
		int beginRow = (currentPage-1)*rowPerPage;
		System.out.println(boardNo + "<--commentService boardNo");
		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		map.put("boardNo", boardNo);
		// lastPage 출력
		int totalRow = commentMapper.getTotalRow(boardNo);
		int lastPage = totalRow/rowPerPage;
		if(totalRow%rowPerPage != 0) {
			lastPage += 1;
		}
		// list와 lastPage Map에 담는다
		List<Comment> commentList = commentMapper.selectCommentList(map);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("commentList", commentList);
		map2.put("lastPage", lastPage);
		
		return map2;
	}
}
