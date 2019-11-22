package com.lzy.mtnj.mapper.system;

import com.lzy.mtnj.model.system.Menu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuMapper extends Mapper<Menu> {
    /**
     * 自定义多表联查的方法
     * @param userId
     * @param parentId
     * @param superAdmin
     * @return
     */
    List<Menu> getUserMenu(@Param("userId") String userId, @Param("parentId") String parentId,@Param("superAdmin") Integer superAdmin);
}
