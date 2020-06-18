package com.gdu.cashbook1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Board;

@Mapper
public interface BoardMapper {
	// 회원 탈퇴
	public int deleteBoardByMember(String memberId);
	// 총 페이지수 구하기
	public int getTotalRow(String searchWord);
	// 게시판 수정
	public int updateBoard(Board board);
	// 게시판 삭제
	public int deleteBoard(int boardNo);
	// 게시판 입력
	public int insertBoard(Board board);
	// 게시판 상세보기
	public Board selectBoardOne(int boardNo);
	// 게시판 리스트
	public List<Board> selectBoardList(Map<String, Object> map);
}
