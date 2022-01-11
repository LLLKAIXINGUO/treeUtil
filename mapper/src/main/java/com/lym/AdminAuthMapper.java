package com.lym;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminAuthMapper extends BaseMapper<AdminAuth> {
    /**
     * 根据父id查询子类菜单
     * @param id
     */
    @Select("select * from admin_auth where auth_pid = #{id}")
    List<AdminAuth> selectByPid(Integer id);

    /**
     * 查询所有的菜单
     *
     */
    @Select("select * from admin_auth")
    List<AdminAuth> selectAll();

    /**
     * 查询除了一级菜单以外的菜单
     */
    @Select("select * from admin_auth where auth_pid != -1")
    List<AdminAuth> selectAllNotBase();
}
