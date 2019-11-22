package com.lzy.mtnj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCondition {
    private int currentPageIndex;
    private int pageSize;
    private String title;
    private String remark;
}
