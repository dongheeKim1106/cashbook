package com.gdu.cashbook1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Comment;

@Mapper
public interface CommentMapper {
	// 회원 탈퇴시 그 회원의 댓글 삭제
	public int deleteCommentByMember(String memberid);
	// 게시판 삭제시 그 게시판에 있는 댓글 모두 삭제
	public int deleteCommentByBoard(int boardNo);
	// 댓글 상세보기
	public Comment selectCommentOne(int commentNo);
	// 댓글 수정
	public int updateComment(Comment comment);
	// 댓글 삭제
	public int deleteComment(int commentNo);
	// 댓글 추가
	public int insertComment(Comment comment);
	// 총 페이지 수 구하기
	public int getTotalRow(int boardNo);
	// 댓글 리스트
	public List<Comment> selectCommentList(Map<String, Object> map);
}
