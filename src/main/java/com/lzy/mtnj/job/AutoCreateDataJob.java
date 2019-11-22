package com.lzy.mtnj.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AutoCreateDataJob {

    @Scheduled(cron = "00 00 03 * * ?")
    public void CreateEmptyData() throws Exception {
        //TODO 执行内容
    }
}
