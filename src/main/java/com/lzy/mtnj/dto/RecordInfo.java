package com.lzy.mtnj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class RecordInfo {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;
    private String bank;
    private String department;
    private String position;
    private String linkManName;
    private String addUserName;
    private String content;
    private int recordType;
    private String recordTypeName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String manager;
    private String currentUserId;
    private boolean superAdmin;
    private String roleId;
    private int pageIndex;
    private int pageSize;
}
