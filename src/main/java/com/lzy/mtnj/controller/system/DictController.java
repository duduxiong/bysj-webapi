package com.lzy.mtnj.controller.system;

import com.lzy.mtnj.infrastructure.ResponseResult;
import com.lzy.mtnj.model.system.Dict;
import com.lzy.mtnj.service.system.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    DictService dictService;

    @GetMapping("/find_all_district")
    public ResponseResult<List<Dict>>findAllDistrict(){
        List<Dict> districtList = dictService.findAllDistrict();
        ResponseResult<List<Dict>> responseResult = new ResponseResult<>();
        responseResult.setData(districtList);
        responseResult.setCode(200);
        return responseResult;
    }
    @GetMapping("/find_all_property")
    public ResponseResult<List<Dict>>findAllProperty(){
        List<Dict> districtList = dictService.findAllProperty();
        ResponseResult<List<Dict>> responseResult = new ResponseResult<>();
        responseResult.setData(districtList);
        responseResult.setCode(200);
        return responseResult;
    }
    @GetMapping("/find_all_category")
    public ResponseResult<List<Dict>>findAllCategory(){
        List<Dict> districtList = dictService.findAllCategory();
        ResponseResult<List<Dict>> responseResult = new ResponseResult<>();
        responseResult.setData(districtList);
        responseResult.setCode(200);
        return responseResult;
    }

    @GetMapping("/find_all_record_type")
    public ResponseResult<List<Dict>>findAllFollowRecordType(){
        List<Dict> districtList = dictService.findAllFollowRecordType();
        ResponseResult<List<Dict>> responseResult = new ResponseResult<>();
        responseResult.setData(districtList);
        responseResult.setCode(200);
        return responseResult;
    }
}
