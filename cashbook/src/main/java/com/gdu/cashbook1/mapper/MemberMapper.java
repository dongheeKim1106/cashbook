package com.gdu.cashbook1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;

@Mapper
public interface MemberMapper {
	
	// 회원 탈퇴
	public int deleteMember(String memberPw);
	// 회원탈퇴 가능 유무
	public String selectMemberPw(String memberPw);
	// 회원정보 하나씩 보기
	public Member selectMemberOne(LoginMember loginMember);
	// 전화번호가 있으면 아이디 비밀번호 보여줌
	public Member selectMemberIdPw(String memberPhone);
	// 전화번호가 있나 확인
	public String findMember(String memberPhone);
	// 아이디 중복 체크
	public String selectMemberId(String memberIdCheck);
	// login
	public LoginMember selectLoginMember(LoginMember loginMember);
	// member 추가
	public int insertMember(Member member);
}
