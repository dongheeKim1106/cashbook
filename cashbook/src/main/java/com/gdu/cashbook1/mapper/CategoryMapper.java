package com.gdu.cashbook1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Category;

@Mapper
public interface CategoryMapper {
	// 카테고리 수정
	public int updateCategory(String categoryName);
	// 카테고리 하나만 보여주기
	public Category selectCategoryOne(String categoryName);
	// 카테고리 삭제
	public int deleteCategory(String categoryName);
	// 카테고리 입력
	public int insertCategory(String categoryName);
	// 총 페이지 수 구하기
	public int getTotalRow();
	// 관리자 카테고리 리스트
	public List<Category> selectCategory(Map<String, Object> map);
	// 가계부 입력, 수정 select option에 넣을 카테고리 리스트
	public List<Category> selectCategoryList();
}
