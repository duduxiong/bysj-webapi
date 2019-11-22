package com.lzy.mtnj.controller.system;

import com.lzy.mtnj.infrastructure.ResponseHelper;
import com.lzy.mtnj.infrastructure.ResponseResult;
import com.lzy.mtnj.model.Role;
import com.lzy.mtnj.model.RoleMenu;
import com.lzy.mtnj.model.system.Menu;
import com.lzy.mtnj.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping("/list")
    public ResponseResult<List<Role>> findAll(){
        return ResponseHelper.OK(roleService.findAllRoles());
    }

    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody Role role){
        return ResponseHelper.OK(roleService.add(role)==1?"添加成功":"添加失败");
    }

    @GetMapping("/{id}")
    public ResponseResult<Role> findById(@PathVariable("id") String id){
        return  ResponseHelper.OK(roleService.findRoleById(id));
    }

    @PostMapping("/update")
    public ResponseResult<String> updateById(@RequestBody Role role){
        return ResponseHelper.OK(roleService.updateById(role)==1?"更新成功":"更新失败");
    }

    @PostMapping("/delete")
    public ResponseResult<String> deleteById(String id){
        return ResponseHelper.OK(roleService.deleteById(id)==1?"删除成功":"删除失败");
    }

    @PostMapping("/author")
    public ResponseResult<String> author(@RequestBody Role role){
        return ResponseHelper.OK(roleService.accredit(role)==1?"授权成功":"授权失败");
    }
    @GetMapping("/rolemenu/{roleid}")
    public ResponseResult<List<String>> findRoleMenuByRoleId(@PathVariable("roleid") String roleId){
        List<RoleMenu> roleMenus = roleService.findRoleMenuByRoleId(roleId);
        List<String> menus = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return ResponseHelper.OK(menus);
    }

}
