package com.lzy.mtnj.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResult<T> {
    private long total;
    private long pages;
    private List<T> data;
}
