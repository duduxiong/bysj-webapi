package com.lzy.mtnj.model.system;

import com.lzy.mtnj.infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "mtnj_dict")
public class Dict {
    @Id
    private Integer id;
    private Integer dictCode;
    private String dictName;
    private Integer parentId;
    private Integer category;
    private String remark;
    private String addUser;
    private LocalDateTime addDate;
    private String updateUser;
    private LocalDateTime updateDate;
}
