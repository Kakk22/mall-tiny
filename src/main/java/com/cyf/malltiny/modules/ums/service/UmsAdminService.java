package com.cyf.malltiny.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyf.malltiny.modules.ums.dto.UmsAdminLoginParam;
import com.cyf.malltiny.modules.ums.model.UmsAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyf.malltiny.modules.ums.model.UmsResource;
import com.cyf.malltiny.modules.ums.model.UmsRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author cyf
 * @since 2020-09-13
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    /**
     * 根据用户id获取用户角色
     * @param adminId 用户id
     * @return  用户角色
     */
    List<UmsRole> getRoleList(Long adminId);

    /**
     * 根据姓名或用户名获取用户列表
     * @param keyword 姓名或用户名
     * @param pageSize 每页大小
     * @param pageNum  当前页
     * @return
     */
    Page<UmsAdmin> list(String keyword,Integer pageSize,Integer pageNum);

    /**
     * 登录
     * @param param 传入账号密码
     * @return token
     */
    String login(UmsAdminLoginParam param);

    /**
     * 根据名字查询用户
     * @param username 用户名
     * @return 用户
     */
    UmsAdmin getUserByUsername(String username);

    /**
     * 根据用户名查询用户 生成UserDetails 为security需要的用户信息
     * @param username 用户名
     * @return 用户
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 根据用户获取可访问的资源列表
     * @param adminId 用户id
     * @return 资源列表
     */
    List<UmsResource> getResources(@Param("adminId") Long adminId);
}
