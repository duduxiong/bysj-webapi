package com.lzy.mtnj.mapper.system;

import com.lzy.mtnj.model.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


public interface UserMapper extends Mapper<User> {
    User getOne(@Param("id") String id);
}
