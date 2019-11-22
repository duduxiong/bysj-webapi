package com.lzy.mtnj.mapper.system;

import com.lzy.mtnj.model.RoleMenu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMenuMapper extends Mapper<RoleMenu> {
    List<RoleMenu> findRoleMenuByRoleId(String roleId);
}
