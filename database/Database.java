package database;

import repository.*;
import entity.*;
import java.util.ArrayList;

public class Database {
    public static UserRepository userRepository = new UserRepository();
    public static ResourceRepository resourceRepository = new ResourceRepository();
    public static BookingRepository bookingRepository = new BookingRepository(new ArrayList<>());

    static {
        // Add dummy resources
        resourceRepository.addResource(new Resource("1", "Conference Room", "Room", 500));
        resourceRepository.addResource(new Resource("2", "Projector", "Equipment", 200));
        resourceRepository.addResource(new Resource("3", "Laptop", "Equipment", 300));

        // Removed dummy bookings
    }
}