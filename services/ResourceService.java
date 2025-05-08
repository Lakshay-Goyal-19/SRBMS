package services;

import entity.*;
import repository.*;
import java.util.*;

public class ResourceService {
    private ResourceRepository repo;

    public ResourceService(ResourceRepository repo) { this.repo = repo; }

    public void addResource(Resource resource) {
        repo.addResource(resource);
        System.out.println("Resource added successfully: " + resource.getName());
    }

    public List<Resource> getResources() { return repo.getAllResources(); }
}