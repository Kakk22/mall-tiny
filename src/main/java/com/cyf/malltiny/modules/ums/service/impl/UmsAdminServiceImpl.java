package com.cyf.malltiny.modules.ums.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyf.malltiny.common.exception.Asserts;
import com.cyf.malltiny.common.service.RedisService;
import com.cyf.malltiny.domain.AdminUserDetails;
import com.cyf.malltiny.modules.ums.dto.UmsAdminLoginParam;
import com.cyf.malltiny.modules.ums.mapper.UmsAdminLoginLogMapper;
import com.cyf.malltiny.modules.ums.mapper.UmsAdminMapper;
import com.cyf.malltiny.modules.ums.mapper.UmsResourceMapper;
import com.cyf.malltiny.modules.ums.mapper.UmsRoleMapper;
import com.cyf.malltiny.modules.ums.model.UmsAdmin;
import com.cyf.malltiny.modules.ums.model.UmsAdminLoginLog;
import com.cyf.malltiny.modules.ums.model.UmsResource;
import com.cyf.malltiny.modules.ums.model.UmsRole;
import com.cyf.malltiny.modules.ums.service.UmsAdminService;
import com.cyf.malltiny.security.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author cyf
 * @since 2020-09-13
 */
@Service
@AllArgsConstructor
@Slf4j
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {


    private RedisService redisService;

    private UmsResourceMapper umsResourceMapper;

    private PasswordEncoder passwordEncoder;

    private JwtTokenUtil jwtTokenUtil;

    private UmsRoleMapper umsRoleMapper;

    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return umsRoleMapper.getRoleList(adminId);
    }

    /**
     * 根据姓名或用户名查询列表
     * @param keyword 姓名或用户名
     * @param pageSize 每页大小
     * @param pageNum  当前页
     * @return
     */
    @Override
    public Page<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<UmsAdmin> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        //用户名或者是姓名
        queryWrapper.lambda()
                    .like(!StringUtils.isEmpty(keyword),UmsAdmin::getUsername,keyword)
                    .or()
                    .like(!StringUtils.isEmpty(keyword),UmsAdmin::getNickName,keyword);
        return page(page,queryWrapper);
    }

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    @Override
    public UmsAdmin getUserByUsername(String username) {
        UmsAdmin user = (UmsAdmin) redisService.get(username);
        if (user != null) {
            return user;
        }
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsAdmin::getUsername, username);
        List<UmsAdmin> adminList = list(queryWrapper);
        if (!CollectionUtil.isEmpty(adminList)) {
            redisService.set(username, adminList.get(0));
            return adminList.get(0);
        }
        return null;
    }

    /**
     * 根据用户id获取资源列表
     *
     * @param adminId 用户id
     * @return 资源列表
     */
    @Override
    public List<UmsResource> getResources(Long adminId) {
        return umsResourceMapper.getResourceList(adminId);
    }

    /**
     * 登录
     *
     * @param param 传入账号密码
     * @return token
     */
    @Override
    public String login(UmsAdminLoginParam param) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(param.getUsername());
            if (!passwordEncoder.matches(param.getPassword(), userDetails.getPassword())) {
                Asserts.fail("密码错误");
            } else if (!userDetails.isEnabled()) {
                Asserts.fail("账号被禁用");
            } else {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                token = jwtTokenUtil.generateToken(userDetails);
                // TODO: 2020/9/13 插入用户登录信息
                insertLoginLog(param.getUsername());
                log.info("insertLoginLog");
            }
        } catch (AuthenticationException e) {
            log.warn("登录异常：{}",e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     */
    private void insertLoginLog(String username) {
        UmsAdmin user = getUserByUsername(username);
        if (user == null) return;
        UmsAdminLoginLog log = new UmsAdminLoginLog();
        log.setCreateTime(new Date());
        log.setAdminId(user.getId());
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String addr = request.getRemoteAddr();
        log.setIp(addr);
        umsAdminLoginLogMapper.insert(log);
    }

    /**
     * 获取UserDetails
     *
     * @param username 用户名
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsAdmin user = getUserByUsername(username);
        if (user != null) {
            //生成security 需要的用户信息
            return new AdminUserDetails(user, getResources(user.getId()));
        } else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}
