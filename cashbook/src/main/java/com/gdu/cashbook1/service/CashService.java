package com.gdu.cashbook1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.CashMapper;
import com.gdu.cashbook1.vo.Cash;

@Service
@Transactional
public class CashService {
	@Autowired
	private CashMapper cashMapper;
	
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
