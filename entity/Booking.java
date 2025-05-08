package entity;

import java.util.Date;
import java.util.Objects;

public class Booking {
    private static int idCounter = 1; // Simplified ID counter
    private String bookingId;
    private String userId;
    private String resourceId;
    private Date startTime;
    private Date endTime;
    private double cost;

    public Booking(String userId, String resourceId, Date startTime, Date endTime, double cost) {
        this.bookingId = "B" + (idCounter++); // Simplified ID
        this.userId = userId;
        this.resourceId = resourceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
    }

    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public String getResourceId() { return resourceId; }
    public Date getStartTime() { return startTime; }
    public Date getEndTime() { return endTime; }
    public double getCost() { return cost; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Booking booking = (Booking) obj;
        return bookingId.equals(booking.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }
}