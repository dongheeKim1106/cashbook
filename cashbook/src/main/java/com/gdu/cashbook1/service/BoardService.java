package com.gdu.cashbook1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.BoardMapper;
import com.gdu.cashbook1.vo.Board;

@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	// 게시판 리스트
	public List<Board> getBoardList(Board board) {
		return boardMapper.selectBoardList(board);
	}
}
