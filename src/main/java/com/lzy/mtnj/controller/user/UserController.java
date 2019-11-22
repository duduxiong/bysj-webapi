package com.lzy.mtnj.controller.user;

import com.github.pagehelper.Page;
import com.lzy.mtnj.dto.LoginUserInfo;
import com.lzy.mtnj.dto.PageResult;
import com.lzy.mtnj.dto.UserAddDto;
import com.lzy.mtnj.infrastructure.BaseController;
import com.lzy.mtnj.infrastructure.ResponseHelper;
import com.lzy.mtnj.infrastructure.ResponseResult;
import com.lzy.mtnj.infrastructure.exception.BusinessException;
import com.lzy.mtnj.infrastructure.shiro.UserInfo;
import com.lzy.mtnj.infrastructure.util.ValidatorUtil;
import com.lzy.mtnj.model.Role;
import com.lzy.mtnj.model.UserRole;
import com.lzy.mtnj.model.user.User;
import com.lzy.mtnj.service.ModelConverterService;
import com.lzy.mtnj.service.system.RoleService;
import com.lzy.mtnj.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.validation.Valid;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @PostMapping("/login")
    public ResponseResult<UserInfo> login(@RequestBody LoginUserInfo loginUserInfo){
        return ResponseHelper.OK(userService.login(loginUserInfo.getUserName(),loginUserInfo.getPassword()));
    }
    @GetMapping("/info")
    public ResponseResult<UserInfo> Info(){
        User user = getCurrentUser();
        UserInfo userInfo = ModelConverterService.ToUserInfo(user);
       return ResponseHelper.OK(userInfo);
    }
    @PostMapping("/logout")
    public ResponseResult<String>logOut(){
        if(getCurrentUser()!=null) {
            getLoginCache().remove(getCurrentUser().getUserName());
            getLoginCache().remove(getCurrentUser().getUserAccount() + "_secret");
        }
        return ResponseHelper.OK("注销成功");
    }

    @PostMapping("/pagelist")
    public ResponseResult<PageResult<User>> findByPage(User user){
        Page<User> list = userService.findByPage(user);
        list.getResult().forEach(o->{o.setPassword("");});
        PageResult<User> result = new PageResult<>();
        result.setData(list.getResult());
        result.setTotal(list.getTotal());
        result.setPages(list.getPages());
        return ResponseHelper.OK(result);
    }

    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody User user){
        return  ResponseHelper.OK(userService.add(user)==1?"添加成功":"添加失败");
    }

    @PostMapping("/update")
    public ResponseResult<String>update(@RequestBody User user){
        return ResponseHelper.OK(userService.updateUserInfo(user)==1?"更新成功":"更新失败");
    }

    @PostMapping("/delete")
    public  ResponseResult<String>delete(@RequestParam("id")String id){
        return ResponseHelper.OK(userService.delete(id)==1?"删除成功":"删除失败");
    }
    @PostMapping("/author")
    public ResponseResult<String>author(@RequestBody User userRoles){
        return ResponseHelper.OK(userService.authorRoles(userRoles.getId(),userRoles.getRoleList())==1?"授权成功":"授权失败");
    }

    @GetMapping("/roles/{userid}")
    public ResponseResult<List<String>> getUserRoles(@PathVariable("userid") String userId){
        List<UserRole> userRoles = userService.getUserRoles(userId);
        if(userRoles!=null && userRoles.size()>0){
            return ResponseHelper.OK(userRoles.stream().map(ur->{return ur.getRoleId();}).collect(Collectors.toList()));
        }
        return  ResponseHelper.OK(new ArrayList<>());
    }































    @PostMapping("/add_user")
    public ResponseResult<Boolean>addUser(@RequestBody @Valid UserAddDto userDto, BindingResult br) {
        ResponseResult<Boolean> result = new ResponseResult<>();
        if(br.hasErrors()){
            return ResponseHelper.ERROR(null,ValidatorUtil.getAllError(br));
        }
        User user = new User();
        user.setUserAccount(userDto.getUserAccount());
        user.setPassword(userDto.getPassword());
        user.setUserName(userDto.getUserName());
        user.setSex(userDto.getSex());
        user.setCellPhone(userDto.getCellPhone());
        user.setEmail(userDto.getEmail());

        if(StringUtil.isEmpty(userDto.getParentId())) {
            user.setParentId(getCurrentUser().getId());
        }else{
            user.setParentId(userDto.getParentId());
        }
        User parentUser = userService.findById(user.getParentId());
        if(parentUser==null){
            throw new BusinessException(0,"上级账户不存在");
        }else{
            user.setParentName(parentUser.getUserName());
        }
        if(userDto.getRoleId()!=null && userDto.getRoleId().trim()!=""){
            //user.setRoleId(userDto.getRoleId());
            Role role = roleService.findRoleById(userDto.getRoleId().trim());
            if(role==null){
                throw new BusinessException(0,"该角色不存在");
            }
            //user.setRoleName(role.getRoleName());
        }else{
            throw new BusinessException(0,"角色不能为空");
        }



        int addResult = userService.add(user);
        if(addResult>0){
            result.setCode(20000);
            result.setMessage("添加成功");
        }else{
            result.setCode(2);
            result.setMessage("添加失败");
        }
        return  result;

    }

    @PostMapping("/find_page_users")
    public ResponseResult<PageResult<UserAddDto>> findPageUsers(@RequestBody UserAddDto userConditon) {
        User queryCondtion = new User();
        queryCondtion.setPage(userConditon.getPageIndex());
        queryCondtion.setLimit(userConditon.getPageSize());
        queryCondtion.setUserAccount(userConditon.getUserAccount());
        queryCondtion.setUserName(userConditon.getUserName());
        queryCondtion.setParentId(userConditon.getParentId());
        if(userConditon.getRoleId()!=null && !userConditon.getRoleId().trim().equals("0") && !userConditon.getRoleId().trim().equals("")) {
            //queryCondtion.setRoleId(userConditon.getRoleId());
        }
        User currentUser = getCurrentUser();
        if(currentUser.getSuperAdmin().intValue()!=1){
            queryCondtion.setParentId(currentUser.getId());
        }

        Page<User> users = userService.findByPage(queryCondtion);
        List<UserAddDto> userDtoList = new ArrayList<>();
        for(User user:users.getResult()){
            UserAddDto userDto = new UserAddDto();
            userDto.setId(user.getId());
            userDto.setUserAccount(user.getUserAccount());
            userDto.setUserName(user.getUserName());
            userDto.setCellPhone(user.getCellPhone());
            userDto.setEmail(user.getEmail());
            userDto.setSex(user.getSex());
            userDto.setParentId(user.getParentId());
            userDto.setParentName(user.getParentName());
            //userDto.setRoleId(user.getRoleId());
            //userDto.setRoleName(user.getRoleName());
            userDtoList.add(userDto);
        }

        ResponseResult<PageResult<UserAddDto>> pageUserResult = new ResponseResult<>();
        PageResult<UserAddDto> pageResult = new PageResult<>();
        pageResult.setData(userDtoList);
        pageResult.setTotal(users.getTotal());
        pageResult.setPages(users.getPages());
        pageUserResult.setData(pageResult);
        pageUserResult.setCode(200);
        return pageUserResult;
    }

    @PostMapping("/delete_user")
    public ResponseResult<Boolean> deleteUser(@RequestParam("id")String id){
        int delResult = userService.delete(id);
        ResponseResult<Boolean> responseResult = new ResponseResult<>();
        if(delResult>0){
            responseResult.setData(true);
            responseResult.setCode(20000);
            responseResult.setMessage("删除成功");
        }else{
            responseResult.setData(false);
            responseResult.setCode(0);
            responseResult.setMessage("删除失败");
        }
        return  responseResult;
    }

    @GetMapping("/find_user_by_id")
    public ResponseResult<UserAddDto> findUserById(@RequestParam("id")String id){
        User user = userService.findById(id);
        ResponseResult<UserAddDto> responseResult = new ResponseResult<>();
        if(user!=null){
            UserAddDto userAddDto = new UserAddDto();
            userAddDto.setId(user.getId());
            userAddDto.setUserAccount(user.getUserAccount());
            userAddDto.setUserName(user.getUserName());
            userAddDto.setCellPhone(user.getCellPhone());
            userAddDto.setEmail(user.getEmail());
            userAddDto.setSex(user.getSex());
            //userAddDto.setRoleId(user.getRoleId());
            userAddDto.setParentId(user.getParentId());
            return ResponseHelper.OK(userAddDto);
        }
        else{
            return ResponseHelper.ERROR(null,"不存在该用户");
        }
    }

    @PostMapping("/update_user")
    public ResponseResult<UserAddDto> updateUser(@RequestBody UserAddDto userAddDto){
        User user = userService.findById(userAddDto.getId());
        ResponseResult<UserAddDto> responseResult = new ResponseResult<>();
        if(user!=null){
            user.setUserName(userAddDto.getUserName());
            user.setSex(userAddDto.getSex());
            user.setCellPhone(userAddDto.getCellPhone());
            user.setEmail(userAddDto.getEmail());
            if(userAddDto.getParentId()==null || userAddDto.getParentId().trim().length()==0){
                user.setParentId(getCurrentUser().getId());
            }else {
                user.setParentId(userAddDto.getParentId());
            }
            User parentUser = userService.findById(user.getParentId());
            if(parentUser==null){
                throw new BusinessException(0,"上级账户不存在");
            }else{
                user.setParentName(parentUser.getUserName());
            }
            user.setSuperAdmin(0);
            if(userAddDto.getRoleId()!=null && userAddDto.getRoleId().trim()!=""){
                //user.setRoleId(userAddDto.getRoleId());
                Role role = roleService.findRoleById(userAddDto.getRoleId().trim());
                if(role==null){
                    throw new BusinessException(0,"该角色不存在");
                }
                //user.setRoleName(role.getRoleName());
                if(role.getId().equals("1")){
                    user.setParentId(null);
                }
            }else{
                throw new BusinessException(0,"角色不能为空");
            }
            int updateResult = userService.update(user);
            if(updateResult>0){
                return ResponseHelper.ERROR(null,"更新成功");
            }
            else{
                return ResponseHelper.ERROR(null,"更新失败");
            }
        }else{
              return ResponseHelper.ERROR(null,"不存在该用户");
        }

    }

