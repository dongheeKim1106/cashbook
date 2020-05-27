package com.gdu.cashbook1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.AdminMapper;
import com.gdu.cashbook1.vo.Admin;

@Service
@Transactional
public class AdminService {
	@Autowired
	private AdminMapper adminMapper;
	
	// adminLogin
	public Admin adminLogin(Admin admin) {
		return adminMapper.selectLoginAdmin(admin);
	}
}
