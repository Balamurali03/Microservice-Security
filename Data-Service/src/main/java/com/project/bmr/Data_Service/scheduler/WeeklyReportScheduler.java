package com.project.bmr.Data_Service.scheduler;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.bmr.Data_Service.service.ReportService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeeklyReportScheduler {

    private final ReportService reportService;

    @Scheduled(
            cron =
            "0 0 9 ? * MON,FRI"
    )
    public void sendWeeklyReport() {

        reportService.sendWeeklyReport();
    }
}