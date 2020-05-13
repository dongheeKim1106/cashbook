package com.gdu.cashbook1.vo;

// 관리 하기 편하기 위해 따로 login vo 설정
public class LoginMember {
	private String memberId;
	private String memberPw;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	
	@Override
	public String toString() {
		return "LoginMember [memberId=" + memberId + ", memberPw=" + memberPw + "]";
	}
}
