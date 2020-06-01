package com.gdu.cashbook1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.CategoryMapper;
import com.gdu.cashbook1.vo.Category;

@Service
@Transactional
public class CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;
	
	// 카테고리 수정
	public int modifyCategory(String categoryName) {
		return categoryMapper.updateCategory(categoryName);
	}
	// 카테고리 리스트 한개
	public Category getCategoryListOne(String categoryName) {
		return categoryMapper.selectCategoryOne(categoryName);
	}
	// 카테고리 삭제
	public int removeCategory(String categoryName) {
		return categoryMapper.deleteCategory(categoryName);
	}
	// 카테고리 입력
	public int addCategory(String categoryName) {
		return categoryMapper.insertCategory(categoryName);
	}
	// 카테고리 리스트
	public Map<String, Object> getCategoryList(int currentPage) {
		int rowPerPage = 5;
		int beginRow = (currentPage-1)*rowPerPage;
		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		
		// lastPage 출력
		int totalRow = categoryMapper.getTotalRow();
		int lastPage = totalRow/rowPerPage;
		if(totalRow%rowPerPage != 0) {
			lastPage += 1;
		}
		List<Category> list = categoryMapper.selectCategory(map);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("list", list);
		map2.put("lastPage", lastPage);
		
		return map2;
	}
}
