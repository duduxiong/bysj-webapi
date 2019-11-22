package com.lzy.mtnj.controller.system;

import com.lzy.mtnj.infrastructure.ResponseHelper;
import com.lzy.mtnj.infrastructure.ResponseResult;
import com.lzy.mtnj.model.system.Menu;
import com.lzy.mtnj.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    @GetMapping("/list")
    public ResponseResult<List<Menu>> list() {
        List<Menu> menus = menuService.findMenuTree();
        if (menus != null && menus.size() > 0) {
            return ResponseHelper.OK(menus);
        }
        return ResponseHelper.OK(new ArrayList<>());
    }

    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody Menu menu) {
        menu.setSortCode(1000);
        return ResponseHelper.OK(menuService.add(menu) == 1 ? "添加成功" : "添加失败");
    }

    @GetMapping("/{id}")
    public ResponseResult<Menu> findId(@PathVariable("id") String id){
        return ResponseHelper.OK(menuService.findMenuById(id));
    }

    @PostMapping("/update")
    public ResponseResult<String> updateById(@RequestBody Menu menu){
        return ResponseHelper.OK(menuService.update(menu)==1?"更新成功":"更新失败");
    }


    @PostMapping("/delete")
    public ResponseResult<String> deleteById(@RequestParam("id") String id){
        return ResponseHelper.OK(menuService.deleteById(id)==1?"删除成功":"删除失败");
    }

    @GetMapping("/usermenu")
    public ResponseResult<List<Menu>> getUserMenuList(){
        return ResponseHelper.OK(menuService.findUserMenuTree());
    }
}
