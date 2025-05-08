package services;

import repository.BookingRepository;
import entity.Booking;
import java.io.PrintWriter;
import java.io.File;

public class ReportService {
    private BookingRepository bookingRepo;

    public ReportService(BookingRepository repo) {
        this.bookingRepo = repo;
    }

    public void generateReport() {
        try (PrintWriter writer = new PrintWriter(new File("report.csv"))) {
            writer.println("Booking ID,Resource ID,Cost");
            for (Booking b : bookingRepo.getAllBookings()) {
                writer.println(b.getBookingId() + "," + b.getResourceId() + "," + b.getCost());
            }
            System.out.println("Report generated: report.csv");
        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}
