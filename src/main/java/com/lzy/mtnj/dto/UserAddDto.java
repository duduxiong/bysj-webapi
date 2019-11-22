package com.lzy.mtnj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddDto {
    private String id;
    private String userAccount;
    private String password;
    private String userName;
    private String cellPhone;
    private String email;
    private int sex;
    private String parentId;
    private String parentName;
    private String roleId;
    private String roleName;

    private int pageIndex;
    private int pageSize;
}
