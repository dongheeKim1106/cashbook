package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Board;

@Mapper
public interface BoardMapper {
	// 게시판 상세보기
	public Board selectBoardOne(int boardNo);
	// 게시판 리스트
	public List<Board> selectBoardList(Board board);
}
