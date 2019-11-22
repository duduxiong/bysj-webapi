package com.lzy.mtnj.infrastructure;

import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;

@Getter
@Setter
public abstract class BaseEntity {
    public void nextId() {
        id = java.util.UUID.randomUUID().toString();
    }
    @Id
    @KeySql(genId = UUIdGenId.class)
    private String id;
    @Transient
    private Integer page = 1;
    @Transient
    private Integer limit = 10;
}
