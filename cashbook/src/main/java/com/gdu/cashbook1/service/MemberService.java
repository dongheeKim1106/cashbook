package com.gdu.cashbook1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook1.mapper.MemberMapper;
import com.gdu.cashbook1.vo.Member;

@Service
public class MemberService {
	// 의존객체 자동주입
	@Autowired
	private MemberMapper memberMapper;
	// member 추가
	public int addMember(Member member) {
		return memberMapper.insertMember(member);
	}
}
