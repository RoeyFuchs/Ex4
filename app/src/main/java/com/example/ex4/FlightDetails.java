package com.example.ex4;

public class FlightDetails {
    private Float Ailron;
    private Float Elevator;

    /**
     * constructor
     * @param ailron
     * @param elevator
     */
    public FlightDetails(float ailron, float elevator) {
        this.Ailron = ailron;
        this.Elevator = elevator;
    }

    public String getAilron() {
        return this.Ailron.toString();
    }

    public String getElevator() {
        return this.Elevator.toString();
    }
}
