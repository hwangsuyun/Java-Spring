package com.example.quartz.config;

import com.example.quartz.TestJobA;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

@SuppressWarnings("unused")
@Configuration
public class JobConfig {
    @Autowired
    private Scheduler scheduler; // 쿼츠 스케줄 객체

    @PostConstruct
    public void run() {
        JobDetail detail = runJobDetail(TestJobA.class, new HashMap<>());
        try {
            // 크론형식 지정 후 스케줄 시작
            scheduler.scheduleJob(detail, runJobTrigger("0/10 * * * * ?"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Trigger runJobTrigger(String scheduleExp){
        // 크론 스케줄 사용
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    //public JobDetail runJobDetail(Class job, Map params){
    public JobDetail runJobDetail(Class<TestJobA> job, Map<? extends java.lang.String, ?> params){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);
        // 스케줄 생성
        return newJob(job).usingJobData(jobDataMap).build();
    }
}
