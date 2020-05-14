package com.gdu.cashbook1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.MemberMapper;
import com.gdu.cashbook1.mapper.MemberidMapper;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;
import com.gdu.cashbook1.vo.Memberid;

@Service
@Transactional
public class MemberService {
	// 의존객체 자동주입
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberidMapper memberidMapper;
	
	// 회원 정보 수정
	public int modifyMember(Member member) {
		return memberMapper.updateMember(member);
	}
	// 회원 탈퇴
	public void removeMember(LoginMember loginMember) {
		// 트랜잭션
		// 삽입
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);
		// 삭제
		memberMapper.deleteMember(loginMember);
	}
	// 회원탈퇴 가능 여부
	public String checkMemberPw(String memberPw) {
		return memberMapper.selectMemberPw(memberPw);
	}
	// 회원정보 하나만 보여주기
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	// 아이디 비밀번호 보여주기
	public Member getMemberIdPw(String memberPhone) {
		return memberMapper.selectMemberIdPw(memberPhone);
	}
	// 아이디 비밀번호를 찾기위해 비밀번호 있나 확인
	public String findMember(String memberPhone) {
		return memberMapper.findMember(memberPhone);
	}
	// 아이디 중복체크
	public String checkMemberId(String memberIdCheck) {
		// id가 있으면 id가 리턴되고 없으면 null이 리턴된다
		return memberMapper.selectMemberId(memberIdCheck);
	}
	
	// login
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	
	// member 추가
	public int addMember(Member member) {
		return memberMapper.insertMember(member);
	}
}
