package com.lzy.mtnj.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.util.Date;

@Getter
@Setter
public class ReqLinkman {
    private int district;
    private int property;
    private int category;
    private String bank;
    private String linkManName;
    private String cellPhone;
    private String workTel;
    private String email;
    private String department;
    private String ownerId;
    private Boolean districtOrder;
    private String districtOrderType;
    private Boolean bankOrder;
    private String bankOrderType;
    private Boolean departmentOrder;
    private String departmentOrderType;
    private int status;
    private int pageIndex;
    private int pageSize;
    private Date startDate;
    private Date endDate;
}
