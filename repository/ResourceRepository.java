package repository;

import entity.Resource;
import java.util.*;

public class ResourceRepository {
    private List<Resource> resources = new ArrayList<>();

    public void addResource(Resource resource) { resources.add(resource); }
    public List<Resource> getAllResources() { return resources; }
}