package com.gdu.cashbook1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;

@Mapper
public interface MemberMapper {
	// login
	public LoginMember selectLoginMember(LoginMember loginMember);
	// member 추가
	public int insertMember(Member member);
}
