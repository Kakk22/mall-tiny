package com.cyf.malltiny.modules.ums.mapper;

import com.cyf.malltiny.modules.ums.model.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author cyf
 * @since 2020-09-13
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {

    /**
     * 根据用户id获取可访问的资源
     * @param adminId 用户id
     * @return 资源列表
     */
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);
}
