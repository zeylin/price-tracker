package com.zeylin.pricetracker.controller;

import com.zeylin.pricetracker.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/monthly/{month}")
    public void generateReport(@PathVariable Integer month, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.pdf\"", "REPORT_" + month + "_" + LocalDate.now().toString()));
        response.getOutputStream().write(reportService.generateMonthlyReport(month));
    }

    @GetMapping(value = "/monthlyCategorised/{month}")
    public void generateCategorisedReport(@PathVariable Integer month, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.pdf\"", "REPORT_" + month + "_" + LocalDate.now().toString()));
        response.getOutputStream().write(reportService.generateCategorisedMonthlyReport(month));
    }
}
