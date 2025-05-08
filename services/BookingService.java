package services;

import repository.BookingRepository;
import entity.Booking;
import java.util.List;

public class BookingService {
    private BookingRepository bookingRepo;

    public BookingService(BookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public List<Booking> getBookings() {
        return bookingRepo.getAllBookings();
    }

    public void removeBooking(Booking booking) {
        bookingRepo.removeBooking(booking);
    }

    public void addBooking(Booking booking) {
        bookingRepo.getAllBookings().add(booking);
    }
}
