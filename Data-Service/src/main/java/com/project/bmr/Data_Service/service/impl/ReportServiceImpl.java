package com.project.bmr.Data_Service.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.project.bmr.Data_Service.client.UserServiceClient;
import com.project.bmr.Data_Service.dto.AdminResponse;
import com.project.bmr.Data_Service.dto.CategoryCount;
import com.project.bmr.Data_Service.repository.ProductRepository;
import com.project.bmr.Data_Service.service.MailService;
import com.project.bmr.Data_Service.service.ReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl
        implements ReportService {

    private final ProductRepository repository;

    private final UserServiceClient userClient;

    private final MailService mailService;

    @Override
    public void sendWeeklyReport() {

        long totalProducts =
                repository.count();

        long lowStockProducts =
                repository.countByQuantityLessThan(
                        10
                );

        List<CategoryCount> categories =
                repository.categoryStats();

        StringBuilder report =
                new StringBuilder();

        report.append(
                "Weekly Product Report\n\n"
        );

        report.append(
                "Total Products : "
        ).append(totalProducts)
         .append("\n");

        report.append(
                "Low Stock Products : "
        ).append(lowStockProducts)
         .append("\n\n");

        report.append(
                "Category Breakdown\n"
        );

        for (CategoryCount category
                : categories) {

            report.append(
                    category.getCategory()
            )
            .append(" : ")
            .append(
                    category.getTotal()
            )
            .append("\n");
        }

        List<AdminResponse> admins =
                userClient.getAllAdmins();

        admins.forEach(admin ->

                mailService.sendMail(
                        admin.getEmail(),
                        "Weekly Product Report",
                        report.toString()
                )
        );
    }
}