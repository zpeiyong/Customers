package com.dataint.topic.crontab;

import com.dataint.cloud.common.model.Constants;
import com.dataint.topic.service.ISpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpiderManageTasks extends SimpleAsyncTaskExecutor {
    @Autowired
    private ISpiderService spiderService;

//    @Scheduled(cron = "0 0 1,7,13,19 * * ?")
    public void delDisposeSpiders() {
        Long currentTimestamps = System.currentTimeMillis();
        System.out.println("----- [crontab]delDisposeSpiders(" + Constants.DateTimeSDF.format(currentTimestamps)+") -----");

        spiderService.delDisposeProjects();
    }
}
