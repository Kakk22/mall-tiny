package com.cyf.malltiny.common.api;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

/**分页数据封装类
 * @author by cyf
 * @date 2020/9/13.
 */
@Getter
@Setter
public class CommonPage<T> {
    public Integer pageNum;
    public Integer pageSize;
    public Integer totalPage;
    public Long total;
    public List<T> list;

    /**
     * 将mybatis-plus 分页结果转换为通用结果
     * @param pageResult
     * @param <T>
     * @return
     */
    public static <T> CommonPage<T> restPage(Page<T> pageResult){
        CommonPage<T> result = new CommonPage<>();
        result.setPageNum(Convert.toInt(pageResult.getCurrent()));
        result.setPageSize(Convert.toInt(pageResult.getSize()));
        result.setTotal(pageResult.getTotal());
        result.setTotalPage(Convert.toInt(pageResult.getTotal()/pageResult.getSize()+1));
        result.setList(pageResult.getRecords());
        return  result;
    }
}
