package com.lzy.mtnj.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ReqDic {
    @NotEmpty(message = "日期不能为空")
    private String dateTitle;
    private String remark;
    private int holiday;
}
