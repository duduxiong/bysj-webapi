package com.lzy.mtnj.service.system.impl;

import com.lzy.mtnj.infrastructure.BaseService;
import com.lzy.mtnj.mapper.system.DictMapper;
import com.lzy.mtnj.model.system.Dict;
import com.lzy.mtnj.service.system.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class DictServiceImpl extends BaseService implements DictService {

    @Autowired
    DictMapper dictMapper;

    @Override
    public List<Dict> findAll(int category, int parentId) {
        Example example = new Example(Dict.class);
        example.and().andEqualTo("category",category);
        example.and().andEqualTo("parentId",parentId);
        return dictMapper.selectByExample(example);
    }

    @Override
    public List<Dict> findAllDistrict() {
        return findAll(1,1);
    }

    @Override
    public List<Dict> findAllProperty() {
        return findAll(3,1);
    }

    @Override
    public List<Dict> findAllCategory() {
        return findAll(2,1);
    }

    @Override
    public List<Dict> findAllFollowRecordType() {
        return findAll(4,1);
    }

    @Override
    public Dict findByDictCode(int category, int dictCode) {
        Example example = new Example(Dict.class);
        example.and().andEqualTo("category",category);
        example.and().andEqualTo("dictCode",dictCode);
        return  dictMapper.selectOneByExample(example);
    }

    @Override
    public Dict findByDictName(int category, String dictName) {
        Example example = new Example(Dict.class);
        example.and().andEqualTo("category",category);
        example.and().andEqualTo("dictName",dictName);
        return  dictMapper.selectOneByExample(example);
    }

    @Override
    public Dict findDistrictDictByDictCode(int dictCode) {
        return findByDictCode(1,dictCode);
    }

    @Override
    public Dict findDistrictDictByDictName(String dictName) {
        return findByDictName(1,dictName);
    }

    @Override
    public Dict findPropertyDictByDictCode(int dictCode) {
        return findByDictCode(3,dictCode);
    }
    @Override
    public Dict findPropertyDictByDictName(String dictName) {
        return findByDictName(3,dictName);
    }
    @Override
    public Dict findCategoryDictByDictCode(int dictCode) {
        return findByDictCode(2,dictCode);
    }
    @Override
    public Dict findCategoryDictByDictName(String dictName) {
        return findByDictName(2,dictName);
    }
    @Override
    public Dict findFollowRecordTypeDictCode(int dictCode) {
        return findByDictCode(4,dictCode);
    }
}
