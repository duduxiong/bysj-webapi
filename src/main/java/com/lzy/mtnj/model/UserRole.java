package com.lzy.mtnj.model;

import com.lzy.mtnj.infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "mtnj_user_role")
public class UserRole  extends BaseEntity {
    private String userId;
    private String roleId;
    private LocalDateTime addDate;
    private String addUser;
}
