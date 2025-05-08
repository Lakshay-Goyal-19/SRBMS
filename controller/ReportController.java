package controller;

import services.ReportService;

public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    public void generateReport() {
        reportService.generateReport();
    }
}
