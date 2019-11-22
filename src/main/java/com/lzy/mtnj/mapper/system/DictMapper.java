package com.lzy.mtnj.mapper.system;

import com.lzy.mtnj.model.system.Dict;
import org.springframework.stereotype.Repository;

@Repository
public interface DictMapper extends tk.mybatis.mapper.common.Mapper<Dict>, tk.mybatis.mapper.common.MySqlMapper<Dict> {
}
