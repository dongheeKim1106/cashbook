package com.gdu.cashbook1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Cash;
import com.gdu.cashbook1.vo.DayAndPrice;

@Mapper
public interface CashMapper {
	// 가계부 수정
	public int updateCash(Cash cash);
	// 가계부 한개 리스트 출력
	public Cash selectCashOne(int cashNo);
	// 가계부 입력
	public int insertCash(Cash cash);
	// 달려에 나오는 합계 구하기 map 안에 연도와 월이 들어간다
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map);
	// 가계부 목록 삭제
	public int deleteCash(int cashNo);
	// 값 합계 구하기
	public int selectCashKindSum(Cash cash);
	// 특정 회원의(로그인사용자) 오늘 날짜 가계부 리스트
	public List<Cash> selectCashListByDate(Cash cash);
}
