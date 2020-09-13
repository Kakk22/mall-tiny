package com.cyf.malltiny.modules.ums.mapper;

import com.cyf.malltiny.modules.ums.model.UmsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author cyf
 * @since 2020-09-13
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    List<UmsRole> getRoleList(@Param("adminId") Long adminId);

}
