package com.lzy.mtnj.service.system;

import com.lzy.mtnj.model.system.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> findMenuByParentId(String parentId);
    List<Menu> findMenuTree();
    int add(Menu menu);
    Menu findMenuById(String id);
    int update(Menu menu);
    int deleteById(String id);
    List<Menu> findUserMenuTree();
}
