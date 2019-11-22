package com.lzy.mtnj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfo {
    private String id;
    private String fileName;
    private Long size;
    private String extension;
}
