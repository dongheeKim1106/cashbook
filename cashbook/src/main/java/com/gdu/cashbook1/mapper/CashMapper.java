package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Cash;

@Mapper
public interface CashMapper {
	
	// 특정 회원의(로그인사용자) 오늘 날짜 가계부 리스트
	public List<Cash> selectCashListByDate(Cash cash);
}