//    @PostMapping("/update_user_info")
//    public ResponseResult<String> updateUserInfo(@RequestBody UserAddDto userAddDto){
//        User currentUser = getCurrentUser();
//        User userInfo = userService.findById(currentUser.getId());
//        userInfo.setId(getCurrentUser().getId());
//        userInfo.setEmail(userAddDto.getEmail());
//        userInfo.setCellPhone(userAddDto.getCellPhone());
//        int result = userService.update(userInfo);
//
//        ResponseResult<String> responseResult = new ResponseResult<>();
//        if(result>0){
//            responseResult.setCode(200);
//            responseResult.setMsg("更新成功");
//        }else{
//            responseResult.setCode(0);
//            responseResult.setMsg("更新失败");
//        }
//        return responseResult;
//    }
//
//    @GetMapping("/find_all_roles")
//    public ResponseResult<List<Role>> findAllRoles(){
//        List<Role> roleList = roleService.findAllRoles();
//        ResponseResult<List<Role>> responseResult = new ResponseResult<>();
//        responseResult.setData(roleList);
//        responseResult.setCode(200);
//        responseResult.setMsg("请求成功");
//        return  responseResult;
//    }
//
//    @GetMapping("/find_all_pm")
//    public ResponseResult<List<User>>findAllBusinessManager(){
//        List<User> users = userService.findAllProjectManager();
//        ResponseResult<List<User>> responseResult = new ResponseResult<>();
//        responseResult.setData(users);
//        responseResult.setCode(200);
//        responseResult.setMsg("请求成功");
//        return  responseResult;
//
//    }
//
//    @GetMapping("/find_children_users")
//    public ResponseResult<List<User>>findChildrenUsers(){
//        ResponseResult<List<User>> responseResult = new ResponseResult<>();
//        List<User> users = userService.findChildrenUsersByParentId(getCurrentUser().getId());
//        User currentUser = getCurrentUser();
//        users.add(0,currentUser);
//        responseResult.setCode(200);
//        responseResult.setData(users);
//        responseResult.setMsg("请求成功");
//        return  responseResult;
//    }
//
//    @GetMapping("/find_all_users")
//    public ResponseResult<List<User>>findAllUsers(){
//        ResponseResult<List<User>> responseResult = new ResponseResult<>();
//        List<User> users = userService.findAllUsers();
//        responseResult.setData(users);
//        responseResult.setCode(200);
//        responseResult.setMsg("请求成功");
//        return  responseResult;
//    }
//
//    @GetMapping("/find_users_by_role")
//    public ResponseResult<List<User>>findUsersByRole(String roleId){
//        ResponseResult<List<User>> responseResult = new ResponseResult<>();
//        List<User> users = userService.findUsersByRole(roleId);
//        responseResult.setData(users);
//        responseResult.setCode(200);
//        responseResult.setMsg("请求成功");
//        return  responseResult;
//    }
//
//    @PostMapping("/reset_password")
//    public ResponseResult<Boolean>resetPassword(@RequestBody UserAddDto userinfo){
//        if(userinfo.getId()==null || userinfo.getId().trim().equals("")){
//            throw new BusinessException(0,"参数错误");
//        }
//        User user = userService.findById(userinfo.getId());
//        if(user==null){
//            throw new BusinessException(0,"该用户不存在");
//        }
//        if(userinfo.getPassword()==null || userinfo.getPassword().trim().equals("")){
//            throw new BusinessException(0,"密码不能为空");
//        }
//        boolean resetResult = userService.resetPassword(userinfo);
//        ResponseResult<Boolean> responseResult = new ResponseResult<>();
//        if(resetResult){
//            responseResult.setCode(200);
//            responseResult.setData(true);
//            responseResult.setMsg("重置密码成功");
//        }
//        else{
//            responseResult.setCode(0);
//            responseResult.setData(false);
//            responseResult.setMsg("重置密码失败");
//        }
//        return  responseResult;
//    }
//
//    @PostMapping("/change_password")
//    public ResponseResult<String>changePassword(@RequestBody @Valid ChangePasswordInfo changePasswordInfo, BindingResult br){
//        ResponseResult<String> result = new ResponseResult<>();
//        if(br.hasErrors()){
//            result.setCode(1);
//            result.setMsg(ValidatorUtil.getAllError(br));
//            return result;
//        }
//        if(!changePasswordInfo.getNewPassword().equals(changePasswordInfo.getNewPasswordCheck())){
//            throw new BusinessException(0,"两次输入的密码不一致");
//        }
//        User currentUser = userService.findById(getCurrentUser().getId());
//        if(!currentUser.getPassword().equals(MD5Util.getMD5Code(changePasswordInfo.getPassword()))){
//            throw new BusinessException(0,"原密码错误");
//        }
//        currentUser.setPassword(MD5Util.getMD5Code(changePasswordInfo.getNewPassword()));
//        int rs = userService.update(currentUser);
//        if(rs>0){
//            result.setCode(200);
//            result.setMsg("修改密码成功");
//        }else{
//            result.setCode(0);
//            result.setMsg("修改密码失败");
//        }
//        return  result;
//
//    }
//    @PostMapping("/check_username")
//    public ResponseResult<Boolean>checkUserName(@RequestBody UserAddDto userInfo){
//        ResponseResult<Boolean> responseResult = new ResponseResult<>();
//        if(userInfo.getUserAccount()!=null && !userInfo.getUserAccount().trim().equals("")){
//           boolean result = userService.checkUserAccountExist(userInfo.getUserAccount().trim());
//           if(result){
//               responseResult.setCode(200);
//               responseResult.setMsg("校验通过");
//               responseResult.setData(true);
//           }else {
//               responseResult.setCode(0);
//               responseResult.setMsg("该账号已存在");
//               responseResult.setData(false);
//           }
//        }else{
//            responseResult.setCode(200);
//            responseResult.setData(true);
//        }
//        return  responseResult;
//    }
//    @GetMapping("/get_current_user")
//    public ResponseResult<UserAddDto>getCurrentUserInfo(){
//        User user = getCurrentUser();
//        User currentUserNew = userService.findById(user.getId());
//        if(currentUserNew==null){
//            throw new BusinessException(0,"用户不存在");
//        }
//        UserAddDto userInfo = new UserAddDto();
//        userInfo.setUserAccount(currentUserNew.getUserAccount());
//        userInfo.setUserName(currentUserNew.getUserName());
//        userInfo.setEmail(currentUserNew.getEmail());
//        userInfo.setCellPhone(currentUserNew.getCellPhone());
//        //userInfo.setRoleName(currentUserNew.getRoleName());
//        ResponseResult<UserAddDto> responseResult = new ResponseResult<>();
//        responseResult.setData(userInfo);
//        responseResult.setCode(200);
//        responseResult.setMsg("请求成功");
//        return  responseResult;
//    }
}
