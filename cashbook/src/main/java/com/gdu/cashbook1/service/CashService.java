package com.gdu.cashbook1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.CashMapper;
import com.gdu.cashbook1.mapper.CategoryMapper;
import com.gdu.cashbook1.vo.Cash;
import com.gdu.cashbook1.vo.Category;
import com.gdu.cashbook1.vo.DayAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired
	private CashMapper cashMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	
	// cash 수정
	public int modifyCash(Cash cash) {
		return cashMapper.updateCash(cash);
	}
	// cash 한개 리스트
	public Cash getCashOne(int cashNo) {
		return cashMapper.selectCashOne(cashNo);
	}
	// 카테고리 리스트
	public List<Category> getCategoryList() {
		return categoryMapper.selectCategoryList();
	}
	// 가계부 추가
	public int addCash(Cash cash) {
		return cashMapper.insertCash(cash);
	}
	// 달력에 일별 합계 돈
	public List<DayAndPrice> getDayAndPrice(String memberId, int year, int month) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);
		return cashMapper.selectDayAndPriceList(map);
	}
	
	// 가계부 삭제
	public int removeCash(int cashNo) {
		return cashMapper.deleteCash(cashNo);
	}
	// 오늘날짜의 가계부 리스트 + 합계 구하기
	public Map<String, Object> getCashListByDate(Cash cash) {
		List<Cash> cashList = cashMapper.selectCashListByDate(cash);
		int cashKindSum = cashMapper.selectCashKindSum(cash);
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("cashKindSum", cashKindSum);
		return map;
	}
}
