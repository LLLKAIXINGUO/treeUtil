package com.lym.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lym.AdminAuth;
import com.lym.AdminAuthMapper;
import com.lym.service.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthServiceImpl implements AdminAuthService {
    @Autowired
    private AdminAuthMapper adminAuthMapper;

    @Override
    public AdminAuth findOne(int id) {
        QueryWrapper<AdminAuth> wrapper = new QueryWrapper<>();
        wrapper.eq("auth_id",id);
        AdminAuth adminAuth = adminAuthMapper.selectOne(wrapper);
        return adminAuth;
    }
}
