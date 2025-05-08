import controller.*;
import database.Database;
import entity.*;
import java.util.*;
import services.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService(Database.userRepository);
        UserController userController = new UserController(userService);
        ResourceController resourceController = new ResourceController(
                new ResourceService(Database.resourceRepository));
        BookingController bookingController = new BookingController(new BookingService(Database.bookingRepository),
                new CalculatorService());
        ReportController reportController = new ReportController(new ReportService(Database.bookingRepository));

        // Dummy users
        userController.registerUser(new Admin("Admin", "a@gmail.com", "a123"));
        userController.registerUser(new ResourceManager("1", "Manager", "m@gmail.com", "m123"));
        userController.registerUser(new RegularUser("2", "Regular User", "u@gmail.com", "u123"));

        while (true) {
            System.out.println("\n====================================");
            System.out.println("=== Select Role ===");
            System.out.println("1. Admin");
            System.out.println("2. Manager");
            System.out.println("3. User");
            System.out.println("4. Exit");
            System.out.println("====================================");
            int roleChoice = getMenuChoice(scanner, 4);

            if (roleChoice == 4) {
                System.out.println("Exiting the program. Goodbye!");
                scanner.close();
                System.exit(0);
            }

            System.out.println("\n=== Login ===");
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            User user = userController.login(email, password);
            if (user == null) {
                continue;
            }

            if ((roleChoice == 1 && user instanceof Admin) ||
                    (roleChoice == 2 && user instanceof ResourceManager) ||
                    (roleChoice == 3 && user instanceof RegularUser)) {
                System.out.println("\nLogged in as: " + user.getClass().getSimpleName());
                while (true) {
                    if (user instanceof Admin) {
                        System.out.println("1. Register User");
                        System.out.println("2. Generate Report");
                        System.out.println("3. View All Users");
                        System.out.println("4. Switch Role");
                        System.out.println("5. Exit");
                        int choice = getMenuChoice(scanner, 5);

                        if (choice == 1) {
                            System.out.print("Enter Role (Admin/Manager/User): ");
                            String role = scanner.nextLine().toLowerCase();

                            System.out.print("Name: ");
                            String name = scanner.nextLine();
                            System.out.print("Email: ");
                            String mail = scanner.nextLine();
                            System.out.print("Password: ");
                            String pass = scanner.nextLine();

                            User newUser = switch (role) {
                                case "admin" -> new Admin(name, mail, pass);
                                case "manager" -> new ResourceManager("0", name, mail, pass); // Temporary ID for
                                                                                              // Manager
                                case "user" -> new RegularUser("0", name, mail, pass); // Temporary ID for User
                                default -> null;
                            };

                            if (newUser != null) {
                                boolean success = userController.registerUser(newUser);
                                if (!success) {
                                    System.out.println("Registration failed.");
                                }
                            } else {
                                System.out.println("Invalid role. Registration failed.");
                            }
                        } else if (choice == 2) {
                            reportController.generateReport();
                        } else if (choice == 3) {
                            userController.viewAllUsers();
                        } else if (choice == 4) {
                            break; // to outer loop for login
                        } else if (choice == 5) {
                            System.out.println("Exiting the program. Goodbye!");
                            scanner.close(); // Close scanner
                            System.exit(0);
                        } else {
                            System.out.println("Invalid choice. Try again.");
                        }

                    } else if (user instanceof ResourceManager) {
                        System.out.println("1. Add Resource");
                        System.out.println("2. View Resources");
                        System.out.println("3. Switch Role");
                        System.out.println("4. Exit");
                        int choice = getMenuChoice(scanner, 4);

                        if (choice == 1) {
                            String id, name, type;

                            // Validate ID (should be numeric)
                            while (true) {
                                System.out.print("ID: ");
                                id = scanner.nextLine();
                                if (id.matches("\\d+")) { // Check if ID contains only digits
                                    break;
                                }
                                System.out.println("Invalid ID. It should be a numeric value.");
                            }

                            // Validate Name (should not be numeric)
                            while (true) {
                                System.out.print("Name: ");
                                name = scanner.nextLine();
                                if (!name.matches(".*\\d.*")) { // Check if Name does not contain any digits
                                    break;
                                }
                                System.out.println("Invalid Name. It should not contain numeric values.");
                            }

                            // Validate Type (should not be numeric)
                            while (true) {
                                System.out.print("Type: ");
                                type = scanner.nextLine();
                                if (!type.matches(".*\\d.*")) { // Check if Type does not contain any digits
                                    break;
                                }
                                System.out.println("Invalid Type. It should not contain numeric values.");
                            }

                            double cost = -1;
                            while (cost < 0) {
                                System.out.print("Cost/Hour: ");
                                if (scanner.hasNextDouble()) {
                                    cost = scanner.nextDouble();
                                } else {
                                    System.out.println("Invalid input. Please enter a valid number.");
                                    scanner.next(); // Clear invalid input
                                }
                            }
                            scanner.nextLine(); // Clear buffer

                            boolean isAdded = resourceController.addResource(new Resource(id, name, type, cost));
                            if (isAdded) {
                                System.out.println("Resource added successfully.");
                            }
                        } else if (choice == 2) {
                            resourceController.viewResources();
                        } else if (choice == 3) {
                            break;
                        } else if (choice == 4) {
                            System.out.println("Exiting the program. Goodbye!");
                            scanner.close(); // Close scanner
                            System.exit(0);
                        } else {
                            System.out.println("Invalid choice. Try again.");
                        }

                    } else if (user instanceof RegularUser) {
                        System.out.println("\n====================================");
                        System.out.println("1. View Resources");
                        System.out.println("2. Book Resource");
                        System.out.println("3. View Bookings");
                        System.out.println("4. Cancel Booking");
                        System.out.println("5. Switch Role");
                        System.out.println("6. Exit");
                        System.out.println("====================================");
                        int choice = getMenuChoice(scanner, 6);

                        if (choice == 1) {
                            resourceController.viewResourcesWithBookings(bookingController);
                        } else if (choice == 2) { // Book Resource
                            System.out.print("Resource ID: ");
                            String rid = scanner.nextLine();

                            Resource res = Database.resourceRepository.getAllResources().stream()
                                    .filter(r -> r.getId().equals(rid)).findFirst().orElse(null);

                            if (res == null) {
                                System.out.println("Resource not found. Please try again.");
                                continue;
                            }

                            Calendar startCal = Calendar.getInstance();
                            Calendar endCal = Calendar.getInstance();

                            try {
                                // Get start time
                                System.out.print("Enter Start Time (yyyy-MM-dd HH:mm): ");
                                String startTimeInput = scanner.nextLine();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                startCal.setTime(sdf.parse(startTimeInput));

                                // Validate that the start date is in the future
                                Calendar now = Calendar.getInstance();
                                if (!startCal.after(now)) {
                                    String currentTime = sdf.format(now.getTime());
                                    System.out.println("Invalid booking: Start time must be in the future. Current time: " + currentTime);
                                    continue;
                                }

                                // Get end time
                                System.out.print("Enter End Time (yyyy-MM-dd HH:mm): ");
                                String endTimeInput = scanner.nextLine();
                                endCal.setTime(sdf.parse(endTimeInput));

                                // Validate booking duration
                                if (!endCal.after(startCal)) {
                                    System.out.println("Invalid booking duration: End time must be after start time.");
                                    continue;
                                }

                                // Calculate total hours
                                long durationInMillis = endCal.getTimeInMillis() - startCal.getTimeInMillis();
                                int totalHours = (int) (durationInMillis / (1000 * 60 * 60));

                                bookingController.bookResource(user.getId(), rid, startCal, endCal, totalHours, res.getCostPerHour());
                            } catch (ParseException e) {
                                System.out.println("Invalid input. Please enter valid date and time values.");
                            }
                        } else if (choice == 3) { // View Bookings
                            bookingController.viewBookings(); // Show all bookings with time and user details
                        } else if (choice == 4) { // Cancel Booking
                            bookingController.viewBookings(); // Show available bookings first
                            System.out.print("\nEnter Booking ID to cancel: ");
                            String bookingId = scanner.nextLine();
                            bookingController.cancelBooking(user.getId(), bookingId);
                        } else if (choice == 5) {
                            break;
                        } else if (choice == 6) {
                            System.out.println("Exiting the program. Goodbye!");
                            scanner.close();
                            System.exit(0);
                        } else {
                            System.out.println("Invalid choice. Try again.");
                        }
                    }
                }
            } else {
                System.out.println("Invalid role for the provided credentials. Try again.");
            }
        }
    }

    private static int getMenuChoice(Scanner scanner, int maxOption) {
        int choice = -1;
        while (choice < 1 || choice > maxOption) {
            System.out.print("Enter your choice (1-" + maxOption + "): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
            if (choice < 1 || choice > maxOption) {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.nextLine(); // Clear buffer
        return choice;
    }
}
