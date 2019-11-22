package com.lzy.mtnj.service.system.impl;

import com.lzy.mtnj.infrastructure.BaseService;
import com.lzy.mtnj.infrastructure.exception.BusinessException;
import com.lzy.mtnj.mapper.system.MenuMapper;
import com.lzy.mtnj.model.system.Menu;
import com.lzy.mtnj.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MenuServiceImpl extends BaseService implements MenuService {

    @Autowired
    MenuMapper menuMapper;
    @Override
    public List<Menu> findMenuByParentId(String parentId) {
        Example query = new Example(Menu.class);
        query.createCriteria().andEqualTo("parentId",parentId);
        List<Menu> menuList = menuMapper.selectByExample(query);
        return menuList;
    }

    @Override
    public List<Menu> findMenuTree() {
        List<Menu> roots = findMenuByParentId("0");
        if(roots!=null && roots.size()>0){
            for (Menu menu:roots){
                initChildMenu(menu);
            }
            return roots;
        }
        return null;
    }
    private void initChildMenu(Menu menu){
        List<Menu> childMenuList = findMenuByParentId(menu.getId());
        if(childMenuList!=null && childMenuList.size()>0){
            for (Menu childMenu :childMenuList){
                initChildMenu(childMenu);
            }
            menu.setChildren(childMenuList);
        }
    }

    @Override
    public int add(Menu menu) {
        return  menuMapper.insert(menu);
    }

    @Override
    public Menu findMenuById(String id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Menu menu) {
        Menu entity = findMenuById(menu.getId());
        if(entity == null){
            throw new BusinessException(0,"节点不存在");
        }
        if(menu.getSortCode()!=null){
            entity.setSortCode(menu.getSortCode());
        }
        entity.setMenuCode(menu.getMenuCode());
        entity.setMenuName(menu.getMenuName());
        entity.setUrl(menu.getUrl());
        entity.setStatus(menu.getStatus());
        return menuMapper.updateByPrimaryKey(entity);
    }

    @Override
    public int deleteById(String id) {
        Example query = new Example(Menu.class);
        query.createCriteria().andEqualTo("parentId",id);
        List<Menu> menus = menuMapper.selectByExample(query);
        if(menus!=null && menus.size()>0){
            throw new BusinessException(0,"该节点包含子节点，无法删除");
        }
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Menu> findUserMenuTree() {
        return findUserMenuTree(getCurrentUser().getId(),"0");
    }

    private List<Menu> findUserMenuTree(String userId,String parentMenuId){
        List<Menu> menus = menuMapper.getUserMenu(userId,parentMenuId,getCurrentUser().getSuperAdmin());
        if(menus!=null && menus.size()>0){
            for (Menu menu:menus) {
                List<Menu> childMenus = findUserMenuTree(userId,menu.getId());
                if(childMenus!=null && childMenus.size()>0){
                    menu.setChildren(childMenus);
                }
            }
        }
        return menus;
    }
}
