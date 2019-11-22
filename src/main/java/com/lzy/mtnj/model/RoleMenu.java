package com.lzy.mtnj.model;

import com.lzy.mtnj.infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "mtnj_role_menu")
public class RoleMenu  extends BaseEntity {
    private String menuId;
    private String roleId;
    private LocalDateTime addDate;
    private String addUser;
}
