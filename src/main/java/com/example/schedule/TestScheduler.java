package com.example.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("unused")
@Component
public class TestScheduler {
    // 3초 간격으로 반복
    @Scheduled(cron = "0/3 * * * * ?")
    //@Scheduled(fixedDelay = 3000)
    //@Scheduled(fixedRate = 3000)
    //@Scheduled(fixedDelay = 3000, initialDelay = 5000)
    public void testPrintRepeat() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        System.out.println("스케줄러 테스트 " + LocalDateTime.now().format(dtf));
    }
}
