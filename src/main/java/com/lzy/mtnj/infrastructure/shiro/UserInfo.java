package com.lzy.mtnj.infrastructure.shiro;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserInfo {
    private String token;
    private String userName;
    private String avatar;
    private Integer sex;
    private String sexName;
    private String email;
    private String tel;
    private String cellPhone;
    private Boolean status;
    private Integer superAdmin;
    private String lastLoginDate;
    private String lastLoginArea;
}
