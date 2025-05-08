package controller;
import database.Database; // Added missing import
import entity.*;
import java.util.UUID;
import java.util.List; 
import services.*;  
import java.util.Calendar; 
public class BookingController {
    private BookingService bookingService;
    private CalculatorService calculator;

    public BookingController(BookingService service, CalculatorService calc) {
        this.bookingService = service;
        this.calculator = calc; // Ensure proper initialization
    }

    private boolean isTimeSlotAvailable(String resourceId, Calendar startCal, Calendar endCal) {
        long startTime = startCal.getTimeInMillis();
        long endTime = endCal.getTimeInMillis();

        for (Booking existingBooking : bookingService.getBookings()) {
            if (existingBooking.getResourceId().equals(resourceId)) {
                long existingStartTime = existingBooking.getStartTime().getTime();
                long existingEndTime = existingBooking.getEndTime().getTime();

                // Check for overlap
                boolean overlap = !(endTime <= existingStartTime || startTime >= existingEndTime);
                if (overlap) {
                    return false;
                }
            }
        }
        return true;
    }

    public void bookResource(String userId, String resourceId, Calendar startCal, Calendar endCal, int totalHours, double costPerHour) {
        if (totalHours <= 0) {
            System.out.println("Booking Failed: Invalid time duration");
            return;
        }

        // Check if the time slot is available
        if (!isTimeSlotAvailable(resourceId, startCal, endCal)) {
            System.out.println("Booking Failed: Resource is already booked for this time period");
            return;
        }

        // Calculate cost and create booking
        double cost = calculator.calculateCost(totalHours, costPerHour);

        Booking booking = new Booking(userId, resourceId, startCal.getTime(), endCal.getTime(), cost);

        bookingService.addBooking(booking);
        System.out.println("\nBooking Successful!");
        System.out.println("Booking ID: " + booking.getBookingId());
        System.out.println("Total Cost: Rs- " + cost);
    }

    public void viewBookings() {
        List<Booking> bookings = bookingService.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("No active bookings found");
            return;
        }
        System.out.println("\n=== Current Bookings ===");
        for (Booking b : bookings) {
            User user = Database.userRepository.getAllUsers().stream()
                    .filter(u -> u.getId().equals(b.getUserId()))
                    .findFirst()
                    .orElse(null);

            Resource resource = Database.resourceRepository.getAllResources().stream()
                    .filter(r -> r.getId().equals(b.getResourceId()))
                    .findFirst()
                    .orElse(null);

            System.out.println("Booking ID: " + b.getBookingId() +
                             " | User: " + (user != null ? user.getName() : "Unknown") +
                             " | Resource: " + (resource != null ? resource.getName() : "Unknown") +
                             " | Start Time: " + b.getStartTime() +
                             " | End Time: " + b.getEndTime() +
                             " | Cost: Rs- " + b.getCost());
        }
        System.out.println();
    }

    public void viewBookingsForResource(String resourceId) {
        List<Booking> bookings = bookingService.getBookings();
        bookings.stream()
                .filter(b -> b.getResourceId().equals(resourceId))
                .forEach(b -> System.out.println("  Booked from " + b.getStartTime() + " to " + b.getEndTime()));
    }

    public void cancelBooking(String userId, String bookingId) {
        List<Booking> bookings = bookingService.getBookings();
        Booking bookingToCancel = bookings.stream()
            .filter(b -> b.getBookingId().equals(bookingId) && b.getUserId().equals(userId))
            .findFirst()
            .orElse(null);

        if (bookingToCancel == null) {
            System.out.println("Cancellation Failed: Invalid booking ID or unauthorized access");
            return;
        }

        bookingService.removeBooking(bookingToCancel);
        System.out.println("Booking Cancelled Successfully: ID " + bookingId);
    }
}