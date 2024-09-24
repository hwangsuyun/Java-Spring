package com.example.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Component
public class TestJobA extends QuartzJobBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    //protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    protected void executeInternal(JobExecutionContext context) {
        logger.info("10초마다 Job 실행");
    }
}
