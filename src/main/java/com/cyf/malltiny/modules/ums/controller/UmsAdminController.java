package com.cyf.malltiny.modules.ums.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyf.malltiny.common.api.CommonPage;
import com.cyf.malltiny.common.api.CommonResult;
import com.cyf.malltiny.modules.ums.dto.UmsAdminLoginParam;
import com.cyf.malltiny.modules.ums.model.UmsAdmin;
import com.cyf.malltiny.modules.ums.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author cyf
 * @since 2020-09-13
 */
@Api(tags = "UmsAdminController",description = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    @Autowired
    private UmsAdminService umsAdminService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam param){
        String token = umsAdminService.login(param);
        if (token == null){
            return CommonResult.failed("账号密码错误");
        }
        HashMap<String,String> map = new HashMap<>(2);
        map.put("token",token);
        map.put("tokenHead",tokenHead);
        return CommonResult.success(map);
    }

    @ApiOperation("根据姓名或用户名查询列表")
    @GetMapping(value = "/list")
    public CommonResult list(String keyword,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             @RequestParam(defaultValue = "1") Integer pageNum){
        Page<UmsAdmin> list = umsAdminService.list(keyword, pageSize, pageNum);
        return  CommonResult.success(CommonPage.restPage(list));
    }
}

