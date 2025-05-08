package controller;

import services.*;
import entity.*;
import java.util.List; // Ensure this import is present

public class ResourceController {
    private ResourceService service;

    public ResourceController(ResourceService service) { this.service = service; }

    public boolean addResource(Resource resource) {
        List<Resource> existingResources = service.getResources();
        boolean isDuplicate = existingResources.stream().anyMatch(r -> r.getId().equals(resource.getId()));
        if (isDuplicate) {
            System.out.println("Resource with ID " + resource.getId() + " already exists. Cannot add duplicate.");
            return false; // Indicate that the resource was not added
        }
        service.addResource(resource); // Add resource only if it's not a duplicate
        return true; // Indicate that the resource was successfully added
    }

    public void viewResources() {
        List<Resource> resources = service.getResources();
        if (resources.isEmpty()) {
            System.out.println("No resources available.");
            System.out.println();
            return;
        }
        System.out.println("=== Available Resources ===");
        for (Resource r : resources) {
            System.out.println("Resource ID: " + r.getId() + ", Name: " + r.getName() + ", Type: " + r.getType() + ", Cost/Hour: Rs-" + r.getCostPerHour());
        }
        System.out.println();
    }

    public void viewResourcesWithBookings(BookingController bookingController) {
        List<Resource> resources = service.getResources();
        if (resources.isEmpty()) {
            System.out.println("No resources available.");
            System.out.println();
            return;
        }
        System.out.println("=== Available Resources ===");
        for (Resource r : resources) {
            System.out.println("Resource ID: " + r.getId() + ", Name: " + r.getName() + ", Type: " + r.getType() + ", Cost/Hour: Rs-" + r.getCostPerHour());
            bookingController.viewBookingsForResource(r.getId());
        }
        System.out.println();
    }
}