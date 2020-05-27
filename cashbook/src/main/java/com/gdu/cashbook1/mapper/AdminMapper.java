package com.gdu.cashbook1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Admin;

@Mapper
public interface AdminMapper {
	// 관리자 로그인
	public Admin selectLoginAdmin(Admin admin);
}
