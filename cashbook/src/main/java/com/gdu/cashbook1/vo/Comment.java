package com.gdu.cashbook1.vo;

public class Comment {
	private int commentNo;
	private int boardNo;
	private String memberId;
	private String commentContent;
	
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	@Override
	public String toString() {
		return "Comment [commentNo=" + commentNo + ", boardNo=" + boardNo + ", memberId=" + memberId
				+ ", commentContent=" + commentContent + "]";
	}
	
}
