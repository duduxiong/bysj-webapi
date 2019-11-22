package com.lzy.mtnj.service.system;

import com.lzy.mtnj.model.Role;
import com.lzy.mtnj.model.RoleMenu;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();
    Role findRoleById(String id);
    int add(Role role);
    int updateById(Role role);
    int deleteById(String id);
    int accredit(Role role);
    List<RoleMenu>findRoleMenuByRoleId(String roleId);
}
