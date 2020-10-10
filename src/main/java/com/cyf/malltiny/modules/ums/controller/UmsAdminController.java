package com.cyf.malltiny.modules.ums.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyf.malltiny.common.api.CommonPage;
import com.cyf.malltiny.common.api.CommonResult;
import com.cyf.malltiny.modules.ums.dto.UmsAdminLoginParam;
import com.cyf.malltiny.modules.ums.model.UmsAdmin;
import com.cyf.malltiny.modules.ums.model.UmsRole;
import com.cyf.malltiny.modules.ums.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author cyf
 * @since 2020-09-13
 */
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    @Autowired
    private UmsAdminService umsAdminService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam param) {
        String token = umsAdminService.login(param);
        if (token == null) {
            return CommonResult.failed("账号密码错误");
        }
        HashMap<String, String> map = new HashMap<>(2);
        map.put("token", token);
        map.put("tokenHead", tokenHead);
        return CommonResult.success(map);
    }

    @ApiOperation("根据姓名或用户名查询列表")
    @GetMapping(value = "/list")
    public CommonResult list(String keyword,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             @RequestParam(defaultValue = "1") Integer pageNum) {
        Page<UmsAdmin> list = umsAdminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation("查询用户角色")
    @GetMapping(value = "/role/{adminId}")
    public CommonResult role(@PathVariable Long adminId) {
        List<UmsRole> roleList = umsAdminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping(value = "/{adminId}")
    public CommonResult getItem(@PathVariable Long adminId) {
        UmsAdmin admin = umsAdminService.getById(adminId);
        return CommonResult.success(admin);
    }

    @ApiOperation("修改账号状态")
    @PostMapping(value = "/admin/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        boolean result = umsAdminService.update(id, umsAdmin);
        return CommonResult.success(result);
    }

    @ApiOperation("刷新token")
    @GetMapping(value = "/refreshToken")
    public CommonResult refreshToken(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String result = umsAdminService.refreshToken(token);
        Map<String,String> map = new HashMap<>(2);
        map.put("token",result);
        map.put("tokenHead",tokenHead);
        return CommonResult.success(map);
    }

    @ApiOperation("给用户分配角色")
    @PostMapping(value = "/role/update")
    public CommonResult updateRole(@RequestParam(value = "adminId") Long adminId,
                                   @RequestParam(value = "roleIds") List<Long> roleIds){
        int i = umsAdminService.updateRole(adminId, roleIds);
        return i > 0 ? CommonResult.success(i) : CommonResult.failed();
    }
}

