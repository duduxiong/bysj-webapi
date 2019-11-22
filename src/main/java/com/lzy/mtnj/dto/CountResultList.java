package com.lzy.mtnj.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CountResultList {
    private List<CountResult> curMonthLinkmanAddedNum;
    private List<CountResult> preMonthLinkmanAddedNum;
    private List<CountResult> curWeekLinkmanAddedNum;
    private List<CountResult> preWeekLinkmanAddedNum;

    private List<CountResult> curMonthRecordAddedNum;
    private List<CountResult> preMonthRecordAddedNum;
    private List<CountResult> curWeekRecordAddedNum;
    private List<CountResult> preWeekRecordAddedNum;
}
