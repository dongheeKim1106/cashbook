package com.gdu.cashbook1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.MemberMapper;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;

@Service
@Transactional
public class MemberService {
	// 의존객체 자동주입
	@Autowired
	private MemberMapper memberMapper;
	// login
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	
	// member 추가
	public int addMember(Member member) {
		return memberMapper.insertMember(member);
	}
}
