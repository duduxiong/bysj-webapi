package com.lzy.mtnj.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ChangePasswordInfo {
    @NotEmpty(message = "原密码不能为空")
    private String password;
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
    @NotEmpty(message = "新密码确认不能为空")
    private String newPasswordCheck;
}
