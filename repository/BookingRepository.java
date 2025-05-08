package repository;

import java.util.List;
import entity.Booking;

public class BookingRepository {
    private List<Booking> bookings;

    public BookingRepository(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    public void removeBooking(Booking booking) {
        if (bookings.contains(booking)) {
            bookings.remove(booking);
        } else {
            System.out.println("Error: Booking not found in the repository.");
        }
    }
}