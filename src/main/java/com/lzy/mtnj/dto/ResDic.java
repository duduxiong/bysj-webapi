package com.lzy.mtnj.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResDic {
    private String id;
    private String dateTitle;
    private String remark;
    private List<String> files;
}
