package com.lzy.mtnj.controller;

import com.lzy.mtnj.dto.LoginUserInfo;
import com.lzy.mtnj.infrastructure.BaseController;
import com.lzy.mtnj.infrastructure.ResponseHelper;
import com.lzy.mtnj.infrastructure.ResponseResult;
import com.lzy.mtnj.infrastructure.shiro.JWTUtil;
import com.lzy.mtnj.infrastructure.shiro.UserInfo;
import com.lzy.mtnj.infrastructure.util.ImageUtil;
import com.lzy.mtnj.infrastructure.util.ValidatorUtil;
import com.lzy.mtnj.model.user.User;
import com.lzy.mtnj.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class HomeController extends BaseController {
    @Autowired
    UserService userService;

//    @ApiParam("登陆接口")
    @PostMapping("/login")
    public ResponseResult<UserInfo> login(@RequestBody @Valid LoginUserInfo userInfo, BindingResult br) throws Exception {
        ResponseResult<UserInfo> result = new ResponseResult<>();
        if(br.hasErrors()){
            return ResponseHelper.ERROR(null,ValidatorUtil.getAllError(br));
        }
//        启用验证码时 讲下边的代码取消注释
//        Object verificationCode = getTempCache().get(userInfo.getVerificationCodeId());
//        if(verificationCode==null || verificationCode.toString().trim().equals("")){
//            result.setCode(1);
//            result.setMsg("验证码已过期，请重新输入");
//            return result;
//        }else if(!verificationCode.toString().trim().equals(userInfo.getVerificationCode().toUpperCase())){
//            result.setCode(1);
//            result.setMsg("验证码错误,请重新输入");
//            getTempCache().remove(userInfo.getVerificationCodeId());
//            return result;
//        }
//        getTempCache().remove(userInfo.getVerificationCodeId());
        UserInfo token = userService.login(userInfo.getUserName(), userInfo.getPassword());
        return ResponseHelper.OK(token);
    }

//    @ApiParam("验证码生成")
    @GetMapping("/verificationCode/{code}")
    public void getCheckCode(@PathVariable("code") String verificationCodeId, HttpServletResponse response) {
        if(StringUtil.isNotEmpty(verificationCodeId)) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            String code = ImageUtil.drawImg(output);
            getTempCache().put(verificationCodeId,code);
            try {
                ServletOutputStream out = response.getOutputStream();
                output.writeTo(out);
            } catch (IOException e) {

            }
        }
    }

//    @ApiParam("注销接口")
    @PostMapping("/logout")
    public ResponseResult<String>loginOut(){
        if(getCurrentUser()!=null) {
            getLoginCache().remove(getCurrentUser().getUserName());
            getLoginCache().remove(getCurrentUser().getUserAccount() + "_secret");
        }
        return ResponseHelper.OK("注销成功");
    }

    @GetMapping("/get_info")
    public ResponseResult<LoginUserInfo> getUserInfo(){

        User user = getCurrentUser();
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setUserName(user.getUserAccount());
        loginUserInfo.setName(user.getUserName());
        loginUserInfo.setUserId(user.getUserAccount());
        loginUserInfo.setAvator("https://avatars0.githubusercontent.com/u/20942571?s=460&v=4");
        loginUserInfo.setSuperAdmin(user.getSuperAdmin().intValue()==1?true:false);
        String role = "";

            role ="super_admin";

        String[]access = { role };
        loginUserInfo.setAccess(access);

        loginUserInfo.setEmail(user.getEmail());
        loginUserInfo.setCellPhone(user.getCellPhone());

        ResponseResult<LoginUserInfo> result = new ResponseResult<>();
        result.setData(loginUserInfo);
        result.setCode(200);

        return result;

    }

    @GetMapping("/checktoken")
    public ResponseResult<String>CheckToken(){
        String secret="bb69148b-8828-43ee-97cc-945ae5524e73";
        String userName="郑金强";
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDM2MzEyNDEsInVzZXJuYW1lIjoi6YOR6YeR5by6In0.NOp4_YqUVoF9NlUWQNgHN7NUHWoMOLFaWSvSJ_npQfU";
        Boolean rs = JWTUtil.verify(token,userName,secret);
        return ResponseHelper.OK(String.valueOf(rs));
    }






}
