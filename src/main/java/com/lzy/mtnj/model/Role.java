package com.lzy.mtnj.model;

import com.lzy.mtnj.infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Table(name = "mtnj_role")
public class Role extends BaseEntity {
    private String parentId;
    private String roleName;
    private Boolean status;
    private Integer sortCode;
    private String remark;
    private String addUser;
    private LocalDateTime addDate;
    @Transient
    private List<String> menuIds;
}
