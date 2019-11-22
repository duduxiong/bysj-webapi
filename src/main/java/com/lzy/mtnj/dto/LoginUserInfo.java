package com.lzy.mtnj.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
public class LoginUserInfo {
    @NotEmpty(message = "账号不能为空")
    @Length(max = 30,message = "账号长度不能大于30")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    @Length( max= 50,message = "密码长度不能大于50")
    private String password;

//    @NotEmpty(message = "验证码标识不能为空")
//    @Length(max = 32,message = "验证码标识长度不能大于32")
    private String verificationCodeId;

//    @NotEmpty(message = "验证码不能为空")
//    @Length(max = 4,message = "验证码长度不能大于4")
    private String verificationCode;

    private String userId;
    private String name;
    private String[] access;
    private String avator;
    private Boolean superAdmin;
    private String roleId;
    private String email;
    private String cellPhone;
    private String roleName;
}
