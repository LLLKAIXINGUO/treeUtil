package com.lym;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lym.vo.AdminAuthVO;
import com.lym.vo.MenuAndButtonVO;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

//权限树调用方法工具
public class AuthTreeUtil {
    public static List<MenuAndButtonVO> buildMenuAndButtonTrees(List<AdminAuth> menus,
                                                            List<AdminAuth> buttons){
        Map<Integer,List<AdminAuth>> pid2menus = menus.stream().collect(Collectors.groupingBy(AdminAuth::getAuthPid));
        List<AdminAuth> firstMenus = pid2menus.get(-1);
        Comparator comparator = new Comparator<AdminAuth>() {
            @Override
            public int compare(AdminAuth o1, AdminAuth o2) {
                //返回值为int类型，大于0为正序，小于0为逆序
                return o1.getAuthSort()-o2.getAuthSort();
            }
        };
        Collections.sort(firstMenus,comparator);
        Map<Integer,List<AdminAuth>> menu2Buttons = new HashMap<>();
        if(CollectionUtils.isNotEmpty(buttons)){
            menu2Buttons = buttons.stream().collect(Collectors.groupingBy(AdminAuth::getAuthPid));
        }
        List<MenuAndButtonVO> menuAndButtons = new ArrayList<>(firstMenus.size());
        for (AdminAuth adminAuth : firstMenus) {
            MenuAndButtonVO menuAndButtonVO = new MenuAndButtonVO();
            BeanUtils.copyProperties(adminAuth,menuAndButtonVO);
            menuAndButtonVO.setList(getChildMenuAndButton(
                    adminAuth.getAuthId(),
                    pid2menus,
                    menu2Buttons,
                    comparator
            ));
            menuAndButtons.add(menuAndButtonVO);
        }
        return menuAndButtons;
    }

    private static List<MenuAndButtonVO> getChildMenuAndButton(Integer pid,
                                                         Map<Integer, List<AdminAuth>> pid2menus,
                                                         Map<Integer, List<AdminAuth>> menu2Buttons,
                                                         Comparator comparator) {
        List<AdminAuth> currentMenus = pid2menus.get(pid);
        if(CollectionUtils.isEmpty(currentMenus)){
            return new ArrayList<>();
        }
        List<MenuAndButtonVO> menus = new ArrayList<>(currentMenus.size());
        Collections.sort(currentMenus,comparator);
        for (AdminAuth adminAuth : currentMenus) {
            MenuAndButtonVO menuAndButtonVO = new MenuAndButtonVO();
            BeanUtils.copyProperties(adminAuth,menuAndButtonVO);
            menuAndButtonVO.setButtonVOS(getButtonVOS(menu2Buttons.get(adminAuth.getAuthId())));
            menuAndButtonVO.setList(getChildMenuAndButton(
                    adminAuth.getAuthId(),
                    pid2menus,
                    menu2Buttons,
                    comparator
            ));
            menus.add(menuAndButtonVO);
        }
        return menus;
    }

    private static List<AdminAuthVO> getButtonVOS(List<AdminAuth> buttonDOS) {
        if(CollectionUtils.isEmpty(buttonDOS)){
            return new ArrayList<>();
        }
        List<AdminAuthVO> vos = new ArrayList<>();
        for (AdminAuth buttonDO : buttonDOS) {
            AdminAuthVO buttonVO = new AdminAuthVO();
            vos.add(buttonVO);
        }
        return vos;
    }
}
