package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Category;

@Mapper
public interface CategoryMapper {
	// 가계부 입력, 수정 select option에 넣을 카테고리 리스트
	public List<Category> selectCategoryList();
}
