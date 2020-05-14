package com.gdu.cashbook1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Memberid;

@Mapper
public interface MemberidMapper {
	public void insertMemberid(Memberid memberid);
}
