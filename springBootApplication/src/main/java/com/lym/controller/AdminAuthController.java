package com.lym.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lym.AdminAuth;
import com.lym.AdminAuthMapper;
import com.lym.service.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hello")
public class AdminAuthController {

    @Autowired
    private AdminAuthService adminAuthService;

    @Autowired
    private AdminAuthMapper adminAuthMapper;

    @GetMapping("/findOne")
    public AdminAuth sout(@RequestParam("authId")Integer authId){
        AdminAuth adminAuth = adminAuthService.findOne(authId);
        return adminAuth;
    }
    @RequestMapping("/getMenuTree")
    public List<AdminAuth> getMenuTree(){
        List<AdminAuth> adminAuthBase = adminAuthMapper.selectByPid(-1);
        List<AdminAuth> adminAuthNotBase = adminAuthMapper.selectAllNotBase();
        for (AdminAuth adminAuth : adminAuthBase) {
            List<AdminAuth> adminAuths = iterateAdminAuths(adminAuthNotBase,adminAuth.getAuthId());
            adminAuth.setList(adminAuths);
        }
        return adminAuthBase;

    }

    /**
     *多级菜单查询方法
     * @param
     * @param pid 父类id
     * @return
     */
    public List<AdminAuth> iterateAdminAuths(List<AdminAuth> adminAuthVOList,Integer pid){
        List<AdminAuth> result = new ArrayList<AdminAuth>();
        for (AdminAuth adminAuth : adminAuthVOList) {
            //获取菜单的id
            Integer authId = adminAuth.getAuthId();
            //获取菜单的父id
            Integer parentid = adminAuth.getAuthPid();

            if(parentid==pid){
                //递归查询当前子菜单的子菜单
                List<AdminAuth> iterateMenu = iterateAdminAuths(adminAuthVOList,authId);
                adminAuth.setList(iterateMenu);
                result.add(adminAuth);
        }
        }
        return result;
    }




}
