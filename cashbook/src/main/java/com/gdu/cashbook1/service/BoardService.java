package com.gdu.cashbook1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.BoardMapper;
import com.gdu.cashbook1.mapper.CommentMapper;
import com.gdu.cashbook1.vo.Board;

@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private CommentMapper commentMapper;
	
	// 다음게시물
	public int nextBoardNo(int boardNo) {
		return boardMapper.nextBoardNo(boardNo);
	}
	// 이전게시물 boardNo 받기
	public int previousBoradNo(int boardNo) {
		return boardMapper.previousBoradNo(boardNo);
	}
	// 게시판 수정
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
	// 게시판 삭제 시 댓글도 삭제
	public void removeBoard(int boardNo) {
		commentMapper.deleteCommentByBoard(boardNo);
		boardMapper.deleteBoard(boardNo);
	}
	// 게시판 추가
	public int addBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
	// 게시판 상세보기
	public Board getBoardListOne(int boardNo) {
		return boardMapper.selectBoardOne(boardNo);
	}
	// 게시판 리스트
	public Map<String, Object> getBoardList(int currentPage, String searchWord) {
		// 디버깅
		System.out.println(searchWord);
  
		// 페이징을 위해서 필요한 값들을 map에 담아서 보내주기.
		int rowPerPage = 10;
		int beginRow = (currentPage-1)*rowPerPage;
		//일회용 맵
		Map<String, Object> map = new HashMap<>(); 
		map.put("searchWord", searchWord);
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		
		// 라스트페이지 출력
		int totalRow = boardMapper.getTotalRow(searchWord);
		int lastPage = totalRow/rowPerPage;
		if(totalRow%rowPerPage != 0) {
		   lastPage += 1;
		}
		  
		// list랑 라스트페이지 맵에 담아서 리턴
		List<Board> list = boardMapper.selectBoardList(map);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("list", list);
		map2.put("lastPage", lastPage);
		
		return map2;
	}
}
