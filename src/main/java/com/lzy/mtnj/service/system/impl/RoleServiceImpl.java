package com.lzy.mtnj.service.system.impl;

import com.alibaba.druid.util.StringUtils;
import com.lzy.mtnj.infrastructure.BaseService;
import com.lzy.mtnj.infrastructure.exception.BusinessException;
import com.lzy.mtnj.mapper.system.RoleMapper;
import com.lzy.mtnj.mapper.system.RoleMenuMapper;
import com.lzy.mtnj.model.Role;
import com.lzy.mtnj.model.RoleMenu;
import com.lzy.mtnj.service.system.RoleService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleServiceImpl extends BaseService implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public List<Role> findAllRoles() {
        Example example = new Example(Role.class);
        if(getCurrentUser().getSuperAdmin().intValue()!=1){
            //example.and().andEqualTo("parentId",getCurrentUser().getRoleId());
        }
        return roleMapper.selectByExample(example);
    }

    @Override
    public Role findRoleById(String id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int add(Role role) {
        role.setId(null);
        role.setAddUser(getCurrentUser().getId());
        role.setAddDate(LocalDateTime.now());
        if(role.getSortCode()==null){
            role.setSortCode(1000);
        }
        return roleMapper.insert(role);
    }

    @Override
    public int updateById(Role role) {
        Role entity = roleMapper.selectByPrimaryKey(role.getId());
        if(entity==null){
            throw new BusinessException(0,"角色不存在");
        }
        entity.setRoleName(role.getRoleName());
        entity.setStatus(role.getStatus());
        entity.setSortCode(role.getSortCode());
        entity.setRemark(role.getRemark());
        return roleMapper.updateByPrimaryKey(entity);
    }

    @Override
    public int deleteById(String id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public int accredit(Role role) {
        if(role.getMenuIds()!=null && role.getMenuIds().size()<1){
            Example query = new Example(RoleMenu.class);
            query.createCriteria().andEqualTo("roleId",role.getId());
            roleMenuMapper.deleteByExample(query);
        }else{
            Example delCondtion = new Example(RoleMenu.class);
            delCondtion.createCriteria().
                    andEqualTo("roleId",role.getId()).
                    andNotIn("menuId",role.getMenuIds());
            roleMenuMapper.deleteByExample(delCondtion);
            for (String menuId :role.getMenuIds()){
                Example query = new Example(RoleMenu.class);
                query.createCriteria()
                        .andEqualTo("roleId",role.getId())
                        .andEqualTo("menuId",menuId);
                RoleMenu entity = roleMenuMapper.selectOneByExample(query);
                if(entity==null){
                    RoleMenu nEntity = new RoleMenu();
                    nEntity.setRoleId(role.getId());
                    nEntity.setMenuId(menuId);
                    nEntity.setAddDate(LocalDateTime.now());
                    nEntity.setAddUser(getCurrentUser().getId());
                    roleMenuMapper.insert(nEntity);
                }
            }
        }
        return 1;
    }

    @Override
    public List<RoleMenu> findRoleMenuByRoleId(String roleId) {
        return roleMenuMapper.findRoleMenuByRoleId(roleId);
    }
}
