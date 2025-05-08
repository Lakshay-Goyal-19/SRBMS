package services;

public class CalculatorService {
    public double calculateCost(double hours, double costPerHour) {
        if (hours < 0 || costPerHour < 0) throw new IllegalArgumentException("Invalid hours or rate");
        return hours * costPerHour;
    }
}