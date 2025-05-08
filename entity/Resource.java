package entity;

public class Resource {
    private String id;
    private String name;
    private String type;
    private double costPerHour;
    private static int idCounter = 1;

    public Resource(String id, String name, String type, double costPerHour) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.costPerHour = costPerHour;
    }

    public Resource(String name, String type, double costPerHour) {
        this.id = "R" + (idCounter++);
        this.name = name;
        this.type = type;
        this.costPerHour = costPerHour;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getCostPerHour() { return costPerHour; }
}
