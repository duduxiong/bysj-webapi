package com.lzy.mtnj.model.system;

import com.lzy.mtnj.infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
@Table(name = "mtnj_menu")
public class Menu extends BaseEntity {
    private String menuCode;
    private String menuName;
    private String icon;
    private String url;
    private String parentId;
    private String remark;
    private Integer sortCode;
    private Boolean status;
    @Transient
    private List<Menu> children;
}
