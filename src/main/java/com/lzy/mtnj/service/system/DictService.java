package com.lzy.mtnj.service.system;

import com.lzy.mtnj.model.system.Dict;

import java.util.List;

public interface DictService {
    /**
     * 查询出某个父节点的所以子节点
     * @param category
     * @param parentId
     * @return
     */
    List<Dict> findAll(int category,int parentId);

    /**
     * 查询出所有的区域字典
     * @return
     */
    List<Dict> findAllDistrict();

    /**
     * 查询出所有的性质字典
     * @return
     */
    List<Dict> findAllProperty();

    /**
     * 查询出所有的类别字典
     * @return
     */
    List<Dict> findAllCategory();

    /**
     * 查询出所有的跟单记录类型
     * @return
     */
    List<Dict> findAllFollowRecordType();

    /**
     * 根据字典编码查询
     * @param dictCode
     * @return
     */
    Dict findByDictCode(int category,int dictCode);
    Dict findByDictName(int category,String dictName);
    Dict findDistrictDictByDictCode(int dictCode);
    Dict findDistrictDictByDictName(String dictName);
    Dict findPropertyDictByDictCode(int dictCode);
    Dict findPropertyDictByDictName(String dictName);
    Dict findCategoryDictByDictCode(int dictCode);
    Dict findCategoryDictByDictName(String dictName);
    Dict findFollowRecordTypeDictCode(int dictCode);

}
